function sendRegisterData() {
    const registerFormEl = document.forms['register-form'];

    const userNameInputEl = registerFormEl.querySelector('input[name="username"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="password"]');
    const roleInputEl = registerFormEl.querySelector('select[name="role"]');

    const username = userNameInputEl.value;
    const password = passwordInputEl.value;
    const role = roleInputEl.value;

    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    params.append('role', role);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function validatePassword() {
    const registerFormEl = document.forms['register-form'];
    var password1 = registerFormEl.querySelector('input[name="password"]').value;
    var password2 = registerFormEl.querySelector('input[name="confirm_password"]').value;
    var validateStatusEl = document.getElementById('validate_status');

    if(password1 == "" && password2 == "") {
        validateStatusEl.innerHTML = "<br>";
    } else if (password1 == password2) {
        validateStatusEl.innerHTML = "valid";
        validateStatusEl.style.color = "green";
    } else {
        validateStatusEl.innerHTML = "passwords don't match!";
        validateStatusEl.style.color = "red";
    }  
}

function onRegisterButtonClicked() {
    var validateStatusEl = document.getElementById('validate_status');

    if (validateStatusEl.innerHTML = "valid") {
        sendRegisterData();
    }
}

function loadRegisterPage() {
    showContents(['register-content']);
}
