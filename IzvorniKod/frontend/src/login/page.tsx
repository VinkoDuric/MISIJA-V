import styles from './styles/login.module.css';
import LoginInfo from './loginInfo';
import SigninInfo from './signinInfo';
import './styles/login.css';

interface LoginProps {
  path: string;
};

export default function Login({path}: LoginProps) {
  return (
    <div className={styles.loginCard}>
      <img className={styles.logo} src="images/logo.svg" width={'70%'} />
      <img
        className={styles.loginImg}
        src="images/flipmemo-login.svg"
        width={'100%'}
      />
      {path === '/login' && <LoginInfo />}
      {path === '/signin' && <SigninInfo />}
    </div>
  );
}
