// Функция для сохранения токена
function saveToken(token) {
    localStorage.setItem('authToken', token);
}

// Функция для получения токена
function getToken() {
    return localStorage.getItem('authToken');
}

// Функция для выполнения запроса с токеном
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

// Функция для получения всех пользователей и отображения их в элементе all-users
async function getAllUsers() {
    try {
        const response = await fetchWithAuth('http://localhost:8080/users', {
            method: 'GET',
        });

        if (response.ok) {
            const data = await response.json();
            const allUsersDiv = $('#all-users');
            allUsersDiv.append('<pre>' + JSON.stringify(data, null, 2) + '</pre>');
        } else {
            console.error('Failed to fetch users:', response.statusText);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Обработка события формы входа
$(document).ready(function () {
    $('#signin-form').on('submit', async function (event) {
        event.preventDefault();  // Предотвращает обновление страницы

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
                const data = await response.json();
                $('#signin-response').html('<p>Sign in successful!</p>');
                saveToken(data.token);  // Сохраните токен, который вы получили от сервера
                console.log(data);
            } else {
                const errorData = await response.json();
                $('#signin-response').html('<p>Sign in failed: ' + errorData.message + '</p>');
            }
        } catch (error) {
            $('#signin-response').html('<p>Error: ' + error.message + '</p>');
        }
    });

    // Обработка нажатия кнопки для получения всех пользователей
    $('#get-users').on('click', async function () {
        await getAllUsers();
    });
});





// $(document).ready(function () {
//     $('#signin-form').on('submit', async function (event) {
//         event.preventDefault();  // Предотвращает обновление страницы
//
//         const emailInput = $('#email-input').val();
//         const passwordInput = $('#password-input').val();
//
//         try {
//             const response = await fetch('http://localhost:8080/auth/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 body: JSON.stringify({
//                     email: emailInput,
//                     password: passwordInput
//                 })
//             });
//
//             if (response.ok) {
//                 const data = await response.json();
//                 $('#signin-response').html('<p>Sign in successful!</p>');
//                 saveToken(data.token);
//                 console.log(data);
//                 console.log("ТЕКСТ ТОКЕНА:" + data.token);
//             } else {
//                 const errorData = await response.json();
//                 console.log(data);
//                 $('#signin-response').html('<p>Sign in failed: ' + errorData.message + '</p>');
//             }
//         } catch (error) {
//             $('#signin-response').html('<p>Error: ' + error.message + '</p>');
//         }
//     });
// });
//
// // Сохранение токена
// function saveToken(token) {
//     localStorage.setItem('authToken', token);
// }
//
// function getToken() {
//     return localStorage.getItem('authToken');
// }
//
// async function fetchWithAuth(url, options = {}) {
//     const token = getToken();
//     if (!options.headers) {
//         options.headers = {};
//     }
//     if (token) {
//         options.headers['Authorization'] = `Bearer ${token}`;
//     }
//
//     const response = await fetch(url, options);
//     return response;
// }
//
// // Пример использования fetchWithAuth для последующих запросов
// async function getUserProfile() {
//     try {
//         const response = await fetchWithAuth('http://localhost:8080/users', {
//             method: 'GET',
//         });
//
//         if (response.ok) {
//             const data = await response.json();
//             console.log('User Profile:', data);
//         } else {
//             console.error('Failed to fetch user profile:', response.statusText);
//         }
//     } catch (error) {
//         console.error('Error:', error);
//     }
// }
//
