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
    //console.log(id);

    const params = new URLSearchParams();
    params.append('id', id);
    //console.log(params.get('id'));

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleDisplayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule-display?' + params.toString());
    xhr.send();
}

function onScheduleDisplayResponse(){
    const text = this.responseText;
    const scheduleDisplayDto = JSON.parse(text);
    //console.log(scheduleDisplayDto);
    onScheduleDisplayGet(scheduleDisplayDto);
}

function onScheduleDisplayGet(scheduleDisplayDto) {
    //console.log(scheduleDisplayDto);
    showContents(['schedule_content', 'profile-content', 'logout-content', 'schedule']);

    createTasksToSelect(scheduleDisplayDto.allTaskForUser);
    
    const scheduleDivEl = document.getElementById('schedule');
    const scheduleTableEl = document.createElement('table');
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
            scheduleTrEl.appendChild(slotTdEl);
            for (let k = 0; k < scheduleDisplayDto.allTaskForSchedule.length; k++){
                const task = scheduleDisplayDto.allTaskForSchedule[k];
                //console.log(task.columns);
                if (task.columns.includes(j) && task.begins === time){
                    slotTdEl.textContent = "test";
                }
            }
        } time++;
        scheduleTableEl.appendChild(scheduleTrEl);
    }
    removeAllChildren(scheduleDivEl);
    scheduleDivEl.appendChild(scheduleTableEl);
}

function createTasksToSelect() {
    
}
