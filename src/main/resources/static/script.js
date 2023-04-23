const registrationForm = document.getElementById('registration-form');
registrationForm.addEventListener('submit', onRegisterHandler);

function onRegisterHandler(e) {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    createUser(data);
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
    const response = await fetch(baseUrl + '/users/register', settings);
    const responseData = await response.json();

    console.log(responseData);
}