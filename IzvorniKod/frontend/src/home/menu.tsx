import { HomeHooks } from "./homeHooks";
import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/menu.module.css";
import { useUserContext } from "../userContext";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export function Menu({ updateHomeText }: HomeHooks) {
    const navigate = useNavigate();
    let { updateUserInfo } = useUserContext();

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
            <OptionBtn onClick={() => { }}>Upravljanje računom</OptionBtn>
            <OptionBtn onClick={() => { }}>Dodaj admina</OptionBtn>
            <OptionBtn onClick={() => { }}>Dodaj riječ</OptionBtn>
            <OptionBtn onClick={onLogoutClick}>Odjava</OptionBtn>
        </div>
    );
}
