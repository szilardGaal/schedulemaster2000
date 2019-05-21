function loadGuestSchedule(){
    showContents(['schedule', 'schedule_content', 'profile-content']);
    document.getElementById('create-schedule-button').classList.add('hidden');
    document.getElementById('x').classList.add('hidden');
    document.getElementById('create-task-button').onclick = createTempTask;
    createGuestSchedule();
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
    document.getElementById('click-task-button-create').onclick = addTaskToTempList();
}

