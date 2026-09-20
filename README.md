# Real Estate Jordan UI automation

Selenium WebDriver + Java 21 + TestNG, organized as Maven page objects and end-to-end tests.
The application under test is [realestate-jo](https://github.com/mohammad2001ah/realestate-jo).

## Layout

- `src/main/java/pages`: locators and page actions.
- `src/test/java/base/BaseTest.java`: Chrome setup, teardown, login and registration helpers.
- `src/test/java/tests`: independent scenarios and generated upload images.

## Prerequisites

Install JDK 21, Maven, and Chrome. Selenium Manager supplies a compatible driver.
Start MongoDB and the app's backend at `http://localhost:5000` and frontend at
`http://localhost:3000`. Ensure the backend has a writable
`server/uploads/properties` directory. Run against a disposable test database:
registration creates accounts, and property tests create and delete records.
At least one approved property is needed for browsing, details, filters and favorites.

## Commands

```bash
mvn test
mvn -Dtest=PropertiesTest test
mvn -Dtest=AgentFlowTest test
mvn -Dheadless=true test
mvn -DadminEmail=admin@example.com -DadminPassword=YOUR_LOCAL_PASSWORD test
```

Use `-DbaseUrl=http://localhost:3000` to override the frontend URL. Admin tests
skip when credentials are absent. They only edit/delete the fresh user made by
their own test. No credentials belong in source control.

The full suite needs the locally running app, Chrome, a healthy test database,
image upload, and admin credentials for admin scenarios. Without these, build
compilation alone does not establish that the UI scenarios pass.

## Known application gaps

- The Edit buttons target `/edit-property/:id`, but `App.js` has no such route.
- `/admin` is protected only by authentication in the frontend; a normal user
  can reach its shell, although admin API endpoints reject the requests.
- The property list has location, price and bedrooms filters; it has no free
  text title search. A bedroom option labeled 5+ currently sends exactly 5.
- Property creation requires five images in the UI. The backend creates
  pending listings, so they will not appear publicly until an admin approves.
