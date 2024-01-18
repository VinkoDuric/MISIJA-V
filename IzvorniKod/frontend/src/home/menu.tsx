import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { Role, useUserContext } from "../userContext";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";

type MenuProps = {
  closeMenu: () => void;
};

export function Menu({ closeMenu }: MenuProps) {
<<<<<<< HEAD
  const navigate = useNavigate();
  let { updateUserInfo } = useUserContext();
  const { updateHomeText } = useHomeContext();
=======
    const navigate = useNavigate();
    const { userInfo } = useUserContext();
    let { updateUserInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
>>>>>>> b6c77e683d87eab642442eefa9b50b624c853546

  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      updateUserInfo(null);
      navigate("/");
    });
  }

  useEffect(() => {
    updateHomeText("Izbornik", "");
  }, []);

<<<<<<< HEAD
  return (
    <div className={styles.menuWrapper}>
      <OptionBtn
        onClick={() => {
          navigate("/home");
          closeMenu();
        }}
      >
        Početna stranica
      </OptionBtn>
      <OptionBtn
        onClick={() => {
          closeMenu();
        }}
      >
        Upravljanje računom
      </OptionBtn>
      <OptionBtn
        onClick={() => {
          closeMenu();
          navigate("/home/add-admin");
        }}
      >
        Dodaj admina
      </OptionBtn>
      <OptionBtn onClick={() => {}}>Dodaj riječ</OptionBtn>
      <OptionBtn accent={true} onClick={onLogoutClick}>
        Odjava
      </OptionBtn>
    </div>
  );
=======
    return (
        <div className={styles.menuWrapper}>
            <OptionBtn onClick={() => { navigate('/home'); closeMenu(); }}>Odabir jezika</OptionBtn>
            <OptionBtn onClick={() => { }}>Upravljanje računom</OptionBtn>
            {
                userInfo !== null && Role[userInfo.role] === Role.ADMIN &&
                (
                    <>
                        <OptionBtn onClick={() => { }}>Dodaj admina</OptionBtn>
                        <OptionBtn onClick={() => { navigate('/word'); closeMenu(); }}>Dodaj riječ</OptionBtn>
                    </>
                )
            }
            <OptionBtn accent={true} onClick={onLogoutClick}>Odjava</OptionBtn>
        </div>
    );
>>>>>>> b6c77e683d87eab642442eefa9b50b624c853546
}
