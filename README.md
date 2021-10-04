# Manage ToDo List Test Automation
Manage todo list test scenarios are automated using Cucumber and Selenium WebDriver and all the project dependencies are managed by maven.

# Prerequisites to execute the tests
1. JAVA
2. Maven

# Test Execution
- Clone this repository

- Run the below command from the project root folder using terminal:
    - To run the entire suite - mvn clean test 

    - To run the particular test scenario - mvn clean test -D cucumber.options="--tags @id_001"

# Test Report
Cucumber HTML Report will be generated in the following path - target/cucumber-html-reports/overview-features.html