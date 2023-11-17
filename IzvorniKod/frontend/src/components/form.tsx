import "./styles/form.css";
import { ChangeEvent, useState } from "react";

export enum ButtonType {
  ACCENT,
}

interface ButtonProps {
  children: string;
  type?: ButtonType;
  className?: string;
  onClick?: () => void;
}

export function Button({
  children,
  type = ButtonType.ACCENT,
  className,
  onClick,
}: ButtonProps) {
  const btnClass = type === ButtonType.ACCENT ? "accentBtn" : "";
  return (
    <button onClick={onClick} className={`${btnClass} ${className}`}>
      {children}
    </button>
  );
}

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
  value?: string;
  onChange?: (newValue: string) => void;
  placeholder?: string;
  className?: string;
}

export function InputText({
  value,
  name,
  onChange,
  placeholder,
  className,
}: InputTextProps) {
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = event.target.value;
    if (onChange) {
      onChange(newValue);
    }
  };

  return (
    <input
      type="text"
      value={value}
      name={name}
      onChange={handleInputChange}
      placeholder={placeholder}
      className={`infoInputText ${className}`}
    />
  );
}

export function InputPassword({
  value,
  name,
  onChange,
  placeholder,
  className,
}: InputTextProps) {
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = event.target.value;
    if (onChange) {
      onChange(newValue);
    }
  };

  return (
    <input
      type="password"
      value={value}
      name={name}
      onChange={handleInputChange}
      placeholder={placeholder}
      className={`infoInputText ${className}`}
    />
  );
}

/* OVDJE IDE OBRADA ZAHTJEVA I REDIRECTANJE NA FORMU */
