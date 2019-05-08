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
    params.append('title', title);
    params.append('duration', duration);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCreateScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedules');
    xhr.send(params);
}

function onCreateNewSchedule() {
    showContents(['back-to-profile-content', 'create-new-schedule']);
}