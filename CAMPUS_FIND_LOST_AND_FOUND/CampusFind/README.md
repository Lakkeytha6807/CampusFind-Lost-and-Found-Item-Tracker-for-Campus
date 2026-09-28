# CampusFind - Lost and Found Item Tracker

## Requirements
- Java 17+
- Maven
- MySQL
- IntelliJ IDEA / Eclipse / VS Code

## 1. Create database
MySQL can create the database automatically because the JDBC URL contains:
createDatabaseIfNotExist=true

## 2. Configure MySQL
Open:
src/main/resources/application.properties

Change:
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

## 3. Run
From the project folder:

mvn spring-boot:run

Or run CampusFindApplication.java from your IDE.

## 4. Open frontend
http://localhost:8080/

## Main APIs
POST /api/auth/register
POST /api/auth/login
POST /api/lost
GET /api/lost
POST /api/found
GET /api/found
PUT /api/found/{id}/status
GET /api/matches/{lostId}
GET /api/admin/dashboard
GET /api/categories
POST /api/categories

## Important business rules
1. A found item starts as AVAILABLE.
2. AVAILABLE cannot directly become RETURNED.
3. It must first become CLAIMED.
4. Only the reporting staff member or an ADMIN can change found-item status.
5. Rules are checked inside the service layer before saving.

## Important note
This is a simple academic project. The login demonstrates registration/password checking, but it does not implement production authentication such as JWT/session authorization. For a college mini-project this keeps the code easy to understand.
