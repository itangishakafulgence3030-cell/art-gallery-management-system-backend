# Art Gallery Management System Backend

Student: ITANGISHAKA Fulgence
Student ID: 26963
Project: Art Gallery Management System

This project is the backend for the mid-term exam described in the course document. It follows the same clinic-style architecture used in the Kigali Clinic API project, but with the Art Gallery domain required by the assignment.

## Backend stack
- Java 21
- Spring Boot 3.3.5
- Spring Data JPA
- H2 database (default local testing mode)
- PostgreSQL driver included for the required exam setup

## Main domain entities
- ArtStyle
- Hall
- Curator
- Artist
- Exhibition
- ArtworkSale

## API notes
The project includes the required CRUD endpoints, derived queries, JPQL queries, business-rule validations, artwork sale logic, dashboard summary, and pagination endpoints described in the assignment.

## Run locally
1. Open a terminal in the project root.
2. Run:
   ```bash
   mvn spring-boot:run
   ```
3. Open the app at:
   ```text
   http://localhost:8080
   ```

## PostgreSQL option
The project includes the PostgreSQL dependency and the exam specification expects PostgreSQL in the lecture environment. If you want to switch to PostgreSQL, update the datasource settings in `src/main/resources/application.properties`.

## Project structure
- `src/main/java/kigali/gallery/rw/domain` — entity classes
- `src/main/java/kigali/gallery/rw/repository` — repository layer
- `src/main/java/kigali/gallery/rw/service` — service logic
- `src/main/java/kigali/gallery/rw/controller` — REST endpoints
- `src/main/java/kigali/gallery/rw/config` — seed data
