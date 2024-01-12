import React, { useEffect } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, redirect } from "react-router-dom";
import "./index.css";
import Auth from "./auth/page";
import Home, { HomePage } from "./home/page";
import User from "./user/page";
import reportWebVitals from "./reportWebVitals";
import AuthPages from "./auth/authpages";
import { Routes, Route } from "react-router-dom";
import { UserContextProvider, useUserContext, Role } from "./userContext";
import ChangePass from "./changepass/page";

const App = function () {
  const { userInfo, updateUserInfo } = useUserContext();

  useEffect(() => {
    fetch("/api/v1/auth/refresh").then((response) => {
      if (response.ok) {
        return response.json()
      }
      throw Error('Unauthenticated.');
    }).then(userInfo => {
      console.log(userInfo);
      updateUserInfo(userInfo)
    })
    .catch(err => {
      console.log("Handled error: " + err);
      redirect('/login');
    });
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Auth page={AuthPages.LOGIN} />} />

        {/* TODO: move this to be available only when logged in */}
        <Route path="/home" element={<Home page={HomePage.LANGUAGES} />} />
        <Route path="/home/:lang" element={<Home page={HomePage.DICTIONARIES} />} />
        <Route path="/changepass" element={<ChangePass />} />

        <Route path="/signin" element={<Auth page={AuthPages.SIGNIN} />} />
        {
          userInfo !== null && Role[userInfo.role] === Role.UNVERIFIED_USER && (
            <Route path="/changepass" element={<ChangePass />} />
          )
        }
        {userInfo !== null && Role[userInfo.role] !== Role.UNVERIFIED_USER  && (
          <>
            <Route path="/" element={<Home page={HomePage.LANGUAGES} />} />
            <Route path="/user" element={<User />} />
          </>
        ) || (
          <Route path="/" element={<Auth page={AuthPages.LOGIN} />} />
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
    <UserContextProvider>
      <App />
    </UserContextProvider>
  </React.StrictMode>
);

reportWebVitals(console.log);
