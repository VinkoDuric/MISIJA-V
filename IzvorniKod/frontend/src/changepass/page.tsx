import { useNavigate } from "react-router-dom";
import { Button, InputPassword, InputText } from "../components/form";
import { useUserContext } from "../userContext";
import styles from "./styles/changepass.module.css";
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
    <div className={styles.changePassCard}>
      <div className={styles.changePassCardHead}>
        <img
          alt="FlipMemoLogo"
          src="images/logo.svg"
          width={"250px"}
        />

        <div className={styles.userInfo}>
          <div className={styles.userName}>{userInfo?.firstName} {userInfo?.lastName}</div>
        </div>
      </div>
      <div className={styles.changePassCardBody}>
        <div className={styles.heading}>Čestitamo na prvoj prijavi!</div>
        <div className={styles.text}>
          Nakon prve prijave nužna je promjena lozinke.
          Molimo Vas da promjenite lozinku kako biste mogli nastaviti koristiti aplikaciju.
        </div>
        <form onSubmit={handleSubmit}>
          <InputPassword name='password1' placeholder="Unesite novu lozinku"/>
          <InputPassword name="password2" placeholder="Ponovite novu lozinku"/>
          {error === true && <div className={styles.errorMsg}>Unesene lozinke su različite</div>}
          <Button className={styles.submitBtn}>Potvrdi</Button>
        </form>
      </div>
    </div>
  );
}
