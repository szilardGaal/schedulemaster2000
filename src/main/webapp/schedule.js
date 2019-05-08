
function onScheduleGetResponse() {
    const text = this.responseText;
    console.log(text);
    const scheduleDto = JSON.parse(text);
    onScheduleGet(scheduleDto);
}

function onScheduleGet(scheduleDto) {
    addMySchedules(scheduleDto.myList);
    addPublicSchedules(scheduleDto.publicList);
}

function addMySchedules(schedules) {
    const myScheduleDivEl = document.getElementById('list-my-schedules');

    removeAllChildren(myScheduleDivEl);

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
            myScheduleUlEl.appendChild(scheduleLiEl);

        }
    } myScheduleDivEl.appendChild(myScheduleUlEl);
    return myScheduleDivEl;
}

function addPublicSchedules(schedules) {
    const publicScheduleDivEl = document.getElementById('list-public-schedules');

    removeAllChildren(publicScheduleDivEl);

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
            publicScheduleUlEl.appendChild(publicScheduleLiEl);

        }
    } publicScheduleDivEl.appendChild(publicScheduleUlEl);
    return publicScheduleDivEl;
}

function onScheduleClicked() {

}

function onCreateScheduleResponse() {
    alert('cucc!');
}

function onCreateScheduleButton() {
    const createScheduleFormEl = document.forms['create-schedule'];

    const titleInputEl = createScheduleFormEl.querySelector('input[name="schedule-name"]');
    const durationInputEl = createScheduleFormEl.querySelector('select[name="schedule-duration"]');

    var title = titleInputEl.value;
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
    showContents(['back-to-profile-content', 'create-new-schedule']);
}
