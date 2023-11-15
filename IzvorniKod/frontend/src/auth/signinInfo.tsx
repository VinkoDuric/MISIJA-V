import { FormEvent } from "react";
import { Link } from "react-router-dom";
import { InputText, InputPassword, Button } from "../components/form";
import styles from "./styles/login.module.css";

export default function SigninInfo() {
  async function handleSubmit(
    event: FormEvent<HTMLFormElement>
  ): Promise<void> {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);
    formData.forEach((value, property) => console.log(value, property));
  }

  return (
    <div className={styles.loginInfoContainer}>
      <span className="xxlarge-text">SIGN IN</span>
      <form className={styles.loginForm} onSubmit={handleSubmit}>
        <InputPassword name="ime" placeholder="Name" />
        <InputPassword name="prezime" placeholder="Surname" />
        <InputText name="email" placeholder="E-mail" />

        <div className={styles.termsAndPolicy}>
          By signing up, you agree to the <a href={"#"}>Terms of Service</a> and{" "}
          <a href={"#"}>Privacy Policy</a>, including Cookie Use.
        </div>

        <Button className={styles.submitBtn}>SIGN IN</Button>
        <Link className={styles.createAccountLink} to="/login">
          Log in
        </Link>
      </form>
    </div>
  );
}
