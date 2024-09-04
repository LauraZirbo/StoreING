# StoreING

StoreING is a cake store management tool

This is a simple backend application for managing a cake store, developed using Java, Spring Boot, Maven. This project
also uses Docker and PostgreSQL as a database
The application provides basic functionality to manage products and orders within a cake store. This project is focused
on backend operations, with no frontend implementation.

## Features

- **Product Management**:
    - Add a new product to the store.
    - Find a product by its ID.
    - List all products.
    - Delete products.
    - Update products.

- **Order Management**:
    - Create a new order.
    - List all orders.
    - Find an order by its ID.
    - Update order status.
    - Delete orders.

- **Authentication and Role-Based Access** :
    - Basic authentication with role-based access control.
    - Users are assigned roles ( `MANAGER`, `EMPLOYEE`), and access to certain endpoints is restricted based on roles (
      DELETE for example, is a featured allowed only for manager).

- **Unit Testing**:
    - Unit tests provided for the Cake flow using JUnit.
    - Tests are run on their own environment using h2 database.
    - Test coverage for typical use cases and edge cases.

## Getting Started

Before starting the application, you need to start database PostgreSQL container of the project from local developer Docker run the following command from
project's src/main/docker folder docker-compose up -d


