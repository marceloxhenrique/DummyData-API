[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[SWAGGER_BADGE]: https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white&textColor=white
[JUNIT_BADGE]: https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white
[THYMELEAF_BADGE]: https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white

<h1 style="font-weight: bold;">DummyData API</h1>

![java][JAVA_BADGE]
![spring][SPRING_BADGE]
![thymeleaf][THYMELEAF_BADGE]
![junit][JUNIT_BADGE]
![swagger][SWAGGER_BADGE]

<p>
 <a href="#started">Getting Started</a> -
 <a href="#ci-cd">CI/CD Pipeline</a> -
 <a href="#routes">API Endpoints</a> -
 <a href="#contribute">Contribute</a>
</p>

DummyData API is a flexible and easy-to-use API designed to provide developers with realistic mock data for testing and prototyping applications. With support for multiple languages (English, French and Portuguese) and customizable data sets, DummyData API helps developers simulate real-world data environments.

<h2 id="started">🚀 Getting started</h2>

<h3>Prerequisites</h3>

- Java 17.0.10
- Maven
- Git

<h3>Cloning</h3>

First, clone the repository:

```shell
git clone git@github.com:marceloxhenrique/DummyData-API.git
```

<h3>Navigate to the project directory</h3>

```shell
cd <your-project-name>
```

<h3>Install the project</h3>

```shell
mvn install
```

<h3>Setup application.properties</h3>

```shell
spring.application.name=dummydata
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.messages.basename=messages
spring.messages.encoding=UTF-8
spring.data.web.pageable.max-page-size=999999999
```

<h3>Running the application</h3>
<p>The app run on port 8080 by default.</p>

```shell
mvn spring-boot:run
```

<h2 id="ci-cd">📦 CI/CD Pipeline</h2>

<p>CI / CD pipeline using GitHub Actions.</p>

<h3>Workflows</h3>

<ul>
  <li>
    <strong>Run Tests on push to Dev branch:</strong> 
    <p>This workflow is triggered on every push to the <code>dev</code> branch. It runs the tests to ensure that the code is working as expected. The tests are run using JDK 17 and Maven.</p>
  </li>
  <li>
    <strong>Deploy App on pull request to Main branch:</strong> 
      <p>This workflow is triggered when a pull request is made to the <code>main</code> branch. It builds the project using Maven, creates a Docker image, and pushes the image to Docker Hub. Additionally, it triggers the deployment on Render, which deploys the application using the newly built Docker image version.</p>
  </li>
</ul>

<h2 id="routes">📍 API Endpoints</h2>

List of endpoints availables
​
| route | description  
|----------------------|-----------------------------------------------------
| GET /api/users | retrieve 100 users [user](#get-users-detail)
| GET /api/users/1 | retrieve a specific user by id [user](#get-users-detail)
| GET /api/comments | retrieve 100 comments [comment](#get-comments-detail)
| GET /api/comment/1 | retrieve a specific comment by id [comment](#get-comments-detail)
| GET /api/images | retrieve 100 images [image](#get-images-detail)
| GET /api/images/1 | retrieve a specific image by id [image](#get-images-detail)

<h3 id="get-users-detail">User</h3>

```json
{
  "id": 1,
  "username": "markbradley",
  "firstName": "Mark",
  "lastName": "Bradley",
  "birthDate": "1995-05-15",
  "phone": "+1 202 555 1234",
  "email": "mark.bradley@example.com",
  "address": {
    "country": "United States of America",
    "state": "California",
    "city": "Los Angeles",
    "street": "123 Elm Street",
    "postalCode": "90001"
  },
  "role": "User"
} ... more 99 users
```

<h3 id="get-comments-detail">Comment</h3>

```json
{
  "id": 1,
  "userId": 1,
  "title": "Great Team Meeting!",
  "body": "I really enjoyed our team meeting today. The new project ideas were fantastic, and I appreciate everyone's input.",
  "createdAt": "2024-09-01T10:45:23"
} ... more 99 comments
```

<h3 id="get-images-detail">Image</h3>

```json
{
  "id": 1,
  "userId": 1,
  "url": "https://picsum.photos/900",
  "width": 900,
  "height": 900,
  "title": "Captured Moment"
} ... more 99 images
```

<h3>You can customize your requests using the following parameters:</h3>
<p><strong>Size: </strong>Use the size parameter to specify the number of records you want to retrieve, ranging from 1 to 100.</p>
<p><strong>Lang: </strong>Use the lang parameter to choose the language of the data:</p>
<ul>
  <li>en - for English</li>
  <li>fr - for French</li>
  <li>pt - for Portuguese (Brazilian)</li>
</ul>
<p>Example:</p>

| route                         | description                                               |
| ----------------------------- | --------------------------------------------------------- |
| GET /api/users?size=2         | retrieve 2 users [user](#get-2-users)                     |
| GET /api/users?lang=fr        | retrieve 100 users in French [user](#get-users-french)    |
| GET /api/users?lang=fr&size=2 | retrieve 2 users in French [comment](#get-2-users-french) |

<h3 id="get-2-users">2 Users</h3>

```json
[
  {
    "id": 1,
    "username": "markbradley",
    "firstName": "Mark",
    "lastName": "Bradley",
    "birthDate": "1995-05-15",
    "phone": "+1 202 555 1234",
    "email": "mark.bradley@example.com",
    "address": {
      "country": "United States of America",
      "state": "California",
      "city": "Los Angeles",
      "street": "123 Elm Street",
      "postalCode": "90001"
    },
    "role": "User"
  },
  {
    "id": 2,
    "username": "janefoster",
    "firstName": "Jane",
    "lastName": "Foster",
    "birthDate": "1991-08-22",
    "phone": "+1 305 555 5678",
    "email": "jane.foster@example.com",
    "address": {
      "country": "United States of America",
      "state": "Florida",
      "city": "Miami",
      "street": "456 Maple Avenue",
      "postalCode": "33101"
    },
    "role": "Admin"
  }
]
```

<h3 id="get-users-french">100 Users in French</h3>

```json
{
    "id": 1,
    "username": "jeandupont",
    "firstName": "Jean",
    "lastName": "Dupont",
    "birthDate": "1995-05-15",
    "phone": "+33 1 23 45 67 89",
    "email": "jean.dupont@example.fr",
    "address": {
      "country": "France",
      "state": "Île-de-France",
      "city": "Paris",
      "street": "123 Rue de la Paix",
      "postalCode": "75001"
    },
    "role": "Utilisateur"
  } ... more 99 users in French
```

<h3 id="get-2-users-french">2 Users in French</h3>

```json
[
  {
    "id": 1,
    "username": "jeandupont",
    "firstName": "Jean",
    "lastName": "Dupont",
    "birthDate": "1995-05-15",
    "phone": "+33 1 23 45 67 89",
    "email": "jean.dupont@example.fr",
    "address": {
      "country": "France",
      "state": "Île-de-France",
      "city": "Paris",
      "street": "123 Rue de la Paix",
      "postalCode": "75001"
    },
    "role": "Utilisateur"
  },
  {
    "id": 2,
    "username": "mariedurand",
    "firstName": "Marie",
    "lastName": "Durand",
    "birthDate": "1991-08-22",
    "phone": "+33 4 56 78 90 12",
    "email": "marie.durand@example.fr",
    "address": {
      "country": "France",
      "state": "Provence-Alpes-Côte d'Azur",
      "city": "Marseille",
      "street": "456 Avenue du Prado",
      "postalCode": "13008"
    },
    "role": "Administrateur"
  }
]
```

<h2 id="contribute">📫 Contribute</h2>

If you want to contribute, clone this repo, create your work branch and get your hands dirty!

1. ```shell
   git clone git@github.com:marceloxhenrique/DummyData-API.git
   ```

2. ```shell
   git checkout -b feature/NAME
   ```

3. Follow commit patterns
4. Open a Pull Request explaining the problem solved or feature made, if exists, append screenshot of visual modifications and wait for the review!

<h2>📄 API documentation </h2>

```shell
http://localhost:8080/swagger-ui/index.html
```

<img width="500" alt="DummyData_API_Documentation_Image" src="https://github.com/user-attachments/assets/f713951c-3fe0-42e9-8299-29007a355bea">
