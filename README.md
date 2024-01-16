# Assignment 1

## Getting Started


## Prerequisites
###  Java 17 as main development program
- This project utilizes an Agile toolset including GitHub, Gradle, JUnit, and Jenkins as required by the Assessment.
- Our GitHub organization contains a shared GitHub repository where each member has contribution rights.
- Gradle is used for build automation and JUnit is used for automated testing, which according to Assessment must have at least 75% code coverage.
- Use Jenkins to integrate with GitHub and run tests automatically.
- Jenkins displays test reports for the application.
- Our group chose to jointly use the IntelliJ IDE to assist with development.

### Building
- Cloning a GitHub repository to your local computer
```bash
git clone <repository_url>
```
<br>

- Build the project using Gradle:
```bash
./gradlew build
```

-  Usage

```bash
./gradlew bootRun --args="<your args here>"
```
<br>

- Alternatively, run the shell

```bash
./shell.sh
```

<br>

#### Admin Dashboard

```bash
./gradlew bootRun --args="admin -p admin -u admin" # default admin credentials
```


### Program Test
- Run JUnit tests using Gradle:
```bash
./gradlew test
```

<br>

- View Test Coverage Report
```bash
./gradlew jacocoTestReport
```


## Program Expectations
- When running the application, any user will be able to browse the menu items and choose to add to and edit the shopping cart.
- Running the program is possible for the contents of the shopping cart.
- The order history will show you details of your previous orders.
- New users can be registered when running the program
- Administrators can use the Admin Dashboard to perform special operations such as adding, updating and deleting menu items, as well as managing the permissions of users that appear.


## How Contributing
- Clone the GitHub repository to each member's local computer.
- Create a new branch based on assign's issus and develop it individually.
```bash
git checkout -b feature-1
```

- Submit Pull Requests and describe changes for each branch task.
- Wait for review and testing
- Merge branches
```bash
git checkout main
git merge feature-1
```

## Contributing

All contributions must be made in a separate branch and submitted as a pull request.
All tests must pass with 75% code coverage before a pull request can be merged.