# 📖 Full Stack Engineering with Spring Boot & React

Welcome to the companion repository for the book **“Full Stack Engineering with Spring Boot & React”**.  
This repo mirrors the journey of the book: from building a clean backend API, to connecting a frontend, to securing, scaling, and deploying the application.

Each **Part** of the book has its own folder, and each **Chapter** is represented as a Git branch so you can follow along step by step.

---

## 🎯 Who This Book Is For
- Students and self-learners who want to understand **end-to-end web development**.  
- Backend developers curious about frontend, or frontend devs who want to learn backend skills.  
- Engineers who want to go beyond “just making it work” and understand **why** certain design choices are made.  

We use **Spring Boot 3.x (Java)** for the backend and **React (JavaScript)** for the frontend, while also comparing these choices with alternatives in other stacks (Node.js, .NET, Django, Vue, etc.).

---

## 📂 Repository Structure

```text
.
├── part1-RESTful_API-SpringBoot        # Part I: Building a RESTful Backend
├── part2-Responsive_Frontend-React       # Part II: Building a Responsive Frontend
├── part3-Advanced-backend-features
├── part4-Advanced-frontend-features
├── part5-Devops-infra-deployment
```

- **Parts** = Folders (major phases of the project)  
- **Chapters** = Branches (each builds on the previous one)  

Example:  
```bash
git checkout part1-RESTful_API-SpringBoot
```
to see the backend project at the beginning of Part I.

---

## 📚 Book Outline

- **Part I - Building a RESTful Backend with Spring Boot**  
  1. Introduction to backend architecture  
  2. Defining the Domain Model with JPA  
  3. Creating API Models (DTOs)
  4. Mapping Between Layers with MapStruct
  5. Implementing the Repository Layer
  6. Building the Service Layer for Business Logic
  7. Exposing Endpoints in the Controller Layer 
  8. Handling Requests and Responses  
  9. Global Exception Handling
  10. Configuring CORS for Cross-Origin Requests
  11. Persistence and Database Migrations
  12. Testing Strategies
  13. Common Pitfalls and How to Avoid Them

- **Part II - Building a Responsive Frontend with React + Vite**  
  1. Introduction to frontend architecture  
  2. Project setup with Vite  
  3. API Integration Layer
  4. Custom Hooks
  5. Notification System
  6. Header Component
  7. Toolbar for Controls
  8. Data Display Component
  9. Modal Forms for Data Entry
  10. Orchestrating the App with Context API
  11. Styling with CSS Modules
  12. Testing and Debugging Strategies
  13. Common Pitfalls and How to Avoid Them

- **Part III - Advanced Backend Features (Spring Boot)**  
  1. Authentication & Authorization with JWT  
  2. Robust Validation & Global Error Handling  
  3. Concurrency & Optimistic Locking  
  4. Advanced Searching with Specifications  
  5. Partial Updates (PATCH with JSON Merge Patch)  
  6. Caching with Redis  
  7. Auditing & Soft Deletes  
  8. File Uploads (Images)  
  9. Observability with Actuator, Metrics, and Tracing  
  10. Rate Limiting & Throttling  
  11. API Versioning & OpenAPI Documentation  
  12. Database Migrations with Flyway & Testing with Testcontainers  
  13. Asynchronous Work & Domain Events  
  14. Hardening & Best Practices Checklist  

- **Part IV - Advanced Frontend Features (React)**  
  1. Introduction to Advanced Frontend with React  
  2. Redux Toolkit & Axios Integration  
  3. Navigation with React Router  
  4. Enhancing Navigation with UI & UX Features  
  5. Forms & Validation  
  6. Search, Filtering & Sorting  
  7. Pagination in React with Redux & Backend Integration  
  8. File Uploads & Image Thumbnails  
  9. Robust Error Handling & Conflict Resolution  
  10. Role-Based UI & Protected Routes  
  11. Auth QA Checklist & Hardening Pass  

- **Part V - DevOps, Infrastructure, and Deployment**  
  1. From Local Development to Production Mindset  
  2. Dockerizing the Full Stack App  
  3. Docker Compose for Full Stack  
  4. CI/CD Pipelines  
  5. Deployment Options  
  6. Logging & Monitoring  
  7. Security Hardening  
  8. Scaling & Future-Proofing  
  9. DevOps & Infrastructure Comparison Across Stacks  
  10. Production-Readiness Checklist  

- **Part VI - Appendices**  
  1. Maven → Gradle Migration  
  2. GitHub Workflow  
  3. IDE Setup & Productivity Shortcuts  
  4. Command-Line Quick Reference  
  5. Spring Boot Annotations Cheat Sheet  
  6. REST API Testing Reference  
  7. Error Troubleshooting Guide  
  8. Database Schema & Sample Data  
  9. Deployment Checklist  
  10. Security Hardening Tips  
  11. Performance Tuning Appendix  
  12. Where to Get Help  
  13. Glossary of Terms  
  14. Next Steps After the Book  

---

## 🚀 Getting Started

Clone this repository:

```bash
git clone https://github.com/brianronock/fullstackdev_spring-and-react.git
cd fullstackdev_spring-and-react.
```

Check out the branch you want to explore:

```bash
git checkout part1-chapter1-intro
```

Each part also has its own `README.md` with more detailed instructions.

---

## 🧑‍🏫 How to Use This Repo

- **As a learner**: Follow the book, switch branches as you progress.  
- **As a teacher**: Use the repo to demo each chapter live.  
- **As a reference**: Jump directly to the part or feature you’re interested in.  

---

## 📖 About the Content

The Docusaurus scaffold for this project is public, but the actual book manuscript—including raw chapters, tools, images, and other resources—is stored in a private repository.  
Learners who clone this repo will see the scaffold and structure but will not have access to the full book text. To access the complete content, please use the live GitHub Pages site:  
https://brianronock.github.io/fullstackdev_spring-and-react/

---

## 📜 License & Contributions

This repo is educational material accompanying the book.  
Feel free to fork and experiment. For contributions or errata, open an issue or PR.  

---

✨ By the end, you’ll not only have a working full stack application, but also a **deep understanding** of why it was built this way — knowledge that transfers to any modern tech stack.
