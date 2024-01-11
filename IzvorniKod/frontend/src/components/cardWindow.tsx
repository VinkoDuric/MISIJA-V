import { ReactNode } from 'react';
import styles from './styles/cardWindow.module.css';

type CardWindowProps = {
    children: ReactNode;
}

export function CardWindow({children}: CardWindowProps) {
    return (
        <div className={styles.changePassCard}>
            <div className={styles.changePassCardHead}>
                <img
                    alt="FlipMemoLogo"
                    src="images/logo.svg"
                    width={"250px"}
                />
            </div>
            <div className={styles.changePassCardBody}>
                {children}
            </div>
        </div>
    );
}
