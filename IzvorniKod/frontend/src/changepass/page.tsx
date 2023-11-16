import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useEffect, useState } from "react";
import "./styles/changepass.css";

interface ChangePass {
  email: string;
  firstName: string;
  lastName: string;
}

export default function ChangePass() {
  let navigate = useNavigate();
  let [serverText, setServerText] = useState<String | null>(null);
  let [userName, setUserName] = useState<String | null>(null);

  const [user, setUser] = useState<ChangePass | undefined>(undefined);
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

  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      navigate("/");
    });
  }

  return (
    <>
      <div className="homeCard-user">
        <div className="row-user1">
          <img
            alt="FlipMemoLogo"
            className="logo-user"
            src="images/logo.svg"
            width={"100%"}
          />

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
            <div>Informacije korisničkog računa: </div>
            <div style={{ padding: "10px" }}></div>
            <div>Ime: {user?.firstName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Prezime: {user?.lastName}</div>
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
                <label htmlFor="newPassword">Unesi novu lozinku</label>
                <input id="newPassword"></input>
                <div style={{ padding: "10px" }}></div>

                <label htmlFor="repeatPassword">Ponovi novu lozinku</label>
                <input id="repeatPassword"></input>
                <div style={{ padding: "20px" }}></div>
                <Button className="button_second-user1">Potvrdi</Button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
