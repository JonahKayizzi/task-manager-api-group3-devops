<a name="readme-top"></a>


<!-- <div align="center">
  <img src="" alt="logo" width="140"  height="auto" />
  <br/>

  <h1><b>Task Management API</b></h1>

</div> -->

<!-- TABLE OF CONTENTS -->

# 📗 Table of Contents

- [📗 Table of Contents](#-table-of-contents)
- [📖 TASK MANAGEMENT API ](#-task-management-api-)
  - [🛠 Built With ](#-built-with-)
    - [Key Features ](#key-features-)
  - [💻 Getting Started ](#-getting-started-)
    - [Prerequisites](#prerequisites)
    - [Setup](#setup)
    - [Building](#building)
    - [Usage](#usage)
    - [Run tests](#run-tests)
  - [👥 Authors ](#-authors-)
  - [🤝 Contributing ](#-contributing-)
  - [⭐️ Show your support ](#️-show-your-support-)
  - [🙏 Acknowledgments ](#-acknowledgments-)
  - [📝 License ](#-license-)

<!-- PROJECT DESCRIPTION -->

# 📖 TASK MANAGEMENT API <a name="about-project"></a>


**Task Management API using Spring Boot**
- Focusing on CI/CD, Dockerization, and Kubernetes deployment. 
- Implementing a working DevOps pipeline 
- Monitoring and logging the backend. 

## 🛠 Built With <a name="built-with"></a>
- Java Spring Framework
- JUnit for testing
- Docker for containerization
- PostgreSQL for database
- Maven as a build automation tool 
- Mockito for writing unit tests
- RESTful APIs for endpoints
- Swagger for API documentation

<!-- Features -->

### Key Features <a name="key-features"></a>

- Creating Tasks
- Updating Tasks
- Retrieving Tasks
- Deleting Tasks

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->

## 💻 Getting Started <a name="getting-started"></a>


To get a local copy up and running, follow these steps.

### Prerequisites

In order to run this project you need:

- Install Java 17 or above
- Install Maven
- PostgreSQL
- IntelliJ IDEA
- Docker (optional, for running PostgreSQL in a container)

### Setup

Clone this repository to your desired folder:

```sh
  cd my-folder
  git clone https://github.com/JonahKayizzi/task-manager-api-group3-devops
```

### Building

Checkout to the main branch, build the program and package to create a JAR file

```sh
cd task-manager-api-group3-devops
git checkout develop
docker-compose up-build
```

### Usage

Use Java to run the JAR file

```sh
 java -jar .\target\taskmanager-0.0.1-SNAPSHOT.jar 
```

### Run tests

To run tests, run the following command:

JUnit and Mockito library are to be used for running tests

```sh
  mvn test
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 👥 Authors <a name="authors"></a>

👤 **Sheila Wafula**

- GitHub: [@shishi5089](https://github.com/shishi5089)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/sheila-wafula-6188a419b/)

👤 **Oseno Ewaose-Joseph**

- GitHub: [@oseno-cmu](https://github.com/oseno-cmu)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/oseno-ewaose-joseph-3010741b3/)

👤 **Joseph Fadiji**

- GitHub: [@justWeird](https://github.com/justWeird)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/joseph-fadiji-ba1766173/)

👤 **Jonathan Kayizzi**

- GitHub: [@JonahKayizzi](https://github.com/JonahKayizzi)
- Twitter: [@JonahKayizzi](https://twitter.com/JonahKayizzi)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/jonathan-kayizzi/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->

## 🤝 Contributing <a name="contributing"></a>

Contributions, issues, and feature requests are welcome!

Feel free to check the [issues page](https://github.com/JonahKayizzi/task-manager-api-group3-devops/issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- SUPPORT -->

## ⭐️ Show your support <a name="support"></a>


If you like this project you can give me a ⭐️

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGEMENTS -->

## 🙏 Acknowledgments <a name="acknowledgements"></a>


I would like to thank [Carnegie Mellon University](https://www.linkedin.com/school/carnegie-mellon-university-africa/?originalSubdomain=rw) for arranging this challenge

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->

## 📝 License <a name="license"></a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>
