function onProfileLoad(user) {

    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpanEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpanEl.textContent = user.userName;
    userPasswordSpanEl.textContent = user.password;



    const params = new URLSearchParams();
    params.append('id', user.id);


    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleGetResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'schedule');
    xhr.send(params);


}

