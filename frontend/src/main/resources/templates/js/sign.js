function saveToken(token) {
    localStorage.setItem('authToken', token);
}

function getToken() {
    return localStorage.getItem('authToken');
}

function deleteToken() {
    localStorage.clear();
}

function saveEmail(email) {
    localStorage.setItem("email", email);
}

function getEmail() {
    return localStorage.getItem("email");
}

// Check authentication; TODO: check JWT expiration date
function checkAuth() {
    const token = getToken();
    return token != null;
}

// Check the user has authenticated and redirect him from sign in and sign up pages
function redirectAuthenticatedUsers() {
    // If the user has authenticated, he cannot go to the signin and signup pages [SO FAR SO]
    if (checkAuth() &&
        (window.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/sign/signin.html"
        || window.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/sign/signup.html" )) {
        window.location.href = '../pages/home.html';
    }
}

// Use the token-authorization in the request
async function fetchWithAuth(url, options = {}) {
    const token = getToken();
    if (!options.headers) {
        options.headers = {};
    }
    if (token) {
        options.headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, options);
    return response;
}

// Processing the page events
$(document).ready(function () {
    redirectAuthenticatedUsers();

    $('#signin-form').on('submit', async function (event) {
        event.preventDefault();  // Prevent page refresh

        const emailInput = $('#email-input').val();
        const passwordInput = $('#password-input').val();

        try {
            const response = await fetch('http://localhost:8080/auth/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: emailInput,
                    password: passwordInput
                })
            });

            if (response.ok) {
                // Save the jwt token and the email of the user in the localstorage
                const data = await response.json();
                saveToken(data.token)
                saveEmail(emailInput);

                // Redirect to the home page
                window.location.href = '../pages/home.html';
            } else {
                const errorData = await response.json();
                $('#error-message').html('Ошибка: ' + errorData.message).show();
            }
        } catch (error) {
            $('#error-message').html('Ошибка: ' + error.message).show();
        }
    });

    $('#signup-form').on('submit', async function (event) {
        event.preventDefault();  // Предотвращает обновление страницы

        const nameInput = $('#name-input').val();
        const birthDateInput = $('#birth-date-input').val();
        const emailInput = $('#email-input').val();
        const passwordInput = $('#password-input').val();
        const weightInput = $('#weight-input').val();
        const heightInput = $('#height-input').val();

        try {
            const response = await fetch('http://localhost:8080/auth/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: nameInput,
                    birthDate: birthDateInput,
                    email: emailInput,
                    password: passwordInput,
                    weight: weightInput,
                    height: heightInput
                })
            });

            if (response.ok) {
                const data = await response.json();

                // Redirect to the signin page
                window.location.href = '../pages/sign/signin.html';

            } else {
                const errorData = await response.json();
                $('#error-message').html('Error: ' + errorData.error).show();
            }
        } catch (error) {
                $('#error-message').html('Error: ' + error.message).show();
        }
    });

    // Button: get list of all users
    $('#get-users').on('click', async function () {
        await getAllUsers();
    });

    // Button: delete token from browser
    $('#delete-jwt').on('click', async function () {
        await deleteToken();
    });

    // If the user had authenticated, it will be show his information
    if (document.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/pages/my-data.html") {
        const container = document.getElementById('user-data-container');

        var divUserInfo = document.createElement('div');
        divUserInfo.setAttribute('class', 'user-info');

        if (checkAuth()) {
            $.ajax({
                url: `http://localhost:8080/users/getUserByEmail/${getEmail()}`,
                type: "GET",
                headers: {
                    "Authorization": `Bearer ${getToken()}`
                },
                success: function (data) {
                    console.log(data)
                    divUserInfo.innerHTML = `
                            <h2>Me:</h2>
                            <h3>Name: ${data.name} + </h3>                      
                            <h3>Birth date: ${data.birthDate}</h3>
                            <h3>Email: ${data.email} </h3>
                            <h3>Weight: ${data.weight}</h3>
                            <h3>Height: ${data.heigh}</h3>
                    `;
                    container.appendChild(divUserInfo);
                }

            });
        } else {
            divUserInfo.innerHTML = `
                    <h3>You're not authenticated</h3>            
            `;
            container.appendChild(divUserInfo);
        }
    }
});

const createHealthDataBtn = document.getElementById("create-health-data-btn");
const createHealthDataDialog = document.getElementById("create-health-data-dialog");
const createHealthDataPopup = document.getElementById("create-health-data-dialog-container");
const cancelCreateHealthDataBtn = document.getElementById("cancel-create-health-data-btn");

createHealthDataBtn.addEventListener("click", () =>
    createHealthDataDialog.showModal()
);
cancelCreateHealthDataBtn.addEventListener("click", () =>
    createHealthDataDialog.close()
);

createHealthDataDialog.addEventListener('click', (event) => {
    if (event.target.id !== createHealthDataPopup.id) {
        createHealthDataDialog.close();
    }
});
