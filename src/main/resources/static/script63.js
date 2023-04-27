const mainBlock = document.getElementsByClassName("main-block")[0]
const splashScreen = document.getElementById('splash');
const postsContainer = document.getElementById('posts-container');

async function getPosts() {
    const baseUrl = 'http://localhost:8080';
    const response = await fetch(baseUrl + '/publications/takePublications');
    let posts = await response.json();
    posts = posts.sort((a, b) => a.id - b.id);
    posts.forEach(post => {
        const postElement = document.createElement('div');
        postElement.classList.add('post');
        const postActions = document.createElement('div');
        postActions.classList.add('post-actions');

        const likeButton = document.createElement('span');
        likeButton.classList.add('h3', 'mx-1', 'like-button', 'muted');
        const likeIcon = document.createElement('i');
        likeIcon.classList.add('bi', 'bi-suit-heart-fill');
        likeButton.appendChild(likeIcon);
        postActions.appendChild(likeButton);
        likeButton.addEventListener('click', function () {
            likeButton.classList.toggle('clicked');
        });

        const imageContainer = document.createElement('div');
        imageContainer.classList.add('position-relative');
        const image = document.createElement('img');
        image.src = post.img;
        image.classList.add('card-img-top');
        image.alt = 'post image';
        imageContainer.appendChild(image);
        const likeHeart = document.createElement('div');
        likeHeart.classList.add('h1', 'like-heart');
        const heartIcon = document.createElement('i');
        heartIcon.classList.add('bi', 'bi-suit-heart-fill');
        likeHeart.appendChild(heartIcon);
        imageContainer.appendChild(likeHeart);

        image.addEventListener('dblclick', function () {
            likeButton.classList.toggle('clicked');
        });

        likeHeart.style.visibility = 'hidden';
        image.addEventListener('dblclick', function () {

            if (!likeButton.classList.contains('clicked')) {
                likeHeart.style.visibility = 'hidden';
                likeHeart.classList.remove('active');
            } else {
                likeHeart.classList.add('active');
                likeHeart.style.visibility = 'visible'
                setTimeout(function () {
                    likeHeart.classList.remove('active');
                    likeHeart.style.visibility = 'hidden';
                }, 1000);

            }
        });

        likeButton.addEventListener('click', function () {
            likeButton.classList.toggle('active');
        });

        const commentsToggle = document.createElement('span');
        commentsToggle.classList.add('h3', 'mx-2', 'mt-0', 'comment-button', 'muted');
        const commentIcon = document.createElement('i');
        commentIcon.classList.add('bi', 'bi-chat-fill');
        commentsToggle.id = `comments-toggle-${post.id}`;
        commentsToggle.appendChild(commentIcon);
        commentsToggle.addEventListener('click', () => toggleComments(post.id));
        const saveButton = document.createElement('span');
        saveButton.classList.add('h3', 'float-sm-right', 'mt-0', 'save-button');
        const saveIcon = document.createElement('i');
        saveIcon.classList.add('bi', 'bi-bookmark-fill');
        saveButton.appendChild(saveIcon);
        postActions.appendChild(saveButton);
        postActions.appendChild(commentsToggle);

        const title = document.createElement('h3');
        title.textContent = post.description;

        const text = document.createElement('p');
        text.textContent = post.body;

        const commentsContainer = document.createElement('div');
        commentsContainer.id = `comments-${post.id}`;

        const commentForm = createCommentFormElement(post);
        postActions.appendChild(commentForm);

        commentsToggle.addEventListener('click', () => {
            commentForm.classList.toggle('d-none');
        });

        commentForm.addEventListener('submit', (event) => {
            event.preventDefault();

            const formData = new FormData(commentForm);
            const comment = {
                user: formData.get("userId"),
                post: formData.get("postId"),
                text: formData.get("comment")
            };
            const commentElement = createCommentElement(comment);
            commentsContainer.appendChild(commentElement);
            sendData(comment)
            commentForm.reset();
        });

        saveButton.addEventListener('click', function () {
            saveButton.classList.toggle('clicked');
        });


        postElement.appendChild(imageContainer);
        postElement.appendChild(title);
        postElement.appendChild(text);
        postElement.appendChild(postActions);
        postElement.appendChild(commentsContainer);

        postsContainer.appendChild(postElement);
    });
}

function createCommentElement(comment) {
    const commentElement = document.createElement('div');
    commentElement.className = 'comment';

    const textElement = document.createElement('p');
    textElement.innerText = comment.text;

    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const day = now.getDate();
    const hours = now.getHours();
    const minutes = now.getMinutes();
    const seconds = now.getSeconds();

    const formattedDate = `${day}/${month}/${year}`;
    const formattedTime = `${hours}:${minutes}:${seconds}`;

    const timeElement = document.createElement('p');
    timeElement.innerText = `${formattedDate} ${formattedTime}`;

    commentElement.appendChild(textElement);
    commentElement.appendChild(timeElement);
    return commentElement;
}

function createCommentFormElement(post) {
    const form = document.createElement('form');
    form.classList.add('comment-form', 'd-none');

    const userIdInput = document.createElement('input');
    userIdInput.type = 'hidden';
    userIdInput.name = 'userId';
    userIdInput.value = post.user;
    form.appendChild(userIdInput);

    const postIdInput = document.createElement('input');
    postIdInput.type = 'hidden';
    postIdInput.name = 'postId';
    postIdInput.value = post.id;
    form.appendChild(postIdInput);

    const textarea = document.createElement('textarea');
    textarea.classList.add('form-control', 'mb-2');
    textarea.name = 'comment';
    textarea.placeholder = 'Write a comment...';
    form.appendChild(textarea);

    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.classList.add('btn', 'btn-primary');
    submitButton.textContent = 'Submit';
    form.appendChild(submitButton);

    return form;
}


const registerForm = document.getElementById("register-form")
registerForm.addEventListener("submit", registerHandler)

function registerHandler(e) {
    e.preventDefault()
    const form = e.target
    const data = new FormData(form)
    createUser(data)
}

const baseUrl = 'http://localhost:8080';

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

const authUrl = "/users/login"
const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', onLoginHandler);


function onLoginHandler(e) {
    e.preventDefault();
    let form = e.target;
    let userFormData = new FormData(form);
    let user = {
        username: userFormData.get("email"),
        password: userFormData.get("password")
    }
    localStorage.setItem('user', JSON.stringify(user));
    authentication();
    loginForm.reset();
}

async function authentication() {
    let userFromStorage = localStorage.getItem("user")
    let userAuth = JSON.parse(userFromStorage)
    let settings = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Basic " + btoa(userAuth.username + ":" + userAuth.password),
        }
    }
    try {
        let searchedUser = await fetch(baseUrl + authUrl, settings).then(response => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            hideSplashScreen()
            let email = document.createElement("span")
            email.id = "name"
            email.setAttribute("style", "color: #fff; text-align: right;")
            let button = document.getElementById("closeButton")
            button.before(email)
            email.append(userAuth.username)
            return response.text();
        })
        console.log(searchedUser)
        splashScreen.style.display = 'none';
        mainBlock.style.display = 'block';
        getPosts();
    } catch (error) {
        alert("Неверный логин или пароль")
    }

}

function hideSplashScreen() {
    let myElement = document.getElementById("splash")
    myElement.hidden = true
}

const closeButton = document.getElementById("closeButton")
closeButton.addEventListener("click", function () {
    let myElement = document.getElementById("splash")
    myElement.hidden = false
    splashScreen.style.display = 'block';
    mainBlock.style.display = 'none';
    localStorage.removeItem('user');
    localStorage.clear()
})