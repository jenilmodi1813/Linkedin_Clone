 # LinkedIn

The LinkedIn Clone backend is a scalable microservices-based application built using Spring Boot, designed to replicate core LinkedIn functionalities. It consists of independent services managing authentication, user profiles, company and job postings, and social posts. 

Each microservice handles specific responsibilities, communicating via REST APIs to provide seamless user experiences like user registration, job searching, networking, and posting updates.

Authentication is managed via an active session flag rather than traditional tokens, emphasizing simplicity and session control. This modular architecture enables easy maintenance, scalability, and independent deployment of services.

---

# LinkedIn Clone – Auth Service

🧾 **Overview**
This microservice handles user registration, login, logout, and session management. Instead of JWT tokens, it uses an `isActive` flag to track if a user is logged in or not.

📘 **Base URL**
`/auth`

📌 **API Endpoints**

---

### 1. Register User

**POST /auth/register**
Registers a new user with the default role USER.

**Request Body:**

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "Password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:**
`200 OK`

```
"User registered successfully"
```

---

### 2. Login User

**POST /auth/login**
Logs in a user, sets `isActive` to true.

**Request Body:**

```json
{
  "email": "john@example.com",
  "password": "Password123"
}
```

**Response:**
`200 OK`

```json
{
  "userId": "12345",
  "isActive": true,
  "message": "User logged in successfully"
}
```

---

### 3. Logout User

**POST /auth/logout**
Logs out the user by setting `isActive` to false.

**Request Body:**

```json
{
  "userId": "12345"
}
```

**Response:**
`200 OK`

```
"User logged out successfully"
```

---

### 4. Check if User is Active

**GET /auth/isActive/{userId}**
Returns whether the user is currently logged in.

**Response:**
`200 OK`

```json
{
  "userId": "12345",
  "isActive": true
}
```

---

# LinkedIn Clone – User Service

🧾 **Overview**
Manages user profiles, connections (followers/following), and exposes user information for other services.

📘 **Base URL**
`/users`

📌 **API Endpoints**

---

### 1. Create User Profile

**POST /users**
Creates a new user profile.

**Request Body:**

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Software Developer"
}
```

**Response:**
`201 Created`

```json
{
  "userId": "12345",
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Software Developer"
}
```

---

### 2. Get User Profile by ID

**GET /users/{userId}**
Fetches the user profile by their userId.

**Response:**
`200 OK`

```json
{
  "userId": "12345",
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Software Developer"
}
```

---

### 3. Update User Profile

**PUT /users/{userId}**
Updates user profile information.

**Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Senior Software Developer"
}
```

**Response:**
`200 OK`

```json
{
  "userId": "12345",
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Senior Software Developer"
}
```

---

### 4. Delete User Profile

**DELETE /users/{userId}**
Deletes the user profile.

**Response:**
`200 OK`

```
"User deleted successfully"
```

---

### 5. Follow Another User

**POST /users/{userId}/follow/{targetUserId}**
Current user follows another user.

**Response:**
`200 OK`

```
"Successfully followed user"
```

---

### 6. Unfollow a User

**DELETE /users/{userId}/unfollow/{targetUserId}**
Current user unfollows another user.

**Response:**
`200 OK`

```
"Successfully unfollowed user"
```

---

### 7. Get User Connections (Following List)

**GET /users/{userId}/connections**
Fetches a list of users that the current user is following.

**Response:**
`200 OK`

```json
[
  {
    "userId": "67890",
    "username": "jane_smith",
    "firstName": "Jane",
    "lastName": "Smith"
  },
  {
    "userId": "11223",
    "username": "bob_lee",
    "firstName": "Bob",
    "lastName": "Lee"
  }
]
```

---

# LinkedIn Clone – Company Service

🧾 **Overview**
Handles company data, job postings, and job categories. Uses Feign clients to communicate with User Service for recruiter info.

📘 **Base URL**
`/companies`

📌 **API Endpoints**

---

### 1. Create Company

**POST /companies**
Adds a new company.

**Request Body:**

```json
{
  "name": "Tech Solutions",
  "description": "A leading tech company.",
  "website": "https://techsolutions.com"
}
```

**Response:**
`201 Created`

```json
{
  "companyId": "54321",
  "name": "Tech Solutions",
  "description": "A leading tech company.",
  "website": "https://techsolutions.com"
}
```

---

### 2. Get Company Details

**GET /companies/{companyId}**
Fetch details for a specific company.

**Response:**
`200 OK`

```json
{
  "companyId": "54321",
  "name": "Tech Solutions",
  "description": "A leading tech company.",
  "website": "https://techsolutions.com"
}
```

---

### 3. Update Company

**PUT /companies/{companyId}**
Updates company information.

**Request Body:**

```json
{
  "name": "Tech Solutions Inc.",
  "description": "Leading tech company with global presence.",
  "website": "https://techsolutions.com"
}
```

**Response:**
`200 OK`

```json
{
  "companyId": "54321",
  "name": "Tech Solutions Inc.",
  "description": "Leading tech company with global presence.",
  "website": "https://techsolutions.com"
}
```

---

### 4. Delete Company

**DELETE /companies/{companyId}**
Deletes a company.

**Response:**
`200 OK`

```
"Company deleted successfully"
```

---

### 5. Create Job Posting

**POST /jobs**
Creates a new job posting linked to a company and category.

**Request Body:**

```json
{
  "title": "Senior Java Developer",
  "description": "Develop enterprise-level applications.",
  "companyId": "54321",
  "categoryId": "1001"
}
```

**Response:**
`201 Created`

```json
{
  "jobId": "98765",
  "title": "Senior Java Developer",
  "companyId": "54321",
  "categoryId": "1001"
}
```

---

### 6. Get Job Details

**GET /jobs/{jobId}**
Get details of a specific job.

**Response:**
`200 OK`

```json
{
  "jobId": "98765",
  "title": "Senior Java Developer",
  "description": "Develop enterprise-level applications.",
  "companyId": "54321",
  "categoryId": "1001"
}
```

---

### 7. Update Job Posting

**PUT /jobs/{jobId}**
Update job details.

**Request Body:**

```json
{
  "title": "Lead Java Developer",
  "description": "Lead development teams.",
  "categoryId": "1002"
}
```

**Response:**
`200 OK`

```json
{
  "jobId": "98765",
  "title": "Lead Java Developer",
  "description": "Lead development teams.",
  "categoryId": "1002"
}
```

---

### 8. Delete Job Posting

**DELETE /jobs/{jobId}**
Deletes a job posting.

**Response:**
`200 OK`

```
"Job deleted successfully"
```

---

### 9. List Jobs by Company

**GET /companies/{companyId}/jobs**
Get all job postings for a company.

**Response:**
`200 OK`

```json
[
  {
    "jobId": "98765",
    "title": "Senior Java Developer"
  },
  {
    "jobId": "87654",
    "title": "Frontend Developer"
  }
]
```

---

### 10. Create Job Category

**POST /categories**
Creates a new job category.

**Request Body:**

```json
{
  "name": "Software Development",
  "description": "Jobs related to software development"
}
```

**Response:**
`201 Created`

```json
{
  "categoryId": "1001",
  "name": "Software Development",
  "description": "Jobs related to software development"
}
```

---

### 11. List Job Categories

**GET /categories**
Get all job categories.

**Response:**
`200 OK`

```json
[
  {
    "categoryId": "1001",
    "name": "Software Development"
  },
  {
    "categoryId": "1002",
    "name": "Design"
  }
]
```

---

### 12. Update Job Category

**PUT /categories/{categoryId}**
Update job category info.

**Request Body:**

```json
{
  "name": "Software Engineering",
  "description": "Engineering related software jobs"
}
```

**Response:**
`200 OK`

```json
{
  "categoryId": "1001",
  "name": "Software Engineering",
  "description": "Engineering related software jobs"
}
```

---

### 13. Delete Job Category

**DELETE /categories/{categoryId}**
Deletes a job category.

**Response:**
`200 OK`

```
"Category deleted successfully"
```

---

# LinkedIn Clone – Post Service

🧾 **Overview**
Manages user posts, comments, likes, and feeds.

📘 **Base URL**
`/posts`

📌 **API Endpoints**

---

### 1. Create Post

**POST /posts**
Creates a new user post.

**Request Body:**

```json
{
  "userId": "12345",
  "content": "Excited to start my new job!",
  "visibility": "PUBLIC"
}
```

**Response:**
`201 Created`

```json
{
  "postId": "55555",
  "userId": "12345",
  "content": "Excited to start my new job!",
  "visibility": "PUBLIC",
  "createdAt": "2025-06-02T12:34:56Z"
}
```

---

### 2. Get Post by ID

**GET /posts/{postId}**
Fetches a post by its ID.

**Response:**
`200 OK`

```json
{
  "postId": "55555",
  "userId": "12345",
  "content": "Excited to start my new job!",
  "visibility": "PUBLIC",
  "createdAt": "2025-06-02T12:34:56Z"
}
```

---

### 3. Update Post

**PUT /posts/{postId}**
Update post content or visibility.

**Request Body:**

```json
{
  "content": "Excited to start my new job at Tech Solutions!",
  "visibility": "CONNECTIONS"
}
```

**Response:**
`200 OK`

```json
{
  "postId": "55555",
  "content": "Excited to start my new job at Tech Solutions!",
  "visibility": "CONNECTIONS"
}
```

---

### 4. Delete Post

**DELETE /posts/{postId}**
Deletes a post.

**Response:**
`200 OK`

```
"Post deleted successfully"
```

---

### 5. Get Feed for User

**GET /posts/feed/{userId}**
Gets posts visible to the user (their own and connections' posts).

**Response:**
`200 OK`

```json
[
  {
    "postId": "55555",
    "userId": "12345",
    "content": "Excited to start my new job!",
    "visibility": "PUBLIC",
    "createdAt": "2025-06-02T12:34:56Z"
  },
  {
    "postId": "55556",
    "userId": "67890",
    "content": "Looking forward to connecting with new professionals!",
    "visibility": "CONNECTIONS",
    "createdAt": "2025-06-01T10:00:00Z"
  }
]
```

---


 ### 🔹 Database Diagram
![Untitled](https://github.com/user-attachments/assets/d0f9fd6b-819c-4890-9c4e-bf0fcf244c2e)
