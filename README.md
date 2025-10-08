# 🚗 Vehicle Insurance Management System  

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-4CAF50?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)



> A **Full Stack Vehicle Insurance Management System** built using **ReactJS**, **Spring Boot**, and **MySQL** to manage clients, policies, and claims efficiently.

---

## 🧭 Table of Contents
- [✨ Overview](#-overview)
- [🧱 Features](#-features)
- [⚙️ Tech Stack](#️-tech-stack)
- [📂 Project Structure](#-project-structure)
- [🚀 Getting Started](#-getting-started)
- [💾 Database Configuration](#-database-configuration)
- [🖥️ Screenshots](#️-screenshots)
- [📜 License](#-license)
- [👨‍💻 Author](#-author)

---

## ✨ Overview
The **Vehicle Insurance Management System** is a web-based application that helps insurance companies and clients manage:
- Vehicle registrations  
- Policy creation & renewal  
- Claim processing   

Built with a **ReactJS frontend** and **Spring Boot backend**, this system offers a modern, responsive, and scalable solution.

---

## 🧱 Features

✅ **User Management** — Add, edit, or delete clients and agents.  
✅ **Vehicle Registration** — Maintain detailed vehicle records.  
✅ **Policy Handling** — Create, update, renew, or cancel policies.  
✅ **Claims Management** — Submit and review insurance claims.  
✅ **Secure Login** — Authentication and authorization using Spring Security *(optional if implemented)*.  
✅ **Responsive UI** — Fully responsive React frontend using Material UI / Bootstrap.  
✅ **REST APIs** — JSON-based communication between frontend and backend.  

---

## ⚙️ Tech Stack

### 💻 Frontend
- ReactJS
- Axios
- React Router
- HTML5 / CSS3 / JavaScript (ES6+)
- Bootstrap / Material UI

### 🔧 Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Web
- MySQL Driver
- Lombok

### 🗄️ Database
- MySQL

### 🛠️ Build Tools
- Maven
- npm / yarn

---

## 📂 Project Structure
```bash
vehicle-insurance-management/
│
├── backend/
│   ├── src/main/java/com/insurance/...
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── static/
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.js
│   ├── package.json
│   └── public/
│
└── README.md

🚀 Getting Started
🧩 1. Clone the repository
git clone https://github.com/<your-username>/<your-repo-name>.git
cd <your-repo-name>

---

⚙️ 2. Backend Setup
cd backend
mvn clean install


Start the server:

mvn spring-boot:run


Backend runs at 👉 http://localhost:8080

💻 3. Frontend Setup
cd ../frontend
npm install
npm start


Frontend runs at 👉 http://localhost:3000

🔐 Authentication

This app uses Spring Security + JWT to handle authentication and authorization.

Workflow:

User registers → credentials stored securely in DB (hashed passwords).

On login → server generates a JWT token.

Token is sent in headers (Authorization: Bearer <token>) for protected routes.

Backend validates token for each secured endpoint.

💾 Database Configuration

Edit backend/src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/fullstack_app
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080

Make sure MySQL database exists:

CREATE DATABASE fullstack_app;

👨‍💻 Author
Rajesh
💼 Full Stack Developer | ReactJS • Spring Boot • MySQL
📧 rajeshkoppuravuri007@gmail.com

