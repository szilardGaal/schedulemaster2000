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

function onScheduleDisplayGet(scheduleDisplayDto) {
    showContents(['schedule_content', 'profile-content', 'logout-content', 'schedule']);

    createTasksToSelect(scheduleDisplayDto.allTaskForUser);
    
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
    for (let i = 0; i < cols; i++){
        const columnHeaderTdEl = document.createElement('td');
        columnHeaderTdEl.textContent = 'Day ' + (i+1);

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
            slotTdEl.id = j.toString() + ',' + time.toString() + ':00';
            slotTdEl.onclick = cellClicked;

            scheduleTrEl.appendChild(slotTdEl);
        } time++;
        scheduleTableEl.appendChild(scheduleTrEl);
    }
    removeAllChildren(scheduleDivEl);
    scheduleDivEl.appendChild(titleEl);
    scheduleDivEl.appendChild(scheduleTableEl);
}

function createTasksToSelect(tasksInDropdown) {
    alert('creating dropdown');
    const dropdown = document.createElement('select');

    for (let i = 0; i < tasksInDropdown.length; i++) {
        const task = tasksInDropdown[i];

        const taskOptionEl = document.createElement('option');
        taskOptionEl.onclick = dropdownTaskClicked;
        taskOptionEl.setAttribute = task.id;
        taskOptionEl.textContent = task.title;

        dropdown.appendChild(taskOptionEl);
    }
}

function dropdownTaskClicked() {
}

function onCreateTaskResponseBla() {
    const text = this.responseText;
    const tasksInDropdown = JSON.parse(text);
    createTasksToSelect(tasksInDropdown);
}

function cellClicked() {
    const ids = this.id.split(',');
    const params = new URLSearchParams();
    
    params.append('schedule-id', document.getElementById('schedule-display-table').getAttribute('data-schedule-id'));
    params.append('columnId', ids[0]);
    params.append('time', ids[1]);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCreateTaskResponseBla);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/tasks?' + params.toString());
    xhr.send();
}
