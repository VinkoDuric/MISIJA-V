import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useEffect, useState } from "react";
import { useUserContext } from "../userContext";
import "./styles/user.css";

interface User {
  email: string;
  firstName: string;
  lastName: string;
}

export default function User() {
  let navigate = useNavigate();

  let {userInfo} = useUserContext();


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
            <div>Ime: {userInfo?.firstName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Prezime: {userInfo?.lastName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Email: {userInfo?.email}</div>
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
