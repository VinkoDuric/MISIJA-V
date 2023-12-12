import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useUserContext } from "../userContext";
import "./styles/changepass.css";
import { FormEvent, useState } from "react";


export default function ChangePass() {
  let navigate = useNavigate();
  let { userInfo, updateUserInfo } = useUserContext(); 
  let [error, setError] = useState<boolean>(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);

    formData.forEach((value, property) => console.log(value, property));
    let inputData = Object.fromEntries(Array.from(formData));

    if (inputData.password1 != inputData.password2) {
      setError(true);
      return;
    }

    let data = {
      email: userInfo?.email,
      firstName: userInfo?.firstName,
      lastName: userInfo?.lastName,
      password: inputData.password1
    }

    fetch('/api/v1/account', {
      method: 'PUT',
      body: JSON.stringify(data),
      headers: new Headers({'Content-Type': 'application/json'})
    }).then(response => {
      console.log(response);
      if (response.ok) {
        return response.json()
      }
    }).then(json => {
      console.log(json);
      updateUserInfo(json);
      navigate('/home');
    });
  }

  return (
    <>
      <div className="changePassCard-user">
        <div className="row-user1">
          <img
            alt="FlipMemoLogo"
            className="logo-user"
            src="images/logo.svg"
            width={"100%"}
          />

          <div className="text-user">
            <p>Hey, {userInfo?.firstName}</p>
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
            <div>Ime: {userInfo?.firstName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Prezime: {userInfo?.lastName}</div>
            <div style={{ padding: "10px" }}></div>
            <div>Email: {userInfo?.email}</div>
            <div style={{ padding: "10px" }}></div>
            <form onSubmit={handleSubmit}>
              <div
                style={{
                  display: "flex",
                  flexDirection: "column",
                }}
              >
                <label htmlFor="newPassword">Unesite novu lozinku</label>
                <input id="newPassword" type="password" name="password1"></input>
                <div style={{ padding: "10px" }}></div>

                <label htmlFor="repeatPassword">Ponovite novu lozinku</label>
                <input id="repeatPassword" type="password" name="password2"></input>
                {error === true && <span style={{ color: "red" }}>Unesene lozinke su različite</span>}
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
