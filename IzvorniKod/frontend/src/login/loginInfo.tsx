import { FormEvent } from 'react';
import { Link } from 'react-router-dom';
import styles from './styles/login.module.css';
import { Button, Checkbox, InputText, InputPassword } from '../components/form';

export default function LoginInfo() {
  function handleSubmit(event: FormEvent<HTMLFormElement>): void {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);
    formData.forEach((value, property) => console.log(value, property));
  }

  return (
    <div className={styles.loginInfoContainer}>
      <span className={'xxlarge-text'}>USER LOGIN</span>
      <form className={styles.loginForm} onSubmit={handleSubmit}>
        <InputText name="email" placeholder="E-mail" />
        <InputPassword name="password" placeholder="Password" />
        <div>
          <Checkbox label="remember" name="remember" />
          <a className={styles.remember} href="#">
            Forgot password?
          </a>
        </div>
        <Button className={styles.submitBtn}>LOGIN</Button>
        <Link className={styles.createAccountLink} to="/signin">
          Create account
        </Link>
      </form>
    </div>
  );
}
