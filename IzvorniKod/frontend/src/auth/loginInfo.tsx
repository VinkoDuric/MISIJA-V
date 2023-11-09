import { FormEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './styles/login.module.css';
import { useNavigate } from 'react-router-dom';
import { Button, Checkbox, InputText, InputPassword } from '../components/form';

export default function LoginInfo() {

  const [error, setError] = useState<boolean>(false);
  const navigate = useNavigate();

  async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);
    formData.forEach((value, property) => console.log(value, property));
    
    let response = await fetch('/api/v1/auth/login', {
      method: "POST",
      body: JSON.stringify(Object.fromEntries(Array.from(formData))),
      headers: new Headers({'Content-Type': 'application/json'})
    });

    if (response.status === 200)
      navigate('/home');
    else
      setError(true);
  }

  return (
    <div className={styles.loginInfoContainer}>
      <span className={'xxlarge-text'}>
        USER LOGIN
      </span>
      {
        error === true &&
        <span className={styles.errorMsg}>
          You have entered an invalid username or password.
        </span>
      }
      <form
        className={styles.loginForm}
        onSubmit={handleSubmit}
        >
        
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
