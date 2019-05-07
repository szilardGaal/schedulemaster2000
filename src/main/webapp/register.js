function sendRegiserData() {
    const registerFormEl = document.forms['register-form'];

    const emailInputEl = registerFormEl.querySelector('input[name="email"]');
    const userNameInputEl = registerFormEl.querySelector('input[name="username"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="password"]');
    const roleInputEl = registerFormEl.querySelector('select[name="role"]');

    const email = emailInputEl.value;
    const username = userNameInputEl.value;
    const password = passwordInputEl.value;
    const role = roleInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('username', username);
    params.append('password', password);
    params.append('role', role);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegisterResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function validatePassword() {
    const registerFormEl = document.forms['register-form'];
    var password1 = registerFormEl.querySelector('input[name="password"]').value;
    var password2 = registerFormEl.querySelector('input[name="confirm_password"]').value;
    var validaetStatusEl = document.getElementById('validate_status');

    if(password1 == "" && password2 == "") {
        validaetStatusEl.innerHTML = "<br>";
    } else if (password1 == password2) {
        validaetStatusEl.innerHTML = "valid";
        validaetStatusEl.style.color = "green";
    } else {
        validaetStatusEl.innerHTML = "passwords don't match!";
        validaetStatusEl.style.color = "red";  
    }  
}

function onRegisterButtonClicked() {
    var validaetStatusEl = document.getElementById('validate_status');

    if (validaetStatusEl.innerHTML = "valid") {
        sendRegiserData();
    }
}

function loadRegisterPage() {
    showContents(['register-content']);
}
