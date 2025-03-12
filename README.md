# 📚 Library Management System  

A **Spring Boot-based Library Management System** that allows users to **manage books, register patrons, and track borrowing records**.  

---

## 🚀 Features  

✅ **Manage Books**: Add, update, and delete books 📖  
✅ **Patron Management**: Register and manage patrons 👥  
✅ **Track Borrowing**: Keep track of book borrowing & return dates ⏳  
✅ **RESTful API**: API endpoints for seamless integration 🌐  

---

## 🛠️ Installation & Setup  

1️⃣ Clone the repository  
```bash
git clone https://github.com/manaraljarkas/Library_Management.git
cd Library_Management
```
2️⃣ Install dependencies
```bash
mvn install
```
3️⃣ Run the application
```bash
mvn spring-boot:run
```
The application should now be running at http://localhost:8080.


📌 API Endpoints\
📖 Book Management Endpoints\
Method	Endpoint	Description\
GET	/api/books	Retrieve all books\
GET	/api/books/{id}	Get book details by ID\
POST	/api/books	Add a new book\
PUT	/api/books/{id}	Update book information\
DELETE	/api/books/{id}	Remove a book from the library\
\
👥 Patron Management Endpoints\
Method	Endpoint	Description\
GET	/api/patrons	Retrieve all patrons\
GET	/api/patrons/{id}	Get patron details by ID\
POST	/api/patrons	Register a new patron\
PUT	/api/patrons/{id}	Update patron information\
DELETE	/api/patrons/{id}	Remove a patron from the system\
\
🔄 Borrowing & Returning Books\
Method	Endpoint	Description\
POST	/api/borrow/{bookId}/patron/{patronId}	Record a book borrowing\
PUT	/api/return/{bookId}/patron/{patronId}	Record the return of a borrowed book\
\
📖 For full API documentation, see [API_DOCUMENTATION.md](https://github.com/manaraljarkas/Library_Management/blob/main/API_DOCUMENTATION.md).\
\

🧪 Running Tests\
📌 Testing Frameworks Used\
    JUnit 5 – Unit testing framework\
    Mockito – Mocking dependencies for isolated tests\
    SpringBootTest – Integration testing\
\
🚀 How to Run Tests\

Run the following command to execute all tests:\

```bash
mvn test
```

or if using Gradle:\
```bash
./gradlew test
```
📂 Test Locations\
    Controller Tests: src/test/java/com/librarymanagement/library_management/controller/\
    Service Tests: src/test/java/com/librarymanagement/library_management/service/\
\
✅ Expected Output\
\
If all tests pass, you'll see:\
```bash
[INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
If a test fails, check the error details for debugging.\
\
🔗 Technologies Used:\
    Spring Boot - Backend framework\
    JPA (Hibernate) - Database management\
    MySQL - Database\
    Lombok - Reduces boilerplate code\
\
📜 License\
\
This project is open-source and available under the MIT License.
