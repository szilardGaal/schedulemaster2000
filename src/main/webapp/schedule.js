function onScheduleGetResponse() {
    clearMessages();
    if (this.status === OK) {
        console.log(JSON.parse(JSON.stringify(this.responseText)));
        onScheduleGet(JSON.parse(this.responseText));
    } else {
        onOtherResponse(mySchedukeListContentUlEl, this);
    }
}

function onScheduleGet(scheduleDto) {
    console.log(scheduleDto);
    addMySchedules(scheduleDto.myList);
    addPublicSchedules(scheduleDto.publicList);
}

function addMySchedules(schedules) {
    const myScheduleUlEl = document.getElementById('list-my-schedules');

    removeAllChildren(myScheduleUlEl);

    if (schedules.length === 0){
        const noSchedulesLiEl = document.createElement('li');
        noSchedulesLiEl.textContent = "You did not create any schedules yet."
    } else {
        for (let i = 0; i < schedules.length; i++){
            const scheduleLiEl = document.createElement("li");
            scheduleLiEl.textContent = schedules[i];
            myScheduleUlEl.appendChild(scheduleLiEl);
        }
    }
}

function addPublicSchedules(schedules) {
    const publicScheduleUlEl = document.getElementById('list-public-schedules');

    removeAllChildren(publicScheduleUlEl);

    if (schedules.length === 0){
        const noSchedulesLiEl = document.createElement('li');
        noSchedulesLiEl.textContent = "You did not create any schedules yet."
    } else {
        for (let i = 0; i < schedules.length; i++){
            const scheduleLiEl = document.createElement("li");
            scheduleLiEl.textContent = schedules[i];
            publicScheduleUlEl.appendChild(scheduleLiEl);
        }
    }
}

