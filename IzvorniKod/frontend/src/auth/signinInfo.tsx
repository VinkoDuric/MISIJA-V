import { FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { InputText, InputPassword, Button } from "../components/form";
import styles from "./styles/login.module.css";

export default function SigninInfo() {
  const navigate = useNavigate();
  const [error, setError] = useState<String|null>(null);

  async function handleSubmit(
    event: FormEvent<HTMLFormElement>
  ): Promise<void> {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);
    formData.forEach((value, property) => console.log(value, property));
    
    let response = await fetch('/api/v1/auth/register', {
      method: "POST",
      body: JSON.stringify(Object.fromEntries(Array.from(formData))),
      headers: new Headers({'Content-Type': 'application/json'})
    });

    if (response.status === 201)
      navigate('/');
    else
      setError((await response.json())["error"]);
  }

  return (
    <div className={styles.loginInfoContainer}>
      <span className="xxlarge-text">Registracija</span>
      {
        error !== null &&
        <span className={styles.errorMsg}>
          { error } 
        </span>
      }
      <form className={styles.loginForm} onSubmit={handleSubmit}>
        <InputText name="firstName" placeholder="Ime" />
        <InputText name="lastName" placeholder="Prezime" />
        <InputText name="email" placeholder="E-mail" />

        <div className={styles.termsAndPolicy}>
          Registracijom pristajem na <a href={"#"}>Uvjete</a> i{" "}
          <a href={"#"}>Pravila privatnosti</a>, uključujući korištenje kolačića.
        </div>

        <Button className={styles.submitBtn}>Registriraj me</Button>
        <Link className={styles.createAccountLink} to="/login">
          Prijava
        </Link>
      </form>
    </div>
  );
}
