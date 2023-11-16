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
            <div style={{fontWeight: "bold"}}>Informacije korisničkog računa: </div>
            <div style={{ padding: "20px"}}></div>
            <div>Ime: {user?.firstName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Prezime: {user?.lastName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Email: {user?.email}</div>
            <div style={{ padding: "10px" }}></div>
            <div style={{ padding: "20px" }}></div>
            <Button className="button_edit">Uredi</Button>
            <div style={{ padding: "20px" }}></div>
            <Button className="button_change_pass">Promjena lozinke</Button>
          </div>
        </div>
      </div>
    </>
  );
}
