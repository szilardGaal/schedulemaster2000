
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

}

function onModifyButtonClicked() {

}

function createModifyAndDeleteButtons() {

    const buttonWrapper = document.createElement('div');
    buttonWrapper.setAttribute('class', 'button-wrapper');

    const deleteButton = document.createElement('button');
    deleteButton.style.backgroundImage = 'url(img/delete.png)';
    deleteButton.addEventListener = ('click', onDeleteButtonClicked);

    const modifyButton = document.createElement('button');
    modifyButton.style.backgroundImage = 'url(img/modify.png)';
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

            scheduleLinkEl.addEventListener('click', onScheduleClicked);

            scheduleLiEl.appendChild(scheduleLinkEl);
            scheduleLiEl.appendChild(createModifyAndDeleteButtons());
            myScheduleUlEl.appendChild(scheduleLiEl);

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
            publicScheduleLiEla.ppendChild(createModifyAndDeleteButtons());
            publicScheduleUlEl.appendChild(publicScheduleLiEl);

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
    xhr.send(params);
}

function onCreateNewSchedule() {
    showContents(['profile-content', 'create-new-schedule', 'schedule']);
}
