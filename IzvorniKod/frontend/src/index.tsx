import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Login from './auth/page';
import reportWebVitals from './reportWebVitals';
import AuthPages from './auth/authpages';
import { Route, RouterProvider, createBrowserRouter } from 'react-router-dom';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login page={AuthPages.LOGIN}/>,
  },
  {
  path: "/login",
  element: <Login page={AuthPages.LOGIN}/>,
  },
  {
    path: "/signin",
    element: <Login page={AuthPages.SIGNIN}/>,
  }
])

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals(console.log);
