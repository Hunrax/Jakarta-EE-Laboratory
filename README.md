# Directors and Their Movies

## Author: Radosław Gajewski

## Project Overview

Project for "Jakarta EE Tools and Applications" Laboratory Classes. The project involves developing a Jakarta EE-based application and demonstrates the implementation of a management system for users, directors and their movies.

## Core Technologies and Features

- **Jakarta Servlet**: Used for handling HTTP requests and responses.
- **Jakarta Contexts and Dependency Injection (CDI)**: Facilitates dependency injection and contextual lifecycle management.
- **Jakarta Faces (JSF)**: Provides a component-based framework for building user interfaces.
- **Jakarta RESTful Web Services (JAX-RS)**: Enables the creation of RESTful APIs for the application.
- **Jakarta Persistence (JPA)**: Manages data persistence and interaction with the database.
- **Jakarta Enterprise Beans (EJB)**: Provides business logic and transaction management.
- **Jakarta Security**: Ensures secure authentication and authorization.

## Running the Application

Use the following Maven command to start the application:
   ```
   mvn -P liberty liberty:dev
   ```

The application will run on the default Open Liberty server and can be accessed via web browser.


## Features Overview

1. **User Management**
   - User registration and login.
   - Secure authentication using Jakarta Security.
   - Delete users (admin only).

2. **Director Management**
   - View the list of directors.
   - Add, edit and delete directors (admin only).

3. **Movie Management**
   - Add new movies with relevant details.
   - View, add, edit and delete movies added by yourself.
   - View, add, edit and delete any movie (admin only).

Available in two language versions: Polish and English.

![image](https://github.com/user-attachments/assets/5845a3ca-e641-4348-b780-acbac2a57335)
