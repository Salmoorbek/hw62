const registrationForm = document.getElementById('registration-form');
registrationForm.addEventListener('submit', onRegisterHandler);
function onRegisterHandler(e) {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    createUser(data);
    registrationForm.reset();
}



async function createUser(userFormData) {
    const userJSON = JSON.stringify(Object.fromEntries(userFormData));
    console.log(userJSON);
    const settings = {
        method: 'POST',
        cache: 'no-cache',
        mode : 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: userJSON
    };
    const baseUrl = 'http://localhost:8080';
    try {
        const response = await fetch(baseUrl + '/users/register', settings);
        const responseData = await response.json();
        if (!userFormData || userFormData.get('email').length === 0) {
            alert("error");
            return;
        }
        console.log(responseData);
        alert("Вы зарегистрировались!!!");
    } catch (error) {
        console.error('Произошла ошибка:', error);
        alert("Такой пользователь уже существует!!!");
    }
}

const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', onLoginHandler);

function onLoginHandler(e) {
    e.preventDefault();
    const form = e.target;
    const userFormData = new FormData(form);
    const user = Object.fromEntries(userFormData);
}







