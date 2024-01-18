import styles from './styles/card.module.css';
import { FC } from 'react';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';

interface CardProps {
    caption: string;
    handleClick?: () => void;
    adminIcon?: IconDefinition;
    handleAdminIconClick?: () => void;
};

interface ImgCardProps extends CardProps {
    imageSrc: string;
}

function adminIconClick(handler: (() => void)|undefined) {
    return (e: React.MouseEvent<SVGSVGElement, MouseEvent>) => {
        e.stopPropagation();
        if (handler) {
            handler();
        }
    }
}


const ImgCard: FC<ImgCardProps> = ({ imageSrc, caption, handleClick, adminIcon, handleAdminIconClick }) => {

    return (
        <div className={styles.card}>
            <div className={styles.cardHover} onClick={handleClick}>
            {
                adminIcon !== undefined && 
                <FontAwesomeIcon
                    icon={adminIcon}
                    className={styles.adminIcon}
                    onClick={adminIconClick(handleAdminIconClick)}/>
            }
            </div>
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

const IconCard: FC<IconCardProps> = ({ icon, handleClick, caption, adminIcon, handleAdminIconClick }) => {

    return (
        <div className={styles.card}>
            <div className={styles.cardHover} onClick={handleClick}>
            {
                adminIcon !== undefined && 
                <FontAwesomeIcon
                    icon={adminIcon}
                    className={styles.adminIcon}
                    onClick={adminIconClick(handleAdminIconClick)}/>
            }
            </div>
            <div className={styles.iconWrap}><FontAwesomeIcon icon={icon} size="4x"/></div>
            <div className={styles.caption}>{caption}</div>
        </div>
    )
};

export { ImgCard, IconCard };
