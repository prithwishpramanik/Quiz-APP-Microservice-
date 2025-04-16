Here’s a polished, professional **README.md** for your Quiz App Microservice project. You can copy this directly into your GitHub repository:

---

# Quiz App Microservice 🧠📚

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue.svg)](https://microservices.io)

A scalable quiz application built with Spring Boot microservices, featuring user authentication, quiz management, and real-time scoring. Designed for high availability and loose coupling between services.

---

## 📌 Features

- **User Service**:
  - ✅ JWT-based authentication/registration
  - 📊 User profile management
  - 🏆 Track quiz scores and progress

- **Quiz Service**:
  - 🎯 Create and manage quizzes/questions
  - 📝 Validate answers in real-time
  - 📈 Calculate scores dynamically

- **Infrastructure**:
  - 🔍 Service discovery with **Eureka Server**
  - 🚦 API Gateway for routing and load balancing
  - 🛡️ Database isolation (one per microservice)

---

## 🛠️ Technologies Used

- **Backend**: Spring Boot 3, Spring Cloud (Eureka, API Gateway)
- **Security**: JWT, Spring Security
- **Communication**: REST APIs, Feign Client
- **Databases**: MySQL/PostgreSQL (configured in `application.properties`)
- **Tools**: Maven, Lombok, ModelMapper

---

## 🏗️ Architecture

### Microservice Diagram
```mermaid
%% Quiz Creation Flow with API Gateway and Microservices
sequenceDiagram
    participant User
    participant APIGateway
    participant QuizController
    participant QuizService
    participant QuizServiceInterface as QuizServiceInterface (Feign Client)
    participant QuestionController
    participant QuestionService
    participant QuestionDao
    participant QuizRepository
    participant QuestionDB
    participant QuizDB

    User->>APIGateway: POST /quiz/create (QuizDTO)
    activate APIGateway
    APIGateway->>QuizController: Route to quiz/create
    deactivate APIGateway

    activate QuizController
    QuizController->>QuizService: createQuiz(quizDTO)
    deactivate QuizController

    activate QuizService
    Note right of QuizService: quizDTO contains:<br/>- category<br/>- numQ<br/>- title
    QuizService->>QuizServiceInterface: generateQuiz(category, numQ)
    deactivate QuizService

    activate QuizServiceInterface
    Note right of QuizServiceInterface: @FeignClient("QUESTION-SERVICE")<br/>GET /api/generateQuiz
    QuizServiceInterface->>QuestionController: GET /api/generateQuiz?category={category}&numQ={numQ}
    deactivate QuizServiceInterface

    activate QuestionController
    QuestionController->>QuestionService: generateQuiz(category, numQ)
    deactivate QuestionController

    activate QuestionService
    QuestionService->>QuestionDao: findQuestionsByCategory(category, numQ)
    deactivate QuestionService

    activate QuestionDao
    QuestionDao->>QuestionDB: Query questions
    QuestionDB-->>QuestionDao: Return questions
    deactivate QuestionDao

    QuestionDao-->>QuestionService: List<Question>
    activate QuestionService
    QuestionService-->>QuestionController: Quiz questions
    deactivate QuestionService

    activate QuestionController
    QuestionController-->>QuizServiceInterface: Return generated questions
    deactivate QuestionController

    activate QuizServiceInterface
    QuizServiceInterface-->>QuizService: Return questions
    deactivate QuizServiceInterface

    activate QuizService
    Note right of QuizService: Assemble Quiz with:<br/>- Title<br/>- Category<br/>- Questions
    QuizService->>QuizRepository: save(quiz)
    deactivate QuizService

    activate QuizRepository
    QuizRepository->>QuizDB: Store Quiz entity
    QuizDB-->>QuizRepository: Stored confirmation
    deactivate QuizRepository

    QuizRepository-->>QuizService: Return persisted Quiz
    activate QuizService
    QuizService-->>QuizController: Return created Quiz
    deactivate QuizService

    activate QuizController
    QuizController-->>APIGateway: Return response
    deactivate QuizController

    activate APIGateway
    APIGateway-->>User: Return created Quiz
    deactivate APIGateway

    box lightblue "Quiz Database"
        participant QuizEntity
    end
    
    box lightgreen "Question Database"
        participant QuestionEntity
    end

    Note right of QuizEntity: Quiz Entity Fields:<br/>- ID<br/>- Title<br/>- Category<br/>- Questions[]
    Note right of QuestionEntity: Question Entity Fields:<br/>- ID<br/>- Category<br/>- QuestionText<br/>- Options<br/>- CorrectAnswer
```
```mermaid
flowchart TD
    A[User] -->|"POST /quiz/create<br/>(QuizDTO)"| B[API Gateway]
    B -->|"Route Request"| C[Quiz Service]
    C -->|"Parse QuizDTO"| D{"Validate Input?"}
    D -->|"Valid"| E["Call Question Service<br/>via Feign Client"]
    D -->|"Invalid"| Z["Return Error 400"]
    
    E -->|"GET /api/generateQuiz<br/>(category, numQ)"| F[Question Service]
    F -->|"Query Database"| G[(Question DB)]
    G -->|"Return Questions"| F
    F -->|"List<Question>"| E
    
    E -->|"Received Questions"| H{"Questions<br/>Available?"}
    H -->|"Yes"| I["Assemble Quiz Entity"]
    H -->|"No"| Y["Return Error 404"]
    
    I -->|"Set Title/Category"| J["Save Quiz"]
    J -->|"Persist Data"| K[(Quiz DB)]
    K -->|"Save Result"| L{"Save<br/>Success?"}
    L -->|"Yes"| M["Return 201 Created"]
    L -->|"No"| N["Return Error 500"]
    
    M -->|"Quiz JSON"| A
    N -->|"Error Message"| A
    Y -->|"Error Message"| A
    Z -->|"Error Details"| A

    style A fill:#f9f,stroke:#333
    style B fill:#cff,stroke:#333
    style C fill:#9f9,stroke:#333
    style F fill:#9f9,stroke:#333
    style G fill:#f96,stroke:#333
    style K fill:#f96,stroke:#333
    style D,H,L fill:#ff9,stroke:#333

    classDef microservice fill:#9f9,stroke:#333;
    classDef database fill:#f96,stroke:#333;
    classDef gateway fill:#cff,stroke:#333;
    classDef decision fill:#ff9,stroke:#333;
    classDef user fill:#f9f,stroke:#333;
    
    class A user
    class B gateway
    class C,F microservice
    class G,K database
    class D,H,L decision

```

### Data Flow
1. **User Authentication**:  
   Client → API Gateway → User Service → Issue JWT

2. **Quiz Interaction**:  
   Client → API Gateway → Quiz Service → Calculate Score → Update User Service via Feign

3. **Service Discovery**:  
   All services register with Eureka for dynamic routing.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- MySQL/PostgreSQL
- IDE (IntelliJ/Eclipse)

### Setup Steps

1. **Clone the repo**:
   ```bash
   git clone https://github.com/prithwishpramanik/Quiz-APP-Microservice-.git
   ```

2. **Configure Databases**:
   - Create two databases: `quiz_db` and `user_db`
   - Update credentials in `application.properties` for both services.

3. **Build and Run**:
   ```bash
   # Start Eureka Server first
   mvn spring-boot:run -pl eureka-server

   # Start API Gateway
   mvn spring-boot:run -pl api-gateway

   # Start User Service
   mvn spring-boot:run -pl user-service

   # Start Quiz Service
   mvn spring-boot:run -pl quiz-service
   ```

4. **Access Endpoints**:
   - Eureka Dashboard: `http://localhost:8761`
   - API Gateway: `http://localhost:8080`

---

## 📡 API Endpoints

### User Service
| Endpoint                | Method | Description                     |
|-------------------------|--------|---------------------------------|
| `/user/register`        | POST   | Register new user               |
| `/user/login`           | POST   | Authenticate and get JWT        |
| `/user/profile/{email}` | GET    | Fetch user profile              |

### Quiz Service
| Endpoint                | Method | Description                     |
|-------------------------|--------|---------------------------------|
| `/quiz/create`          | POST   | Create a new quiz               |
| `/quiz/{quizId}`        | GET    | Get quiz details                |
| `/quiz/submit/{quizId}` | POST   | Submit answers and get score    |

---

## 🤝 Contributing

Contributions are welcome! Follow these steps:
1. Fork the repository
2. Create a branch: `git checkout -b feature/your-idea`
3. Commit changes: `git commit -m 'Add awesome feature'`
4. Push: `git push origin feature/your-idea`
5. Open a pull request.

---

## 📜 License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.

---

## 🙌 Acknowledgments

- Spring Boot Team
- Microservices.io for architectural guidance
- Open-source community tools

---

**Happy Quizzing!** 🚀  
Let me know if you need further refinements!
