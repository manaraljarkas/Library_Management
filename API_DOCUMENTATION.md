# 📌 API Documentation - Library Management System  

This document provides detailed information on the **RESTful API endpoints** available in the **Library Management System**.  

---

## 📖 Book Management Endpoints  

| **Method** | **Endpoint**         | **Description**                   | **Request Body (if applicable)**                                                                                                                       |
|------------|----------------------|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **GET**    | `/api/books`         | Retrieve a list of all books      | none                                                                                                                                                   |
| **GET**    | `/api/books/{id}`    | Retrieve details of a book by ID  | none                                                                                                                                                   |
| **POST**   | `/api/books`         | Add a new book                    | `{ "title": "Book Title", "description":"book description", "publicationYear": 2025, "author": "Author Name", "isbn": "123-456789", "loanPeriod": 15}` |
| **PUT**    | `/api/books/{id}`    | Update book details               | `{ "title": "New Title", "description":"new description", "publicationYear": 2023, "author": "New Author", "isbn": "123-456789", "loanPeriod": 20}`    |
| **DELETE** | `/api/books/{id}`    | Remove a book from the library    | none                                                                                                                                                   |

---

## 👥 Patron Management Endpoints  

| **Method** | **Endpoint**         | **Description**                  | **Request Body (if applicable)**                                                                                |
|------------|----------------------|----------------------------------|-----------------------------------------------------------------------------------------------------------------|
| **GET**    | `/api/patrons`       | Retrieve a list of all patrons   | none                                                                                                            |
| **GET**    | `/api/patrons/{id}`  | Retrieve patron details by ID    | none                                                                                                            |
| **POST**   | `/api/patrons`       | Add a new patron                 | `{"fullname": "Manar Aljarkas","phoneNumber": "1234567890","email": "test@example.com","password": "********"}` |
| **PUT**    | `/api/patrons/{id}`  | Update patron details            | `{"fullname": "newName","phoneNumber": "newPhoneNumber","email": "newEmail","password": "newPassword"}`         |
| **DELETE** | `/api/patrons/{id}`  | Remove a patron from the system  | none                              |

---

## 🔄 Borrowing & Returning Books  

| **Method** | **Endpoint**                                   | **Description**                   | **Request Body (if applicable)** |
|------------|------------------------------------------------|-----------------------------------|----------------------------------|
| **POST**   | `/api/borrow/{bookId}/patron/{patronId}`       | Borrow a book                     | none                             |
| **PUT**    | `/api/return/{bookId}/patron/{patronId}`       | Return a borrowed book            | none                             |


