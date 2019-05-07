function onProfileLoad(user) {
    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpanEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpanEl.textContent = user.email;
    userPasswordSpanEl.textContent = user.password;
}
