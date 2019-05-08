function onProfileLoad(user) {

    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpanEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpanEl.textContent = user.userName;
    userPasswordSpanEl.textContent = user.password;

    showSchedules();
    showTasks();
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

function onTasksReceived() {
    const text = this.responseText;
    const tasks = JSON.parse(text);
    document.getElementById('profile-data').appendChild(createTasksTable(tasks));
}

function createTasksTable(tasks) {
    const ulEl = document.createElement('ul');

    for (let i = 0; i < tasks.length; i++) {
            const task = tasks[i];

            const dataTaskIdAttr = document.createAttribute('data-task-id');
            dataTaskIdAttr.value = task.id;

            const taskButtonEl = document.createElement('button');
            taskButtonEl.textContent = task.title;
            taskButtonEl.setAttributeNode(dataTaskIdAttr);
            taskButtonEl.addEventListener('click', onTaskClicked);

            const liEl = document.createElement('li');
            liEl.appendChild(taskButtonEl);

            ulEl.appendChild(liEl);
        }

        return ulEl;
}

function onTaskClicked() {

}

