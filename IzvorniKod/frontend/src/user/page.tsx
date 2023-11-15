import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useEffect, useState } from "react";
import "./styles/user.css";

interface User {
  email: string;
  firstName: string;
  lastName: string;
}

export default function User() {
  let navigate = useNavigate();
  let [serverText, setServerText] = useState<String | null>(null);
  let [userName, setUserName] = useState<String | null>(null);

  const [user, setUser] = useState<User | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    fetch(process.env.API_BASE_LOCAL + "api/v1/user")
      .then((res) => res.json())
      .then((data) => setUser(data))
      .catch(() =>
        setUser({
          email: "fejk@email.com",
          firstName: "Ante",
          lastName: "Horvat",
        })
      )
      .finally(() => setIsLoading(false));
  }, []);

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

  function goBack() {
    fetch("/api/v1/account").then(() => {
      navigate("/home");
    });
  }

  return (
    <>
      <div className="homeCard-user">
        <div className="row-user">
          <img
            alt="FlipMemoLogo"
            className="logo-user"
            src="images/logo.svg"
            width={"100%"}
          />

          <div className="row_second-user">
            <Button className="button_second-user" onClick={goBack}>
              Vrati
            </Button>
            <Button className="button_second-user" onClick={onLogoutClick}>
              Odjava
            </Button>
          </div>
          <div className="text-user">
            <p>Hey, {user?.firstName}</p>
          </div>
        </div>
        <div className="homeCard_second-user">
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              padding: "40px",
            }}
          >
            <div>Your account information:</div>
            <div style={{ padding: "10px" }}></div>
            <div>Name: {user?.firstName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Last name: {user?.lastName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Email: {user?.email}</div>
            <div style={{ padding: "10px" }}></div>
            <form>
              <div
                style={{
                  display: "flex",
                  flexDirection: "column",
                }}
              >
                <label htmlFor="newPassword">New password</label>
                <input id="newPassword"></input>
                <div style={{ padding: "10px" }}></div>

                <label htmlFor="repeatPassword">Repeat new password</label>
                <input id="repeatPassword"></input>
                <div style={{ padding: "20px" }}></div>
                <Button className="button_second-user1">Submit</Button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
