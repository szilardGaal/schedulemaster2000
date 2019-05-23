function loadGuestSchedule(){
    showContents(['schedule', 'schedule_content', 'profile-content']);
    document.getElementById('create-schedule-button').classList.add('hidden');
    document.getElementById('x').classList.add('hidden');
    document.getElementById('create-task-button').onclick = createTempTask;
    createGuestSchedule();
    createSampleTable();
}

function createGuestSchedule() {
    const myScheduleDivEl = document.getElementById('list-my-schedules');

    removeAllChildren(myScheduleDivEl);
    const titleEl = document.createElement('h2');
    titleEl.innerHTML = 'My Schedules:';

    myScheduleDivEl.appendChild(titleEl);

    const myScheduleUlEl = document.createElement('ul');
    const noSchedulesLiEl = document.createElement('li');
    noSchedulesLiEl.textContent = 'Schedule sample';
    myScheduleUlEl.appendChild(noSchedulesLiEl);
    myScheduleDivEl.appendChild(myScheduleUlEl);

    return myScheduleDivEl;
}

function createTempTask(){
    showContents(['schedule', 'schedule_content', 'profile-content', 'x']);
    document.getElementById('cancel-create-button').classList.add('hidden');
    document.getElementById('click-task-button-create').onclick = addTaskToTempList();
}


function createSampleTable() {
    const scheduleDivEl = document.getElementById('schedule');
    const scheduleTableEl = document.createElement('table');

    const headerRowTrEl = document.createElement('tr');
    const emptyColTdEl = document.createElement('td');
    headerRowTrEl.appendChild(emptyColTdEl);
    for (let i = 0; i < 3; i++){
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
        for (let j = 1; j <= 3; j++){
            const slotTdEl = document.createElement('td');
            scheduleTrEl.appendChild(slotTdEl);
        } time++;
        scheduleTableEl.appendChild(scheduleTrEl);
    }
    removeAllChildren(scheduleDivEl);
    scheduleDivEl.appendChild(scheduleTableEl);
}

function addTaskToTempList() {
    
}

