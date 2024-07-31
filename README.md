# health-tracker
### 🦾 Tech Stack:
- Java 21
- Spring Boot
- Spring Security (JWT-authentication)
- Spring Cloud Gateway
- Kafka
- PostgreSQL
- Docker

# 🤔 About the project
Health-tracker is a microservices-based application designed to monitor users' health. The project consists of several services, including gateway-service, auth-service, and users-service.

As part of this project, 4 services have been developed, the names of which are: 
- gateway-service
- auth-service
- users-service
- analytics-service

# 🔑 Key Features:
- RESTful API
- Security: user authentication with JWT
- Frontend

# 🚀 Explore REST API

| Method | URL                                 | Description                                       | Valid Request Body                    | Valid Request Param                   | Response                                                              |
|--------|-------------------------------------|---------------------------------------------------|---------------------------------------|---------------------------------------|-----------------------------------------------------------------------|
| POST   | /auth/signup                        | register a new user                               | [info](#register-a-new-user)          |                                       | "CREATED" or [error](#register-error)                                 |
| POST   | /auth/signin                        | login                                             | [info](#login-to-account)             |                                       | [ok](#login) or [error](login-error)                                  |
| GET    | /users                              | get all users                                     |                                       |                                       | [info](#get-all-users)                                                |
| GET    | /users/{id}                         | get user by id                                    |                                       | [info](#get-user-by-id)               | [info](#get-user-by-id)                                               |
| GET    | /users/getUserByEmail/{email}       | get user by email                                 |                                       | [info](#get-user-by-email)            | [info](#get-user-by-email)                                            |
| POST   | /users/add                          | add a new user (it use auth-service for register) | [info](#register-a-new-user)          |                                       | "CREATED" or [error](#register-error)                                 |
| PATCH  | /users/{id}/update                  | update the user                                   | [info](#update-the-user)              | [info](#update-the-user)              | "OK"                                                                  |
| DELETE | /users/{id}/delete                  | delete the user                                   |                                       | [info](#delete-the-user)              | "OK" or [error](#delete-the-user-error)                               |
| GET    | /users/health-data                  | get all health-data                               |                                       |                                       | [info](#get-all-health-data)                                          |
| GET    | /users/health-data/{id}             | get the health-data by id                         |                                       | [info](#get-health-data-by-id)        | [ok](#get-health-data-by-id) or [error](#get-health-data-by-id-error) |
| GET    | /users/health-data/getByUserId/{id} | get the health-data by user id                    |                                       | [info](#get-health-data-by-user-id)   | [ok](#get-health-data-by-user-id)                                     |
| POST   | /users/health-data/add              | add a new health-data                             | [info](#add-a-new-health-data)        |                                       | "OK" or [error](#add-a-new-health-data-error)                         |
| PATCH  | /users/health-data/{id}/update      | update the health-data                            | [info](#update-the-health-data-by-id) | [info](#update-the-health-data-by-id) | "OK"                                                                  |
| DELETE | /users/health-data/{id}/delete      | delete the health-data by id                      |                                       | [info](#delete-the-health-data-by-id) | "OK" or [error](#delete-the-health-data-error)                        |
| GET    | /analytics                          | get all data from analytics                       |                                       |                                       | [info](#get-all-data-from-analytics)                                  |    
| GET    | /analytics/{id}                     | get user data from analytics                      |                                       | [info](#get-user-data-from-analytics) | [info](#get-user-data-from-analytics)                                 |    
| POST   | /analytics/add                      | add a new data for analytics                      | [info](#add-a-new-data-for-analytics) |                                       | "OK"                                                                  |    


## 📝 Note
The users-service and the analytics-service require a JWT, so you need to add the next to the request headers:
``` 
    Authorization: Bearer ${token}
```

# ✅ Valid Request Body and Valid Request Param

### Register a new user
http://localhost:8080/auth/signup

```json 
    {
        "name": "string",
        "birthDate": "2005-05-05",
        "email": "string",
        "password": "string",
        "weight": 0,
        "height": 0
    }
```

### Login to account 
http://localhost:8080/auth/signin

```json 
    {
        "email": "string",
        "password": "string"
    }
```

### Update the user
http://localhost:8080/users/{id}/update

```json
    {
        "name": "string",
        "birthDate": "2024-07-31",
        "email": "string",
        "password": "string",
        "weight": 0,
        "height": 0
    }
```

### Delete the user
http://localhost:8080/users/{id}/delete

### Add a new health-data
http://localhost:8080/users/health-data/add

```json
    {
        "userId": 0,
        "date": "2024-07-31T06:10",
        "steps": 0,
        "calories": 0,
        "sleepHours": 0,
        "heartRate": 0
    }
```

### Update the health-data by id
http://localhost:8080/users/health-data/{id}/update

```json
    {
        "userId": 0,
        "date": "2024-07-31T06:10",
        "steps": 0,
        "calories": 0,
        "sleepHours": 0,
        "heartRate": 0
    }
```

### Delete the health-data by id
http://localhost:8080/users/health-data/{id}/delete


### Add a new data for analytics
http://localhost:8080/analytics/add

```json
    {
        "userId": 0,
        "date": "2024-07-31T06:24",
        "steps": 0,
        "calories": 0,
        "sleepHours": 0,
        "heartRate": 0
    }
```



# 🙋 Response

### Login
```json
    {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbm5hQG1haWwucnUiLCJpc3MiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjI0MDQ2MDQsImV4cCI6MTcyMjQ5MTAwNH0.K0hiOBweI-yKFaORl_N1ML1EoZXunRsCHk7xVsQfqts"
    }
```

### Get all users
http://localhost:8080/users

```json 
  [
        {
            "id": 0,
            "name": "string",
            "birthDate": "2024-07-31",
            "email": "string",
            "password": "string",
            "weight": 0,
            "height": 0,
            "role": "USER",
            "healthDataList": [
              {
                "id": 0,
                "date": "2024-07-31T05:16",
                "steps": 0,
                "calories": 0,
                "sleepHours": 0,
                "heartRate": 0,
                "createdAt": 0,
                "updatedAt": 0
              }
            ],
            "createdAt": 0,
            "updatedAt": 0
        }
  ]
```

### Get user by id
http://localhost:8080/users/{id}

```json 
  {
      "id": 0,
      "name": "string",
      "birthDate": "2024-07-31",
      "email": "string",
      "password": "string",
      "weight": 0,
      "height": 0,
      "role": "USER",
      "healthDataList": [
        {
          "id": 0,
          "date": "2024-07-31T05:21",
          "steps": 0,
          "calories": 0,
          "sleepHours": 0,
          "heartRate": 0,
          "createdAt": 0,
          "updatedAt": 0
        }
      ],
      "createdAt": 0,
      "updatedAt": 0
  }
```

### Get user by email
http://localhost:8080/users/getUserByEmail/{email}

```json 
  {
      "id": 0,
      "name": "string",
      "birthDate": "2024-07-31",
      "email": "string",
      "password": "string",
      "weight": 0,
      "height": 0,
      "role": "USER",
      "healthDataList": [
        {
          "id": 0,
          "date": "2024-07-31T05:21",
          "steps": 0,
          "calories": 0,
          "sleepHours": 0,
          "heartRate": 0,
          "createdAt": 0,
          "updatedAt": 0
        }
      ],
      "createdAt": 0,
      "updatedAt": 0
  }
```

### Get all health-data
http://localhost:8080/users/health-data

```json
    [
        {
            "id": 0,
            "date": "2024-07-31T06:02",
            "steps": 0,
            "calories": 0,
            "sleepHours": 0,
            "heartRate": 0,
            "createdAt": 0,
            "updatedAt": 0
        }
    ]
```

### Get health-data by id
http://localhost:8080/users/health-data/{id}

```json
    {
        "id": 0,
        "date": "2024-07-31T06:02",
        "steps": 0,
        "calories": 0,
        "sleepHours": 0,
        "heartRate": 0,
        "createdAt": 0,
        "updatedAt": 0
    }
```

### Get health-data by user id
http://localhost:8080/users/health-data/getByUserId/{id}

```json
    [
        {
        "id": 0,
        "date": "2024-07-31T06:02",
        "steps": 0,
        "calories": 0,
        "sleepHours": 0,
        "heartRate": 0,
        "createdAt": 0,
        "updatedAt": 0
        }
    ]
```

### Get all data from analytics
http://localhost:8080/analytics

```json 
    [
        {
            "id": 1,
            "userId": 1,
            "totalSteps": 9000,
            "totalCalories": 2700,
            "totalSleepHours": 8,
            "totalHeartRate": 80,
            "averageHeartRate": 80,
            "countHeartRateDays": 1
        }
    ]
```

### Get user data from analytics
http://localhost:8080/analytics/{id}

```json
    {
        "id": 1,
        "userId": 1,
        "totalSteps": 9000,
        "totalCalories": 2700,
        "totalSleepHours": 8,
        "totalHeartRate": 80,
        "averageHeartRate": 80,
        "countHeartRateDays": 1
    }
```



# ❌ Error response

### Register error
```json
    {
        "error": "weight - The weight should be greater than 0; email - This email address is already in use; "
    }
```

### Login error
Incorrect email
```json
    {
        "error": "The user with this email=anna@mail.ru is not found"
    }
```

Incorrect password:
```json
    {
        "error": "Bad credentials"
    }
```

### Delete the user error
```json
    {
        "message": "The user with this id=9 is not found",
        "timestamp": 1722405606623
    }
```

### Get health-data by id error
```json
    {
        "message": "The health data with this id=0 isn't found",
        "timestamp": 1722407612025
    }
```

### Add a new health-data error
```json
    {
        "message": "sleepHours - The count of the sleep hours should be greater than 0; ",
        "timestamp": 1722408252292
    }
```

### Delete the health-data error
```json
    {
        "message": "The health data with this id=100 isn't found",
        "timestamp": 1722406534077
    }
```

# 🛠️ Run the App
## Local
1. 🧑‍💻 ️Clone project: ```git clone https://github.com/MiroslavMagnin/health-tracker.git```
2. 📁 Go to the project's home directory: ```cd health-tracker```
3. ⌨️ Run docker compose: ```docker-compose up```
4. ⌛ Wait while all images are built and all services are launching   
5. 📋 For swagger ui localhost:8080/{service-name}/swagger-ui/index.html   

# 📸 Screenshots

<details>
  <summary>👀 Look at this:</summary>

### Register and login
![image](frontend/src/main/resources/static/1.png)
![image](frontend/src/main/resources/static/2.png)

### My data
![image](frontend/src/main/resources/static/3.png)
![image](frontend/src/main/resources/static/4.png)
![image](frontend/src/main/resources/static/5.png)

### About
![image](frontend/src/main/resources/static/6.png)


### Postman
![image](frontend/src/main/resources/static/7.png)
![image](frontend/src/main/resources/static/8.png)
![image](frontend/src/main/resources/static/9.png)
![image](frontend/src/main/resources/static/10.png)

### Docker
![image](frontend/src/main/resources/static/11.png)
![image](frontend/src/main/resources/static/12.png)

### Swagger
![image](frontend/src/main/resources/static/13.png)
![image](frontend/src/main/resources/static/14.png)
![image](frontend/src/main/resources/static/15.png)

</details>
