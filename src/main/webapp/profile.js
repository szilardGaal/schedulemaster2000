function onProfileLoad(user) {
    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpanEl = document.getElementById('user-email');

    userEmailSpanEl.textContent = ' ' + user.userName;

    showTasks();
    showSchedules();
}

function showSchedules() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleGetResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule');
    xhr.send();
}

function showTasks() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}

