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
        title.textContent = post.title;

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

getPosts();

async function toggleComments(postId) {
    const commentsContainer = document.getElementById(`comments-${postId}`);
    const response = await fetch(`https://localhost:8080/comments?postId=${postId}`);
    const comments = await response.json();
    if (commentsContainer.style.display === 'block') {
        commentsContainer.style.display = 'none';
    } else {
        commentsContainer.style.display = 'block';
    }

    commentsContainer.innerHTML = '';

    comments.forEach(comment => {
        const commentElement = document.createElement('div');
        commentElement.innerHTML = `
      <h4>${comment.name}</h4>
      <p>${comment.body}</p>
    `;
        commentsContainer.appendChild(commentElement);
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

async function sendData(comment) {
    const url = `https://jsonplaceholder.typicode.com/comments`;

    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify({
            postId: comment.postId,
            name: comment.name,
            email: comment.email,
            body: comment.body
        }),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    });

    return await response.json();
}