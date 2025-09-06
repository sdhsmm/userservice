# Backend Application

This repository contains the backend service for managing users, authentication, and profiles. It is built with **Spring Boot** (Kotlin/Java) and uses **JWT-based authentication** for secure API access.

---

## 🚀 Tech Stack

* **Language:** Kotlin / Java
* **Framework:** Spring Boot
* **Database:** H2 (configurable)
* **Authentication:** JWT (JSON Web Token)
* **Build Tool:** Gradle
* **Containerization:** Docker (optional)

---

## ⚙️ Setup Instructions

1. **Clone the repository**

   ```bash
   https://github.com/sdhsmm/userservice.git
   cd your-backend-app
   ```

2. **Configure Environment Variables**
   Create an `.env` or configure `application.yml` with the following:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/yourdb
       username: dbuser
       password: dbpassword
     jpa:
       hibernate:
         ddl-auto: update
   jwt:
     secret: your-secret-key
     expiration: 86400000 # 1 day in ms
   ```

3. **Build & Run**

   ```bash
   ./gradlew bootRun
   ```

## 📌 API Overview

* **POST /api/auth/signup** → Register a new user
* **POST /api/auth/login** → Authenticate and get JWT token
* **POST /api/auth/logout** → Invalidate active token (server-side tracking if enabled)
* **GET /api/user/profile** → Get logged-in user profile
* **PUT /api/user/profile** → Update profile details

API docs (Swagger) available at:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Important Notes

* All secured endpoints **require JWT token** in the `Authorization` header:

  ```
  Authorization: Bearer <token>
  ```
* **Never commit secrets** (JWT secret, DB password, API keys) into Git. Use environment variables or secret managers.
* Use **HTTPS** in production to secure token transmission.
* Logout implementation can be:

  * **Stateless** → Token expires automatically.
  * **Stateful** → Store blacklisted/active tokens in Redis or DB and invalidate on logout.
* Ensure **CORS policies** are configured if frontend is hosted on a different domain.
* For scaling, consider:

  * Connection pooling
  * Distributed cache (Redis) for sessions/token invalidation
  * Horizontal scaling with Kubernetes/Docker

---

## 🧪 Testing

Run tests with:

```bash
./gradlew test
```

or

```bash
mvn test
```

---

## 📂 Project Structure

```
src/main/kotlin/com/example/app
├── config        # Security and app configurations
├── controller    # REST controllers
├── dto           # Request/response DTOs
├── entity        # Database entities
├── repository    # JPA repositories
├── security      # JWT & auth logic
├── service       # Business services
└── utils         # Utility classes
```

---

## 🛠️ Contribution

1. Fork the repo
2. Create a feature branch (`feature/your-feature`)
3. Commit your changes
4. Create a pull request

---

## 📜 License

This project is licensed under the MIT License.

---

👉 Do you want me to keep the **README** more **developer-oriented** (for people working on this repo) or more **deployment-oriented** (for DevOps/infra people setting it up in production)?
