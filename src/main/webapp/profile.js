function onProfileLoad(user) {
    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpanEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpanEl.textContent = user.userName;
    userPasswordSpanEl.textContent = user.password;
}
