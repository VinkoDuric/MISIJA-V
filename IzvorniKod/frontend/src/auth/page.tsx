import { lazy, Suspense } from 'react';
import styles from './styles/login.module.css';
import AuthPages from './authpages';
import LoginInfo from './loginInfo';
//import SigninInfo from './signinInfo';
import './styles/login.css';

interface LoginProps {
  page: AuthPages;
};

const SigninInfo = lazy(() => import('./signinInfo'));

export default function Login({page}: LoginProps) {
  return (
    <div className={styles.loginCard}>
      <img className={styles.logo} src="images/logo.svg" width={'70%'} />
      <img
        className={styles.loginImg}
        src="images/flipmemo-login.svg"
        width={'100%'}
      />
      {page === AuthPages.LOGIN && <Suspense fallback={<div>Loding...</div>}><LoginInfo /></Suspense>}
      {page === AuthPages.SIGNIN && <Suspense fallback={<div>Loding...</div>}><SigninInfo /></Suspense>}
    </div>
  );
}
