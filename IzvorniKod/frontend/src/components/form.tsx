import "./styles/form.css";
import { ChangeEvent, RefObject, useState } from "react";

interface CheckboxProps {
    label: string;
    name: string;
    checked?: boolean;
    onChange?: (checked: boolean) => void;
}

export function Checkbox({
    label,
    name,
    checked = false,
    onChange,
}: CheckboxProps) {
    const [isChecked, setIsChecked] = useState<boolean>(checked);

    const handleCheckboxChange = (event: ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = event.target.checked;
        setIsChecked(newCheckedValue);

        if (onChange) {
            onChange(newCheckedValue);
        }
    };

    return (
        <label>
            <input
                type="checkbox"
                checked={isChecked}
                onChange={handleCheckboxChange}
                name={name}
            />
            {` ${label}`}
        </label>
    );
}

interface InputTextProps {
    name: string;
    onChange?: (newValue: string) => void;
    placeholder?: string;
    className?: string;
    inputRef?: RefObject<HTMLInputElement>;
    value?: string;
}

export function InputText({
    name,
    onChange,
    placeholder,
    className,
    inputRef,
    value
}: InputTextProps) {
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value;
        if (onChange) {
            onChange(newValue);
        }
    };

    return (
        <input
            ref={inputRef}
            value={value}
            type="text"
            name={name}
            onChange={handleInputChange}
            placeholder={placeholder}
            className={`infoInputText ${className}`}
        />
    );
}

export function InputPassword({
    name,
    onChange,
    placeholder,
    className,
    inputRef
}: InputTextProps) {
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value;
        if (onChange) {
            onChange(newValue);
        }
    };

    return (
        <input
            ref={inputRef}
            type="password"
            name={name}
            onChange={handleInputChange}
            placeholder={placeholder}
            className={`infoInputText ${className}`}
        />
    );
}

/* OVDJE IDE OBRADA ZAHTJEVA I REDIRECTANJE NA FORMU */
