# Part II – Building the Frontend (React + Vite)

## Introduction

Having built a robust backend API in the previous part, this section focuses on creating a dynamic and responsive frontend using React and Vite. You'll learn how to structure your application, manage state, handle user interactions, and integrate with the backend API to build a seamless user experience.

## Chapters

- Introduction to the Frontend Architecture
- Setting Up the Project with Vite
- API Integration Layer (api.js)
- Custom Hooks (useDebounced.js)
- Notification System (Toasts.jsx)
- Header Component (Header.jsx)
- Toolbar for Controls (Toolbar.jsx)
- Data Display Component (DataTable.jsx)
- Modal for Forms (Modal.jsx)
- Orchestrating the App (App.jsx)
- Styling with CSS (styles.css)
- Testing and Debugging Strategies
- Common Pitfalls and How to Avoid Them
- Conclusion and Next Steps


## Tech Stack

- **React 18+** — for building UI components  
- **Vite** — fast frontend build tool with HMR  
- **Plain CSS / CSS Modules** — for styling  
- **React Testing Library & Jest** — for component and unit testing  

## Getting Started

1. Clone the repository:  
   ```bash
   git clone https://github.com/brianronock/fullstackdev_spring-and-react.git
   ```  
2. Navigate to the frontend folder:  
   ```bash
   cd Part2_Responsive-Frontend-React
   ```  
3. Install dependencies:  
   ```bash
   npm install
   ```  
4. Start the development server:  
   ```bash
   npm run dev
   ```  
5. Open your browser and visit:  
   ```
   http://localhost:5173
   ```  

## Testing

To run frontend tests using Jest and React Testing Library:

```bash
npm test
```

Tests cover components, hooks, and UI interactions to ensure your interface behaves as expected.

## Next Steps

- Once comfortable with the frontend basics, you can explore connecting your frontend to backend APIs in later parts of the book.  
- Explore Part III to learn about backend enhancements and security.  
- In Part IV, focus on deploying your fullstack application for production.

Happy coding!
