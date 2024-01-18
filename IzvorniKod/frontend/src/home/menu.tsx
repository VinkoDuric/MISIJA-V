import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { useUserContext } from "../userContext";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";
import { Account } from "./account";

type MenuProps = {
    closeMenu: () => void;
}

export function Menu({ closeMenu }: MenuProps) {
    const navigate = useNavigate();
    let { updateUserInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
    const [isAccountOpen, setIsAccountOpen] = useState(false);

    const openAccount = () => {
        setIsAccountOpen(true);
      };
    
    const closeAccount = () => {
        setIsAccountOpen(false);
    };

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
            {!isAccountOpen && (
                <>
                    <OptionBtn onClick={() => { navigate('/home'); closeMenu(); }}>Početna stranica</OptionBtn>
                    <OptionBtn onClick={() => { openAccount();}}>Upravljanje računom</OptionBtn>
                    <OptionBtn onClick={() => { }}>Dodaj admina</OptionBtn>
                    <OptionBtn onClick={() => { }}>Dodaj riječ</OptionBtn>
                    <OptionBtn accent={true} onClick={onLogoutClick}>Odjava</OptionBtn>
                </>
            )}
            {isAccountOpen === true && (<Account closeAccount={closeAccount} />)}
        </div>  
    );
}
