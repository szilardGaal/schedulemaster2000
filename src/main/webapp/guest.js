function onGuestButtonClicked() {
    onGuestScreenLoad();
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'guest');
    xhr.send();
}

function onGuestScreenLoad(){
    clearMessages();
    showContents(['guest-content', 'create-new-task-guest']);
}

