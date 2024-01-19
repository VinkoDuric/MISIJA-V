import React, { useEffect } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, redirect } from "react-router-dom";
import "./index.css";
import Auth from "./auth/page";
import Home from "./home/page";
import User from "./user/page";
import reportWebVitals from "./reportWebVitals";
import AuthPages from "./auth/authpages";
import { Routes, Route } from "react-router-dom";
import { UserContextProvider, useUserContext, Role } from "./userContext";
import ChangePass from "./changepass/page";
import { Languages } from "./home/languages";
import { Dictionaries } from "./home/dictionaries";
import { Dictionary } from "./home/dictionary";
import { AddLanguage } from "./home/addLanguage";
import { Word } from "./home/word";
import AddAdmin from "./addAdmin/addAdmin";
import { StudyMode } from "./home/studyMode";
import { Quiz } from "./home/quiz";
import { Account } from "./home/account";

const App = function () {
  const { userInfo, updateUserInfo } = useUserContext();
  useEffect(() => {
    fetch("/api/v1/auth/refresh")
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw Error("Unauthenticated.");
      })
      .then((userInfo) => {
        console.log(userInfo);
        updateUserInfo(userInfo);
      })
      .catch((err) => {
        console.log("Handled error: " + err);
        redirect("/login");
      });

    {
      /* TODO: delete this part of code for setting the user manually */
    }
    let userInfo2 = userInfo;
    if (userInfo === null) {
      userInfo2 = {
        id: 0,
        firstName: "Ivan",
        lastName: "Cvrk",
        role: "ADMIN",
        email: "ivan.cvrk@gmail.com",
        tokenVersion: 0,
      };
    }
    updateUserInfo(userInfo2);
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Auth page={AuthPages.LOGIN} />} />

        {/* TODO: move this to be available only when logged in */}
        <Route
          path="/home"
          element={
            <Home>
              <Languages />
            </Home>
          }
        />
        <Route
          path="/home/account"
          element={
            <Home>
              <Account />
            </Home>
          }
        />
        <Route
          path="/home/:lang"
          element={
            <Home>
              <Dictionaries />
            </Home>
          }
        />
        <Route
          path="/home/studyMode"
          element={
            <Home>
              <StudyMode />
            </Home>
          }
        />
        <Route
          path="/home/Quiz/ABC"
          element={
            <Home>
              <Quiz studyMode={"ABC"}/>
            </Home>
          }
        />
        <Route
          path="/home/Quiz/text"
          element={
            <Home>
              <Quiz studyMode={"text"}/>
            </Home>
          }
        />
        <Route
          path="/home/Quiz/voice"
          element={
            <Home>
              <Quiz studyMode={"voice"}/>
            </Home>
          }
        />
        <Route path="/changepass" element={<ChangePass />} />
        <Route
          path="/dictionary/:dict?"
          element={
            <Home>
              <Dictionary />
            </Home>
          }
        />
        <Route
          path="/add/language"
          element={
            <Home>
              <AddLanguage />
            </Home>
          }
        />
        <Route
          path="/word/:wordId?"
          element={
            <Home>
              <Word />
            </Home>
          }
        />
        <Route
          path="/home/add-admin"
          element={
            <Home>
              <AddAdmin />
            </Home>
          }
        />

        <Route path="/signin" element={<Auth page={AuthPages.SIGNIN} />} />
        {userInfo !== null && Role[userInfo.role] === Role.UNVERIFIED_USER && (
          <Route path="/changepass" element={<ChangePass />} />
        )}
        {(userInfo !== null && Role[userInfo.role] !== Role.UNVERIFIED_USER && (
          <>
            <Route
              path="/"
              element={
                <Home>
                  <Languages />
                </Home>
              }
            />
            <Route path="/user" element={<User />} />
          </>
        )) || <Route path="/" element={<Auth page={AuthPages.LOGIN} />} />}
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
