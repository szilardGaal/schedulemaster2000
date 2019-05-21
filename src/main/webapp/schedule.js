function deleteSchedule(id) {
    const params = new URLSearchParams();
    params.append('scheduleId', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', function () { location.reload();});
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/schedule?' + params.toString());
    xhr.send();
}

function onSubmitModifyScheduleButton() {

}

function createModifyScheduleForm(scheduleDto) {
    const modifyScheduleFormEl = document.forms['modify-schedule'];

    const titleInputEl = modifyScheduleFormEl.querySelector('input[name="schedule-name-update"]');
    const durationInputEl = modifyScheduleFormEl.querySelector('select[name="schedule-duration-update"]');
    const isPublicInputEl = modifyScheduleFormEl.querySelector('select[name="is-public-update"]');

    titleInputEl.value = scheduleDto.schedule.name;
    durationInputEl.value = scheduleDto.schedule.cols;
    isPublicInputEl.value = scheduleDto.schedule.public.toString();
/*
    var title = titleInputEl.value;
    if (title == '') {
        return;
    }
    var duration = durationInputEl.value;

    const params = new URLSearchParams();
    params.append('schedule-name', title);
    params.append('schedule-cols', duration);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCreateScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedule');
    xhr.send(params);*/
}

function onModifyScheduleResponse() {
    const text = this.responseText;
    const scheduleDto = JSON.parse(text);
    createModifyScheduleForm(scheduleDto);
}

function modifySchedule(id) {
    showContents(['profile-content', 'modify-schedule', 'schedule']);

    const params = new URLSearchParams();
    params.append('id', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onModifyScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule-display?' + params.toString());
    xhr.send();
}

function onScheduleGetResponse() {
    const text = this.responseText;
    const scheduleDto = JSON.parse(text);
    onScheduleGet(scheduleDto);
}

function onScheduleGet(scheduleDto) {
    addMySchedules(scheduleDto.myList);
    addPublicSchedules(scheduleDto.publicList);
}

function onDeleteButtonClicked() {
    const parentEl = this.parentElement.parentElement;
    let id;
    if (this.getAttribute('data-type') === 'schedule') {
        id = parentEl.firstChild.getAttribute('data-schedule-id');
        deleteSchedule(id);
    } else {
        id = parentEl.firstChild.getAttribute('data-task-id');
        deleteTask(id);
    }
}

function onModifyButtonClicked() {
    const parentEl = this.parentElement.parentElement;
    let id;
    if (this.getAttribute('data-type') === 'schedule') {
        id = parentEl.firstChild.getAttribute('data-schedule-id');
        modifySchedule(id);
    } else {
        id = parentEl.firstChild.getAttribute('data-task-id');
        modifyTask(id);
    }
}

function createModifyAndDeleteButtons(thisElement) {

    const buttonWrapper = document.createElement('div');
    buttonWrapper.setAttribute('class', 'button-wrapper');

    const deleteButton = document.createElement('button');
    deleteButton.innerHTML = '   ';
    deleteButton.style.backgroundImage = 'url(img/delete.png)';
    deleteButton.setAttribute('data-type', thisElement.parentElement.getAttribute('data-type'));
    deleteButton.addEventListener('click', onDeleteButtonClicked);

    const modifyButton = document.createElement('button');
    modifyButton.innerHTML = '   ';
    modifyButton.style.backgroundImage = 'url(img/modify.png)';
    modifyButton.setAttribute('data-type', thisElement.parentElement.getAttribute('data-type'));
    modifyButton.addEventListener('click', onModifyButtonClicked);

    buttonWrapper.appendChild(deleteButton);
    buttonWrapper.appendChild(modifyButton);

    return buttonWrapper;
}

function addMySchedules(schedules) {
    const myScheduleDivEl = document.getElementById('list-my-schedules');

    removeAllChildren(myScheduleDivEl);
    const titleEl = document.createElement('h2');
    titleEl.innerHTML = 'My Schedules:'

    myScheduleDivEl.appendChild(titleEl);

    const myScheduleUlEl = document.createElement("ul");
    myScheduleUlEl.setAttribute('data-type', 'schedule');
    if (schedules.length === 0){
        const noSchedulesLiEl = document.createElement('li');
        noSchedulesLiEl.textContent = "You did not create any schedules yet.";
        myScheduleUlEl.appendChild(noSchedulesLiEl);
        myScheduleDivEl.appendChild(myScheduleUlEl);
    } else {
        for (let i = 0; i < schedules.length; i++){
            const scheduleLiEl = document.createElement("li");

            const scheduleIdAttr = document.createAttribute('data-schedule-id');
            scheduleIdAttr.value = schedules[i].id;

            const scheduleLinkEl = document.createElement('a');
            scheduleLinkEl.setAttributeNode(scheduleIdAttr);
            scheduleLinkEl.textContent = schedules[i].name;
        
            if (schedules[i].public) {
                const publicEl = document.createElement('i');
                publicEl.style.marginLeft = '8px';
                publicEl.style.color = 'red';
                publicEl.style.fontStyle = 'italic';
                publicEl.textContent = 'public';
                scheduleLinkEl.appendChild(publicEl);
            }

            scheduleLinkEl.addEventListener('click', onScheduleClicked);

            scheduleLiEl.appendChild(scheduleLinkEl);
            myScheduleUlEl.appendChild(scheduleLiEl);
            scheduleLiEl.appendChild(createModifyAndDeleteButtons(scheduleLiEl));

        }
    } myScheduleDivEl.appendChild(myScheduleUlEl);
    showFirstSchedule();
    return myScheduleDivEl;
}

function addPublicSchedules(schedules) {
    const publicScheduleDivEl = document.getElementById('list-public-schedules');

    removeAllChildren(publicScheduleDivEl);

    const titleEl = document.createElement('h2');
    titleEl.innerHTML = 'Public Schedules:'
    publicScheduleDivEl.appendChild(titleEl);

    const publicScheduleUlEl = document.createElement("ul");
    publicScheduleUlEl.setAttribute('data-type', 'schedule');
    if (schedules.length === 0){
        const noSchedulesLiEl = document.createElement('li');
        noSchedulesLiEl.textContent = "No public schedules available yet.";
        publicScheduleUlEl.appendChild(noSchedulesLiEl);
        publicScheduleDivEl.appendChild(publicScheduleUlEl);
    } else {
        for (let i = 0; i < schedules.length; i++){
            const publicScheduleLiEl = document.createElement("li");

            const scheduleIdAttr = document.createAttribute('data-schedule-id');
            scheduleIdAttr.value = schedules[i].id;

            const publicScheduleLinkEl = document.createElement('a');
            publicScheduleLinkEl.setAttributeNode(scheduleIdAttr);
            publicScheduleLinkEl.textContent = schedules[i].name;

            publicScheduleLinkEl.addEventListener('click', onScheduleClicked);

            publicScheduleLiEl.appendChild(publicScheduleLinkEl);
            publicScheduleUlEl.appendChild(publicScheduleLiEl);
            publicScheduleLiEl.appendChild(createModifyAndDeleteButtons(publicScheduleLiEl));

        }
    } publicScheduleDivEl.appendChild(publicScheduleUlEl);
    return publicScheduleDivEl;
}

function onCreateScheduleResponse() {

    const createScheduleFormEl = document.forms['create-schedule'];
    const titleInputEl = createScheduleFormEl.querySelector('input[name="schedule-name"]');
    const durationInputEl = createScheduleFormEl.querySelector('select[name="schedule-duration"]');
    var title = titleInputEl.value;
    var duration = durationInputEl.value;

    alert(title + ' created with ' + duration + ' colums!');
    location.reload();
}

function onCreateScheduleButton() {
    const createScheduleFormEl = document.forms['create-schedule'];

    const titleInputEl = createScheduleFormEl.querySelector('input[name="schedule-name"]');
    const durationInputEl = createScheduleFormEl.querySelector('select[name="schedule-duration"]');
    const visibilityInputEl = createScheduleFormEl.querySelector('select[name="is-public"]');

    var title = titleInputEl.value;
    if (title == '') {
        return;
    }
    var duration = durationInputEl.value;
    var visibility = visibilityInputEl.value;

    const params = new URLSearchParams();
    params.append('schedule-name', title);
    params.append('schedule-cols', duration);
    params.append('is-public', visibility);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCreateScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedule');
    xhr.send(params);
}

function onCreateNewSchedule() {
    showContents(['profile-content', 'create-new-schedule', 'schedule']);
}
