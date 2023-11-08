import { lazy, Suspense } from 'react';
import styles from './styles/login.module.css';
import AuthPages from './authpages';
import LoginInfo from './loginInfo';
//import SigninInfo from './signinInfo';
import './styles/login.css';

interface AuthProps {
  page: AuthPages;
};

const SigninInfo = lazy(() => import('./signinInfo'));

export default function Auth({page}: AuthProps) {
  return (
    <div className={styles.loginCard}>
      <img
        alt='FlipMemoLogo'
        className={styles.logo}
        src="images/logo.svg"
        width={'70%'} />
      <img
        alt='LoginImage'
        className={styles.loginImg}
        src="images/flipmemo-login.svg"
        width={'100%'}
      />
      {page === AuthPages.LOGIN && <LoginInfo />}
      {page === AuthPages.SIGNIN && <Suspense fallback={<div>Loding...</div>}><SigninInfo /></Suspense>}
    </div>
  );
}
