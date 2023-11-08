import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, RouterProvider, createBrowserRouter } from 'react-router-dom';
import './index.css';
import Auth from './auth/page';
import Home from './home/page';
import reportWebVitals from './reportWebVitals';
import AuthPages from './auth/authpages';
import { Routes, Route } from 'react-router-dom';

/**
const router = createBrowserRouter([
  {
    path: "/",
    element: <Auth page={AuthPages.LOGIN}/>,
  },
  {
  path: "/login",
  element: <Auth page={AuthPages.LOGIN}/>,
  },
  {
    path: "/signin",
    element: <Auth page={AuthPages.SIGNIN}/>,
  },
  {
    path: "/home",
    element: <Home/>
  }
])
**/

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    {/* <RouterProvider router={router} */}
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Auth page={AuthPages.LOGIN}/>} />
        <Route path='/login' element={<Auth page={AuthPages.LOGIN}/>} />
        <Route path='/signin' element={<Auth page={AuthPages.SIGNIN}/>} />
        <Route path='/home' element={<Home/>} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals(console.log);
