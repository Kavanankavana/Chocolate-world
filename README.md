# Chocolate World 🍫

A Spring Boot + Hibernate (JPA) website about handcrafted chocolates from around the world with user authentication and a MySQL database.

---

## Tech Stack
- **Backend:** Java 21, Spring Boot 3.3.5, Spring Data JPA (Hibernate)
- **Database:** MySQL Server 8.0 (`chocolatedb`)
- **Frontend / UI:** Thymeleaf, HTML5, CSS3 (responsive chocolate theme)

---

## Database Configuration (MySQL)
Configured in `src/main/resources/application.properties`:
- **Database:** `chocolatedb`
- **Host / Port:** `localhost:3306`
- **Username:** `root`
- **Password:** `tiger`
- **Hibernate DDL:** `update` (auto-creates and maintains tables)

---

## Authentication & Demo Accounts
Visit `/login` or `/register` to sign in or create an account.

| Username | Password | Role | Description |
| :--- | :--- | :--- | :--- |
| `admin` | `password123` | Administrator | Chocolatier Admin |
| `user` | `password123` | User | Cocoa Lover |

---

## Running the Application

### Option A: Using the Maven Wrapper (Recommended)
```cmd
.\mvnw.cmd spring-boot:run
```

### Option B: Using Installed Maven
```cmd
mvn spring-boot:run
```

Once started, open [http://localhost:8080](http://localhost:8080) in your browser.
- **Home:** [http://localhost:8080/](http://localhost:8080/)
- **Login:** [http://localhost:8080/login](http://localhost:8080/login)
- **Register:** [http://localhost:8080/register](http://localhost:8080/register)
- **Add Chocolate:** [http://localhost:8080/chocolates/new](http://localhost:8080/chocolates/new)
