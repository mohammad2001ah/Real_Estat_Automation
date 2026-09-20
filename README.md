# Real Estate Jordan - UI Automation Testing

UI automation testing project for a MERN-based real estate application using Selenium WebDriver, Java, TestNG, Maven, and the Page Object Model (POM).

## Project Objective

The goal of this project is to practice and demonstrate UI automation testing on real user authentication flows, including positive and negative scenarios, validation checks, reusable page objects, and automated test execution.

## Technologies Used

- Java 21
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- Git & GitHub
- Eclipse IDE
- Google Chrome

## Automated Test Coverage

### Login
- Valid login
- Invalid password
- Invalid email
- Empty email
- Empty password
- Empty fields
- Invalid email format

### Registration
- Valid user registration
- Existing email
- Empty full name
- Empty email
- Invalid email format
- Empty password
- Empty confirm password
- Empty required fields
- Password mismatch
- Agent account registration

## Framework Structure

```text
src
├── base
│   └── BaseTest.java
├── pages
│   ├── LoginPage.java
│   └── RegisterPage.java
└── tests
    ├── LoginTest.java
    ├── RegisterTest.java
    └── SmokeTest.java