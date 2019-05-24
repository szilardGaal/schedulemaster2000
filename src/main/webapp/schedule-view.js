function showFirstSchedule() {
    var scheduleList = document.getElementById('list-my-schedules').getElementsByTagName('li');
    if (scheduleList.length < 1) {
        return;
    }
    const firstId = scheduleList[0].firstChild.getAttribute('data-schedule-id');
    const params = new URLSearchParams();
    params.append('id', firstId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleDisplayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule-display?' + params.toString());
    xhr.send();
}

function onScheduleClicked() {
    const id = this.getAttribute('data-schedule-id');

    const params = new URLSearchParams();
    params.append('id', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleDisplayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule-display?' + params.toString());
    xhr.send();
}

function onScheduleDisplayResponse(){
    const text = this.responseText;
    const scheduleDisplayDto = JSON.parse(text);
    onScheduleDisplayGet(scheduleDisplayDto);
}

function makeRequest (method, url) {
    return new Promise(function (resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.onload = function () {
            if (this.status >= 200 && this.status < 300) {
                resolve(JSON.parse(xhr.responseText));
            } else {
                reject({
                status: this.status,
                statusText: xhr.statusText
                });
            }
        };
        xhr.onerror = function () {
            reject({
            status: this.status,
            statusText: xhr.statusText
            });
        };
        xhr.send();
    });
}

function getColumnForSchedule(id) {
    const params = new URLSearchParams();
    params.append('schedule-id', id);

    const columns = makeRequest('GET', 'protected/columns?' + params.toString());
    return columns;
}

function onScheduleDisplayGet(scheduleDisplayDto) {
    showContents(['schedule_content', 'profile-content', 'logout-content', 'schedule']);
    
    const scheduleDivEl = document.getElementById('schedule');
    const titleEl = document.createElement('h2');
    titleEl.innerHTML = scheduleDisplayDto.schedule.name;

    const scheduleTableEl = document.createElement('table');
    scheduleTableEl.id = 'schedule-display-table';
    scheduleTableEl.setAttribute('data-schedule-id', scheduleDisplayDto.schedule.id);
    const cols = scheduleDisplayDto.schedule.cols;

    const headerRowTrEl = document.createElement('tr');
    const emptyColTdEl = document.createElement('td');

    headerRowTrEl.appendChild(emptyColTdEl);

    getColumnForSchedule(scheduleDisplayDto.schedule.id).then((columns)=> {
        debugger;
        const colIds = [];
        for (let i = 0; i < cols; i++){
            const columnHeaderTdEl = document.createElement('td');
            columnHeaderTdEl.textContent = columns[i].title;
            colIds[i] = (columns[i].id);
            headerRowTrEl.appendChild(columnHeaderTdEl);
        }
        scheduleTableEl.appendChild(headerRowTrEl);

        let time = 1;

        for (let i = 0; i < 24; i++){
            const scheduleTrEl = document.createElement('tr');
            const timeColTdEl = document.createElement('td');
            timeColTdEl.textContent = time + ':00';
            timeColTdEl.classList.add('line-highlight');
            scheduleTrEl.appendChild(timeColTdEl);
            for (let j = 1; j <= cols; j++){
                const slotTdEl = document.createElement('td');
                slotTdEl.id = colIds[j-1].toString() + ',' + time.toString() + ':00';
                checkIfSlotHasTask(slotTdEl.id);
                slotTdEl.onclick = cellClicked;

                scheduleTrEl.appendChild(slotTdEl);
            } time++;
            scheduleTableEl.appendChild(scheduleTrEl);
        }
        removeAllChildren(scheduleDivEl);
        scheduleDivEl.appendChild(titleEl);
        scheduleDivEl.appendChild(scheduleTableEl);
    });
}

function checkIfSlotHasTask(id) {
    const params = new URLSearchParams();
    params.append('cellId', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', ifSlotHasTaskReceived, false);
    xhr.addEventListener('error', onNetworkError);
    xhr.myParam = id;
    xhr.open('GET', 'protected/slots?' + params.toString());
    xhr.send(params);
}

function ifSlotHasTaskReceived(evt) {
    debugger;
    const text = this.responseText;
    const task = JSON.parse(text);
    const id = evt.target.myParam;
    fillSlotIfItHasTask(task, id);
}

function fillSlotIfItHasTask(task, id) {
    if (task.title != null) {
        const cellEl = document.getElementById(id);
        cellEl.textContent = task.title;

        const removeButtonEl = document.createElement('button');
        removeButtonEl.setAttribute('cell-id', id);
        removeButtonEl.onclick = removeTaskFromCell;
        removeButtonEl.textContent = 'X';

        cellEl.appendChild(removeButtonEl);
    }
}

function removeTaskFromCell() {
    const cellId = this.getAttribute('cell-id');

    const slotToEmpty = document.getElementById(cellId);
    slotToEmpty.removeAllChildren;
}

function createTasksInSelect(tasksInDropdown) {
    const dropdown = document.createElement('select');
    dropdown.style.display = 'block';

    for (let i = 0; i < tasksInDropdown.length; i++) {
        const task = tasksInDropdown[i];

        const taskOptionEl = document.createElement('option');
        taskOptionEl.onclick = dropdownTaskClicked;
        taskOptionEl.setAttribute('task-id', task.id);
        taskOptionEl.textContent = task.title;

        dropdown.appendChild(taskOptionEl);
    }
    const cellEl = document.getElementById(cellIdToPass);
    cellEl.appendChild(dropdown);
}

function removeDropDown() {
}

function dropdownTaskClicked() {
    const thisElement = this;
    const ids = cellIdToPass.split(',');
    const task_id = this.getAttribute('task-id');

    const params = new URLSearchParams();
    params.append('schedule-id', document.getElementById('schedule-display-table').getAttribute('data-schedule-id'));
    params.append('task-id', task_id);
    params.append('column-id', ids[0]);
    params.append('time', ids[1]);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', removeDropDown);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/schedule-display?' + params.toString());
    xhr.send();
}

function onCreateTaskResponseBla() {
    const text = this.responseText;
    const tasksInDropdown = JSON.parse(text);
    createTasksInSelect(tasksInDropdown);
}

function cellClicked() {
    const id = this.id;
    if (cellIdToPass != null) {
        console.log(cellIdToPass);
        const ParentOfDivToClose = document.getElementById(cellIdToPass);
        const divToClose = ParentOfDivToClose.firstChild;
        const property = divToClose.style.display;
        if (cellIdToPass == id && property == 'block') {
            divToClose.style.display = 'none';
            cellIdToPass = this.id;
            return;
        }
        if (cellIdToPass == id && property == 'none') {
            divToClose.style.display = 'block';
            cellIdToPass = this.id;
            return;
        }
        divToClose.style.display = 'none';
    }
    cellIdToPass = this.id;

    const ids = this.id.split(',');
    const params = new URLSearchParams();
    
    params.append('schedule-id', document.getElementById('schedule-display-table').getAttribute('data-schedule-id'));
    params.append('column-id', ids[0]);
    params.append('time', ids[1]);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCreateTaskResponseBla);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/taskContent?' + params.toString());
    xhr.send();
}
