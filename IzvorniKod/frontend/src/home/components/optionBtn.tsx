import { ReactNode } from "react";
import styles from "./styles/optionBtn.module.css";

interface OptionProps {
    children: ReactNode | string;
    onClick: () => void;
}

export function OptionBtn({children, onClick}: OptionProps) {
    return (
        <button className={styles.optionWrapper} onClick={onClick}> {children} </button>
    );
}
