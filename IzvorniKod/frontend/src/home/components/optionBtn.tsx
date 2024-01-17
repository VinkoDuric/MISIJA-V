import { ReactNode } from "react";
import styles from "./styles/optionBtn.module.css";

interface OptionProps {
    children: ReactNode | string;
    onClick: () => void;
    accent?: boolean;
    answer?: boolean;
    submit?: boolean;
}

export function OptionBtn({accent, answer, submit, children, onClick}: OptionProps) {
    return (
        <button className={`${styles.optionWrapper} ${accent && styles.accentColor}  ${answer && styles.answerColor} ${submit && styles.submitColor}`} onClick={onClick}> {children} </button>
    );
}
