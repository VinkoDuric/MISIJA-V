import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { useUserContext } from "../userContext";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";

type MenuProps = {
    closeMenu: () => void;
}

export function Menu({ closeMenu }: MenuProps) {
    const navigate = useNavigate();
    let { updateUserInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();

    function onLogoutClick() {
        fetch("/api/v1/auth/logout").then(() => {
            updateUserInfo(null);
            navigate('/');
        });
    }

    useEffect(() => {
        updateHomeText('Izbornik', '');
    }, []);

    return (
        <div className={styles.menuWrapper}>
            <OptionBtn onClick={() => { navigate('/home'); closeMenu(); }}>Početna stranica</OptionBtn>
            <OptionBtn onClick={() => { closeMenu(); }}>Upravljanje računom</OptionBtn>
            <OptionBtn onClick={() => { }}>Dodaj admina</OptionBtn>
            <OptionBtn onClick={() => { }}>Dodaj riječ</OptionBtn>
            <OptionBtn onClick={onLogoutClick}>Odjava</OptionBtn>
        </div>
    );
}
