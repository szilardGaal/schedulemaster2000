function validatePassword() {
    if(password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}

function onRegisterButtonClicked() {
    const registerFormEl = document.forms['register-form'];

    const emailInputEl = registerFormEl.querySelector('input[name="email"]');
    const userNameInputEl = registerFormEl.querySelector('input[name="username"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="password"]');
    const confirmPasswordEl = registerFormEl.querySelector('input[name="confirm_password"]');
    const roleInputEl = registerFormEl.querySelector('select[name="role"]');

    const password = passwordInputEl.value;
    const confirm_password = confirmPasswordEl.value;
    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;

    const email = emailInputEl.value;
    const username = userNameInputEl.value;
    const role = roleInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);
    params.append('username', username);
    params.append('role', role);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegisterResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function loadRegisterPage() {
    showContents(['register-content']);
}