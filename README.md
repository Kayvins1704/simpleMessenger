# Getting Started

### Steps to follow to run the project

* Run the following command in the base location of the project 
  * docker compose up simple-messenger --build
* Hit localhost:8090/swagger-ui/index.html to access swagger link
* To stop the container
  * docker compose down

### Curls

* Signup
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/auth/signup' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "vinit20",
    "passcode": "vinit20"
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "CREATED",
    "message": "User signup is successful",
    "data": {
    "username": "vinit20"
    }
    }
* Login : 
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/auth/login' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "vinit20",
    "passcode": "vinit20"
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "User login is successful",
    "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDIwIiwiaWF0IjoxNzA5NTY1OTc0LCJleHAiOjE3MDk2NTIzNzR9.AMSN4qM5eUFr0nl01eye-wFxqt8UXQClsw5W7Nu9rNk"
    }
    }
* Logout : 
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/auth/logout' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "vinit20"
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "User logout is successful"
    }
* Send one on one message : 
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/user/vinit20/message' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDIwIiwiaWF0IjoxNzA5NTY5MzI0LCJleHAiOjE3MDk2NTU3MjR9.7NMKob6RVziRIKkguyafMCgMv29wMA4QVwQIBOx_4k8' \
    -H 'Content-Type: application/json' \
    -d '{
    "to": "vinit1",
    "message": "Hello"
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Message sent successfully"
    }
* Read Unread Messages (Messages once received in the response are marked as read, in the subsequent API call, these won't be displayed again. Messages are in ascending order of arrival from each person)
  * Request : \
    curl -X 'GET' \
    'http://localhost:8090/user/vinit1/message' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDIwIiwiaWF0IjoxNzA5NTcwOTQ1LCJleHAiOjE3MDk2NTczNDV9.LCH68KzUQMvdPf7BYb4k4s2kPCF53SuU7l5rsnoYp4g'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Unread messages fetched successfully",
    "data": [
    {
    "fromUser": "vinit10",
    "texts": [
    "Hi6",
    "Hi7"
    ]
    },
    {
    "fromUser": "vinit20",
    "texts": [
    "Hi1",
    "Hi2",
    "Hi3"
    ]
    },
    {
    "fromUser": "vinit2",
    "texts": [
    "Hi4",
    "Hi5"
    ]
    }
    ]
    }
* Chat History between 2 people (Only displays the chat history between 2 people, doesn't mark them as read)
  * Request : \
    curl -X 'GET' \
    'http://localhost:8090/user/vinit1/message/chatHistory?friend=vinit20' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDIwIiwiaWF0IjoxNzA5NTcwOTQ1LCJleHAiOjE3MDk2NTczNDV9.LCH68KzUQMvdPf7BYb4k4s2kPCF53SuU7l5rsnoYp4g'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Chat History fetched successfully",
    "data": [
    {
    "username": "vinit20",
    "text": "Hi"
    },
    {
    "username": "vinit20",
    "text": "Hello"
    },
    {
    "username": "vinit20",
    "text": "Hello"
    },
    {
    "username": "vinit20",
    "text": "Hello"
    },
    {
    "username": "vinit20",
    "text": "Hello"
    },
    {
    "username": "vinit20",
    "text": "Hi"
    },
    {
    "username": "vinit20",
    "text": "Hi1"
    },
    {
    "username": "vinit20",
    "text": "Hi2"
    },
    {
    "username": "vinit20",
    "text": "Hi3"
    },
    {
    "username": "vinit1",
    "text": "Hi8"
    },
    {
    "username": "vinit1",
    "text": "Hi9"
    },
    {
    "username": "vinit1",
    "text": "Hi10"
    },
    {
    "username": "vinit20",
    "text": "Hru1"
    }
    ]
    }
* Get All users currently in the system
  * Request : \
    curl -X 'GET' \
    'http://localhost:8090/user' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDIwIiwiaWF0IjoxNzA5NTcwOTQ1LCJleHAiOjE3MDk2NTczNDV9.LCH68KzUQMvdPf7BYb4k4s2kPCF53SuU7l5rsnoYp4g'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "All users fetched successfully",
    "data": [
    {
    "username": "vinit"
    },
    {
    "username": "vinit1"
    },
    {
    "username": "vinit2"
    },
    {
    "username": "vinit10"
    },
    {
    "username": "vinit20"
    }
    ]
    }
* Create a new group
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/group/createGroup' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDEwIiwiaWF0IjoxNzA5NTc5OTgyLCJleHAiOjE3MDk2NjYzODJ9.lWJAQoAnw8DlBGW5Z52tEJs9wPDhZ0Gn0Yfz4kwg20A' \
    -H 'Content-Type: application/json' \
    -d '{
    "groupName": "group2",
    "usernameList": [
    "vinit1","vinit20", "vinit2"
    ]
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Group created successfully"
    }
* Fetch all users in a group
  * Request : \
    curl -X 'GET' \
    'http://localhost:8090/group/fetchAllUsers/group2' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDEwIiwiaWF0IjoxNzA5NTc5OTgyLCJleHAiOjE3MDk2NjYzODJ9.lWJAQoAnw8DlBGW5Z52tEJs9wPDhZ0Gn0Yfz4kwg20A'
  * Response :
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "All users fetched successfully",
    "data": {
    "groupName": "group2",
    "username": [
    "vinit1",
    "vinit2",
    "vinit20"
    ]
    }
    }
* Send message in a group
  * Request : \
    curl -X 'POST' \
    'http://localhost:8090/user/vinit2/groupMessage' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDEwIiwiaWF0IjoxNzA5NTc5OTgyLCJleHAiOjE3MDk2NjYzODJ9.lWJAQoAnw8DlBGW5Z52tEJs9wPDhZ0Gn0Yfz4kwg20A' \
    -H 'Content-Type: application/json' \
    -d '{
    "text": "Wassup ?",
    "groupName": "group1"
    }'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Message sent successfully"
    }
* Get Chat History In a group
  * Request : \
    curl -X 'GET' \
    'http://localhost:8090/user/group1/groupMessage/chatHistoryInGroup' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5pdDEwIiwiaWF0IjoxNzA5NTc5OTgyLCJleHAiOjE3MDk2NjYzODJ9.lWJAQoAnw8DlBGW5Z52tEJs9wPDhZ0Gn0Yfz4kwg20A'
  * Response : \
    {
    "status": "SUCCESS",
    "statusCode": "OK",
    "message": "Chat History fetched successfully",
    "data": [
    {
    "username": "vinit1",
    "text": "Wassup ?"
    },
    {
    "username": "vinit2",
    "text": "Wassup ?"
    }
    ]
    }

      

