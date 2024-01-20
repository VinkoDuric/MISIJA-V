import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import styles from './styles/itemsList.module.css'

type ItemClickCallback = (arg: number) => void;

type ItemProps = {
    clickArg: number;
    text: string;
    icon?: IconDefinition;
    handleIconClick?: ItemClickCallback;
    handleClick: ItemClickCallback;
};

function Item({ text, icon, clickArg, handleIconClick, handleClick }: ItemProps) {

    return (
        <div className={styles.item} onClick={() => handleClick(clickArg)}>
            <div className={styles.itemText}>{text}</div>
            {
                icon !== undefined && handleIconClick !== undefined &&
                <div>
                    <FontAwesomeIcon
                        icon={icon}
                        onClick={(e) => { e.stopPropagation(); handleIconClick(clickArg); }}
                        className={styles.itemIcon} />
                </div>
            }
        </div>
    );
}

export type ItemsListElement = {
 clickArg: number, text: string 
};

type ItemsListProps = {
    items: Array<ItemsListElement>;
    icon?: IconDefinition;
    handleIconClick?: ItemClickCallback;
    handleClick: ItemClickCallback;
    className?: string;
};

export function ItemsList({items, icon, handleIconClick, handleClick, className }: ItemsListProps) {

    return (
        <div className={`${styles.itemsList} ${className}`}>
            {
                items.map(item =>
                    <Item
                        key={item.clickArg}
                        text={item.text}
                        icon={icon}
                        handleClick={handleClick}
                        handleIconClick={handleIconClick}
                        clickArg={item.clickArg} />)
            }
        </div>
    );
}
