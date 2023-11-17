import { FormEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './styles/login.module.css';
import { useNavigate } from 'react-router-dom';
import { Button, Checkbox, InputText, InputPassword } from '../components/form';
import { useUserContext, Role, UserInfo } from '../userContext';

export default function LoginInfo() {
  const { userInfo, updateUserInfo } = useUserContext();

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

    if (response.status === 200) {
      let json = await response.json();
      updateUserInfo(json);
      if (json.role !== 'UNVERIFIED_USER') {
        navigate('/home');
      } else {
        navigate('/changepass');
      }
    }
    else
      setError(true);
  }

  return (
    <div className={styles.loginInfoContainer}>
      <span className={'xxlarge-text'}>
        Prijava
      </span>
      {
        error === true &&
        <span className={styles.errorMsg}>
          Uneseni e-mail ili lozinka su netočni.
        </span>
      }
      <form
        className={styles.loginForm}
        onSubmit={handleSubmit}
        >
        
        <InputText name="email" placeholder="E-mail" />
        <InputPassword name="password" placeholder="Lozinka" />
        
        <div>
          <Checkbox label="zapamti me" name="remember" />
          <a className={styles.remember} href="#">
            Zaboravili ste lozinku?
          </a>
        </div>
        
        <Button className={styles.submitBtn}>Prijava</Button>
        
        <Link className={styles.createAccountLink} to="/signin">
          Registracija
        </Link>
      </form>
    </div>
  );
}
