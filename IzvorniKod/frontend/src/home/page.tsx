import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import "./styles/home.css";
import { useUserContext } from "../userContext";

export default function Home() {
  let {userInfo, updateUserInfo} = useUserContext();
  let navigate = useNavigate();

  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      console.log("logout");
      updateUserInfo(null);
      navigate("/");
    });
  }

  function onLogIn() {
    navigate("/user");
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
            <p>Hey, {userInfo?.firstName || "Korisnik"}</p>
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
