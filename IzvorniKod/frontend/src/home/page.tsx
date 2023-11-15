import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useEffect, useState } from "react";
import "./styles/home.css";

export default function Home() {
  let navigate = useNavigate();
  let [serverText, setServerText] = useState<String | null>(null);
  let [userName, setUserName] = useState<String | null>(null);

  useEffect(() => {
    fetch("/api/v1/secured/admin")
      .then((response) => {
        if (response.ok) {
          return response.text();
        }
        throw new Error("Failed authentication");
      })
      .then((text) => setServerText(text))
      .catch((error) => console.log(error));

    fetch("/api/v1/user/name")
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Failed to fetch user name");
      })
      .then((data) => setUserName(data.name))
      .catch((error) => console.log(error));
  }, []);

  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      navigate("/");
    });
  }

  function onLogIn() {
    fetch("/api/v1/account").then(() => {
      navigate("/user");
    });
  }

  return (
    <>
      <div className="homeCard">
        <div className="row">
          <img
            alt="FlipMemoLogo"
            className="logo"
            src="images/logo.svg"
            width={"50%"}
          />

          <div className="row_second">
            <Button className="button_second" onClick={onLogIn}>
              Korisnički račun
            </Button>
            <Button className="button_second" onClick={onLogoutClick}>
              Odjava
            </Button>
          </div>
          <div className="text">
            <p>Hey, {userName || "Korisnik"}</p>
          </div>
        </div>
        <div className="homeCard_second">
          <div className="card">
            <img
              alt="FlipMemoLogo"
              className="flag"
              src="images/uk.svg"
              width={"100%"}
            />
            <div className="text_second">
              <p>Engleski</p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
