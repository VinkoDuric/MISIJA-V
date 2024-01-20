import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./styles/buttons.css";
import { IconDefinition } from "@fortawesome/fontawesome-svg-core";

export enum ButtonType {
  ACCENT,
  NORMAL
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

interface IconButtonProps {
    icon: IconDefinition;
    className?: string;
    onClick?: () => void;
}

export function IconButton({icon, onClick, className}: IconButtonProps) {
    return (
        <div className={"iconBtn"}>
          <FontAwesomeIcon className={className} onClick={onClick} icon={icon} />
        </div>
    );

}