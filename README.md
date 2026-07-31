# Hospital Management System (HMS)

A modular desktop-based Hospital Management System developed using **JavaFX**. The system is designed to simplify hospital operations by providing a structured platform for managing staff, patients, appointments, and other healthcare-related data.

This README is intended for developers who want to understand the project structure, setup the development environment, and contribute to the system.

---

## Table of Contents

* [Project Overview](#project-overview)
* [Technology Stack](#technology-stack)
* [System Architecture](#system-architecture)
* [Project Structure](#project-structure)
* [Development Setup](#development-setup)
* [Running the Application](#running-the-application)
* [Coding Guidelines](#coding-guidelines)
* [Module Explanation](#module-explanation)
* [Git Workflow](#git-workflow)
* [Future Improvements](#future-improvements)

---

# Project Overview

The Hospital Management System (HMS) is a JavaFX-based application developed to manage essential hospital operations through an intuitive graphical user interface.

The system follows a modular design approach, separating:

* User Interface (FXML + Controllers)
* Business Logic (Services)
* Data Models
* Application Context / Shared Data Management

The goal of this architecture is to improve maintainability, scalability, and collaboration between developers.

---

# Technology Stack

## Programming Language

* Java 21

## Framework

* JavaFX 17

## Development Tools

* Eclipse IDE
* Scene Builder (FXML Design)
* Git / GitHub

## Design Pattern

The project applies concepts from:

* Object-Oriented Programming (OOP)
* Model-View-Controller (MVC)
* Service Layer Architecture
* Modular Software Design

---

# System Architecture

The system follows an MVC-inspired architecture:

```
                User
                 |
                 v
        +----------------+
        | JavaFX UI      |
        | (FXML Views)   |
        +----------------+
                 |
                 v
        +----------------+
        | Controllers    |
        +----------------+
                 |
                 v
        +----------------+
        | Services       |
        +----------------+
                 |
                 v
        +----------------+
        | Models         |
        | (Data Objects) |
        +----------------+
```

### Model Layer

Responsible for representing application data.

Examples:

* Staff
* Patient
* Appointment
* Medical Record

---

### View Layer

Contains JavaFX user interfaces.

Responsibilities:

* Display information
* Capture user input
* Handle UI events

Files:

```
.fxml
.css
```

---

### Controller Layer

Acts as the communication bridge between UI and business logic.

Responsibilities:

* Handle button actions
* Validate user input
* Update UI components
* Communicate with services

Example:

```
StaffController.java
```

---

### Service Layer

Contains application logic and data processing.

Responsibilities:

* Searching
* Adding records
* Removing records
* Updating information

Example:

```
StaffService.java
```

---

# Project Structure

Recommended project structure:

```
src
│
├── application
│   ├── Main.java
│   └── HospitalManagement.java
│
├── controller
│   ├── MainPageController.java
│   ├── SidebarController.java
│   └── StaffController.java
│
├── model
│   ├── Staff.java
│   ├── Patient.java
│   └── Appointment.java
│
├── service
│   ├── StaffService.java
│   └── PatientService.java
│
├── util
│   ├── AppContext.java
│   └── Validator.java
│
└── resources
    │
    ├── fxml
    │   ├── MainPage.fxml
    │   └── Staff.fxml
    │
    └── css
        └── style.css
```

---

# Development Setup

## Requirements

Install the following:

* JDK 21
* JavaFX SDK 17
* Eclipse IDE
* Scene Builder

---

## Configure JavaFX

Ensure JavaFX libraries are added to the project build path.

VM Arguments example:

```
--module-path "PATH_TO_JAVAFX/lib"
--add-modules javafx.controls,javafx.fxml
```

Replace:

```
PATH_TO_JAVAFX
```

with your JavaFX SDK location.

---

# Running the Application

## Method 1: Eclipse

1. Clone the repository

```bash
git clone <repository-url>
```

2. Import the project into Eclipse.

3. Configure:

```
Project → Properties → Java Build Path
```

4. Add JavaFX libraries.

5. Run:

```
Main.java
```

---

## Method 2: Command Line

Compile:

```bash
javac --module-path PATH_TO_JAVAFX/lib \
--add-modules javafx.controls,javafx.fxml \
*.java
```

Run:

```bash
java --module-path PATH_TO_JAVAFX/lib \
--add-modules javafx.controls,javafx.fxml \
application.Main
```

---

# Module Explanation

## Staff Management

Handles hospital employee records.

Features:

* Add staff
* Remove staff
* Search staff
* Display staff information

Main files:

```
Staff.java
StaffController.java
StaffService.java
```

---

## Patient Management

Handles patient information.

Possible features:

* Register patients
* Update patient details
* Search patient records

---

## Appointment Management

Responsible for scheduling and tracking appointments.

Possible features:

* Create appointments
* Assign doctors
* View schedules

---

# Application Context

The project uses a shared application context to maintain access to shared services and data.

Example:

```
AppContext.getInstance()
```

Purpose:

* Avoid duplicated service objects
* Maintain consistent application state
* Improve communication between controllers

---

# Coding Guidelines

## Naming Convention

### Classes

Use PascalCase:

```
StaffController
PatientService
```

### Methods

Use camelCase:

```
searchStaff()
addPatient()
```

### Variables

Use meaningful names:

Good:

```java
private String staffName;
```

Avoid:

```java
private String x;
```

---

# Controller Guidelines

Controllers should:

* Handle UI interaction
* Avoid complex business logic
* Delegate processing to services

Example:

Bad:

```java
button.setOnAction(e -> {
    // 100 lines of logic
});
```

Better:

```java
button.setOnAction(e -> staffService.addStaff());
```

---

# Service Guidelines

Services should:

* Handle application logic
* Validate data
* Process collections
* Communicate with databases in future versions

Controllers should not directly modify data.

---

# Git Workflow

## Creating a Feature Branch

```bash
git checkout -b feature-name
```

Example:

```bash
git checkout -b staff-search-function
```

---

## Commit Messages

Use descriptive commits:

Good:

```
Add staff search validation
Fix FXML loading issue
Refactor StaffService filtering logic
```

Avoid:

```
update
fix
changes
```

---

## Before Pushing

Always:

```bash
git pull origin main
```

Then:

```bash
git add .
git commit -m "Your message"
git push
```

---

# Future Improvements

Potential improvements:

* Database integration (MySQL/PostgreSQL)
* User authentication and role management
* Encryption of sensitive patient information
* REST API backend
* Automated testing
* Improved UI responsiveness
* Report generation
* Cloud deployment

---

# Contributors

Developed by:

* [Developer Name]

---

# License

This project is developed for academic purposes.
