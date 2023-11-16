import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom/client";
import {
  BrowserRouter,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import "./index.css";
import Auth from "./auth/page";
import Home from "./home/page";
import User from "./user/page";
import reportWebVitals from "./reportWebVitals";
import AuthPages from "./auth/authpages";
import { Routes, Route } from "react-router-dom";
import { RoleContextProvider, useRoleContext, Role } from "./roleContext";
import ChangePass from "./changepass/page";

const App = function () {
  const { role, updateRole } = useRoleContext();

  useEffect(() => {
    fetch("/api/v1/auth/refresh").then((response) => {
      if (response.ok) {
        updateRole(Role.USER);
      }
    });
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Auth page={AuthPages.LOGIN} />} />
        <Route path="/login" element={<Auth page={AuthPages.LOGIN} />} />
        <Route path="/signin" element={<Auth page={AuthPages.SIGNIN} />} />
        <Route path="/changepass" element={<ChangePass />} />
        {role != Role.NONE && (
          <>
            <Route path="/home" element={<Home />} />
            <Route path="/user" element={<User />} />
          </>
        )}
      </Routes>
    </BrowserRouter>
  );
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <RoleContextProvider>
      <App />
    </RoleContextProvider>
  </React.StrictMode>
);

reportWebVitals(console.log);
