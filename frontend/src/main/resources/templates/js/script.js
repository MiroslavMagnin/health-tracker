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

function saveUserId(id) {
    localStorage.setItem("user_id", id);
}

function getUserId() {
    return localStorage.getItem("user_id");
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
        (window.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/pages/sign/signup.html"
        || window.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/pages/sign/signin.html" )) {
        window.location.href = '../home.html';
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

function getUserIdResponse() {
    $.ajax({
        url: 'http://localhost:8080/users/getUserByEmail/' + getEmail(),
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${getToken()}`
        },
        success: function (data) {
            saveUserId(data.id);
        }
    });
}

function getUserHealthDataResponse() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'http://localhost:8080/users/health-data/getByUserId/' + getUserId(),
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer ${getToken()}`
            },
            success: function (data) {
                resolve(data);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}


// Validate input with digits
function validateInput(input) {
    // Delete all symbols except digits
    input.value = input.value.replace(/[^0-9]/g, '');

    // Set min value 0
    if (parseInt(input.value) < 0) {
        input.value = 0;
    }
}


// Processing the page events
$(document).ready(function () {
    redirectAuthenticatedUsers();

    if (getUserId() == null && getEmail() != null) {
        getUserIdResponse();
    }

    // Sign in
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
                window.location.href = '../home.html';
            } else {
                const errorData = await response.json();
                $('#error-message').html('Ошибка: ' + errorData.message).show();
            }
        } catch (error) {
            $('#error-message').html('Ошибка: ' + error.message).show();
        }
    });

    // Sign up
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
                window.location.href = '../signin.html';

            } else {
                const errorData = await response.json();
                $('#error-message').html('Error: ' + errorData.error).show();
            }
        } catch (error) {
                $('#error-message').html('Error: ' + error.message).show();
        }
    });

    // Button: delete token from browser
    $('#delete-jwt').on('click', async function () {
        await deleteToken();
    });

    // If the user had authenticated, it will be show his information
    if (document.location.href === "http://localhost:63342/health-tracker/frontend/src/main/resources/templates/pages/my-data.html") {
        const userDataContainer = document.getElementById('user-data-container');
        const createHealthDataContainer = document.getElementById('create-health-data-container');
        const getHealthDataContainer = document.getElementById('get-health-data-container');

        const divUserInfo = document.createElement('div');
        divUserInfo.setAttribute('class', 'user-info');

        const divCreateHealthDataElements = document.getElementById('create-health-data-elements');
        const divNotAuth = document.createElement('not-auth');

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
                            <h3>Name: ${data.name}</h3>                      
                            <h3>Birth date: ${data.birthDate}</h3>
                            <h3>Email: ${data.email} </h3>
                            <h3>Weight: ${data.weight}</h3>
                            <h3>Height: ${data.height}</h3>
                    `;
                    userDataContainer.appendChild(divUserInfo);
                }
            });

            // Show the create-health-data div
            divCreateHealthDataElements.style.display = "block";

            // Get list of the health data
            getUserHealthDataResponse()
                .then(data => {
                    data.slice().reverse().forEach(function(item) {
                        console.log(item)
                        const divHealthData = document.createElement('div');
                        divHealthData.setAttribute('class', 'health-data');

                        divHealthData.innerHTML = `
                        <h3>Date: ${item.date.replace('T', ' ')}</h3> 
                        <h3>Steps: ${item.steps}</h3> 
                        <h3>Calories: ${item.calories}</h3> 
                        <h3>Sleep Hours: ${item.sleepHours}</h3> 
                        <h3>Heart Rate: ${item.heartRate}</h3> 
                        <div>
                        <!--TODO -->
                        <!-- <button class="update-health-data-btn" value="${item.id}" onclick="updateHealthData(this.value)" type="button">Update</button>-->
                            <button class="delete-health-data-btn" value="${item.id}" onclick="deleteHealthData(this.value)" type="button">Delete</button>
                        </div>
                    `;
                        getHealthDataContainer.appendChild(divHealthData);
                    });
                })
                .catch(error => {
                    console.error("Error fetching data:", error);
                });

        } else {
            divUserInfo.innerHTML = `
                    <h3>You're not authenticated</h3>            
            `;
            userDataContainer.appendChild(divUserInfo);

            divCreateHealthDataElements.style.display = "none";

            divNotAuth.innerHTML = `
                    <h3>You're not authenticated</h3>            
            `;
            createHealthDataContainer.appendChild(divNotAuth);
        }
    }

    // Create health-data
    $('.create-health-data-form').on('submit', async function (event) {
        event.preventDefault();  // Предотвращает обновление страницы

        const createHealthDataDialog = document.getElementById('create-health-data-dialog');
        const errorCreateHealthData = document.getElementById('error-create-health-data');

        const dateInput = $('#date-input').val();
        const stepsInput = $('#steps-input').val();
        const caloriesInput = $('#calories-input').val();
        const sleepHoursInput = $('#sleepHours-input').val();
        const heartRateInput = $('#heartRate-input').val();

        $.ajax({
            url: 'http://localhost:8080/users/health-data/add',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer ${getToken()}`
            },
            dataType: "json",
            data: JSON.stringify({
                userId: parseInt(getUserId()),
                date: dateInput,
                steps: stepsInput,
                calories: caloriesInput,
                sleepHours: sleepHoursInput,
                heartRate: heartRateInput
            }),
            success: function (data) {
                console.log("Create health-data: " + data);
                createHealthDataDialog.close();
                location.reload();
            },
            error: function (error) {
                console.log("Error: create health-data - status - " + error.status);
                errorCreateHealthData.innerHTML = `
                    <h2>Error: status - ${error.status}</h2>
                `;
                errorCreateHealthData.style.display = "block";
            }
        });

    });
});

