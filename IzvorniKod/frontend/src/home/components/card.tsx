import styles from './styles/card.module.css';
import { FC } from 'react';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface CardProps {
    caption: string;
    onClick?: () => void;
};

interface ImgCardProps extends CardProps {
    imageSrc: string;
}

const ImgCard: FC<ImgCardProps> = ({ imageSrc, caption, onClick }) => {

    return (
        <div className={styles.card}>
            <div className={styles.cardHover} onClick={onClick}></div>
            <img
                alt={caption}
                src={imageSrc}
                className={styles.cardImg}
                width={"100%"}
            />
            <div className={styles.caption}>{caption}</div>
        </div>
    )
};

interface IconCardProps extends CardProps {
    icon: IconDefinition;
}

const IconCard: FC<IconCardProps> = ({ icon, onClick, caption }) => {

    return (
        <div className={styles.card}>
            <div className={styles.cardHover} onClick={onClick}></div>
            <div className={styles.iconWrap}><FontAwesomeIcon icon={icon} size="4x"/></div>
            <div className={styles.caption}>{caption}</div>
        </div>
    )
};

export { ImgCard, IconCard };
