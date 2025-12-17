# REST API - Spring Boot - MySQL

## Description - Exercise statement
This project implements a REST API to manage fruits and their providers using Spring Boot and MySQL.
Each fruit is associated with a provider, and the API allows registering, retrieving, updating, and deleting both fruits and providers.

## Technologies used
- Oracle OpenJDK 21.0.8
- Spring Boot 3.5.8
- Maven 3.9.11
- MySQL
- Docker
- Postman
- IntelliJ IDEA Community Edition

## Requirements
- Oracle OpenJDK 21.0.8
- Spring Boot 3.5.8
- Maven 3.9.11
- Docker
- Java IDE

## Installation
1. Clone repository (https://github.com/AlbertMedina/4.2-Spring-Framework-Basics-Nivell2.git).
```git clone https://github.com/AlbertMedina/4.2-Spring-Framework-Basics-Nivell2.git```
2. Navigate to project folder.
```cd fruit-api-MySQL```
3. Ensure Docker is installed and running

## Execution
1. Build and start the containers.
```docker-compose up --build```
This will start MongoDB and the Spring Boot API. The API will be available at http://localhost:8080.
2. Test endpoints with Postman or any HTTP client.
- Example endpoints:
  - POST / GET -> /providers
  - GET / PUT / DELETE -> /providers/{id}
  - 
- Example POST JSON:
```
{
  "customerName": "Joan",
  "deliveryDate": "2025-12-18",
  "items": [
    { "fruitName": "Apple", "quantityInKilos": 5 },
    { "fruitName": "Banana", "quantityInKilos": 3 }
  ]
}
```
