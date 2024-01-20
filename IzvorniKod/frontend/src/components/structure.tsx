import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { ReactNode } from 'react';
import { IconButton } from './buttons';
import styles from './styles/structure.module.css';

type AppCardProps = {
    children: ReactNode;
}

export function AppCard({ children }: AppCardProps) {
    return (
        <div className={styles.appCard}>
            <div className={styles.appCardHead}>
                <img
                    alt="FlipMemoLogo"
                    src="images/logo.svg"
                    width={"250px"}
                />
            </div>
            <div className={styles.appCardBody}>
                {children}
            </div>
        </div>
    );
}

type AppWindowProps = {
    title: string;
    footer: string;
    icon?: IconDefinition;
    handleIconClick?: () => void;
    children: ReactNode;
};

export function AppWindow({title, footer, icon, handleIconClick, children}: AppWindowProps) {
    return (
        <div className={styles.appWindow}>
            <div className={styles.appWindowHeader}>
                <img
                    alt="FlipMemoLogo"
                    className={styles.appWindowLogo}
                    src="/images/logo.svg"
                />
                <div className={styles.appWindowTitle}>{title}</div>
                <div className={styles.appWindowIcon} onClick={handleIconClick}>
                {icon !== undefined && <IconButton icon={icon} />}
                </div>
            </div>
            <div className={styles.appWindowContent}>
                {children}
                <div className={styles.appWindowFooter}>{footer}</div>
            </div>
        </div>
    );
}

