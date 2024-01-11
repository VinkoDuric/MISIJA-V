import styles from './styles/card.module.css';
import { FC } from 'react';

interface CardInput {
    caption: string;
    imageSrc: string;
    onClick?: () => void;
}

const Card: FC<CardInput> = ({imageSrc, caption, onClick}) => {

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
}


export { Card };
