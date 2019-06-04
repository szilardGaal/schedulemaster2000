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

function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();

  const params = new URLSearchParams();
  params.append('id_token', googleUser.getAuthResponse().id_token);

  const xhr = new XMLHttpRequest();
  xhr.addEventListener('load', onGLoginResponse);
  xhr.addEventListener('error', onNetworkError);
  xhr.open('POST', 'glogin');
  xhr.send(params);
}

function onGLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        onProfileLoad(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}



