import styles from './styles/card.module.css';
import { FC } from 'react';

interface CardInput {
    caption: string;
    imageSrc: string;
}

const Card: FC<CardInput> = ({imageSrc, caption}) => {

    return (
        <div className={styles.card}>
          <div className={styles.cardHover}></div>
          <img
            alt={caption}
            src={imageSrc}
            width={"100%"}
          />
          <div className={styles.caption}>{caption}</div>
        </div>
    )
}


export { Card };