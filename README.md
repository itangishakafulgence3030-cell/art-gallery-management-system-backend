# Art Gallery Management System Backend

Student: ITANGISHAKA Fulgence
Student ID: 26963
Project: Art Gallery Management System

This project is the backend for the mid-term exam described in the course document. It follows the same clinic-style architecture used in the Kigali Clinic API project, but with the Art Gallery domain required by the assignment.

## Backend stack
- Java 21
- Spring Boot 3.3.5
- Spring Data JPA
- PostgreSQL
- H2 for automated tests

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
1. Create a PostgreSQL database named `art_gallery`.
2. Set `DB_PASSWORD` in PowerShell if your PostgreSQL user has a password:
   ```powershell
   $env:DB_PASSWORD = "your-postgres-password"
   ```
3. Open a terminal in the project root and run:
   ```bash
   mvn spring-boot:run
   ```
3. Open the app at:
   ```text
   http://localhost:8080
   ```

## Project structure
- `src/main/java/kigali/gallery/rw/domain` — entity classes
- `src/main/java/kigali/gallery/rw/repository` — repository layer
- `src/main/java/kigali/gallery/rw/service` — service logic
- `src/main/java/kigali/gallery/rw/controller` — REST endpoints
- `src/main/java/kigali/gallery/rw/config` — seed data
