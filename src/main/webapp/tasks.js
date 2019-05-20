function onTasksReceived() {
    const text = this.responseText;
    const tasks = JSON.parse(text);
    profileEl = document.getElementById('profile-data');
    createTasksTable(tasks);
}

function createTasksTable(tasks) {
    const tasksDivEl = document.getElementById('list-my-tasks');

    removeAllChildren(tasksDivEl);

    const idAttribute = document.createAttribute('id');
    idAttribute.value = 'myTasks'
    tasksDivEl.setAttributeNode(idAttribute);

    const ulEl = document.createElement('ul');

    const tasksDivElTitle = document.createElement('h2');
    tasksDivElTitle.innerHTML = 'My tasks:<br>';

    tasksDivEl.appendChild(tasksDivElTitle);
    tasksDivEl.appendChild(ulEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];

        const dataTaskIdAttr = document.createAttribute('data-task-id');
        dataTaskIdAttr.value = task.id;

        const taskButtonEl = document.createElement('a');
        taskButtonEl.textContent = task.title;
        taskButtonEl.setAttributeNode(dataTaskIdAttr);
        taskButtonEl.addEventListener('click', onTaskClicked);

        const liEl = document.createElement('li');
        liEl.appendChild(taskButtonEl);
        liEl.appendChild(createModifyAndDeleteButtons());

        ulEl.appendChild(liEl);
    }
}

function onCreateNewTask() {
    showContents(['profile-content', 'create-new-task', 'schedule']);
}

function onTaskClicked() {
}

function onCreateTaskButton() {
        const createTaskFormEl = document.forms['create-task'];

        const titleInputEl = createTaskFormEl.querySelector('input[name="task-name"]');

        const descInputEl = createTaskFormEl.querySelector('input[name="task-description"]');

        var name = titleInputEl.value;
        if (name == '') {
            return;
        }
        var description = descInputEl.value;

        const params = new URLSearchParams();
        params.append('task-name', name);
        params.append('task-description', description);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onCreateTaskResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'protected/tasks');
        xhr.send(params);
}

function onCreateTaskResponse() {

    const createTaskFormEl = document.forms['create-task'];
    const titleInputEl = createTaskFormEl.querySelector('input[name="task-name"]');
    var title = titleInputEl.value;

    alert(title + ' created!');
    location.reload();
}
