import { ReactNode } from "react";
import styles from "./styles/optionBtn.module.css";

interface OptionProps {
    children: ReactNode | string;
    onClick: () => void;
    accent?: boolean;
}

export function OptionBtn({accent, children, onClick}: OptionProps) {
    return (
        <button className={`${styles.optionWrapper} ${accent && styles.accentColor}`} onClick={onClick}> {children} </button>
    );
}
