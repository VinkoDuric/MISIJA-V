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
  const navigate = useNavigate();
  const { userInfo } = useUserContext();
  let { updateUserInfo } = useUserContext();
  const { updateHomeText } = useHomeContext();
  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      updateUserInfo(null);
      navigate("/");
    });
  }

  useEffect(() => {
    updateHomeText("Izbornik", "");
  }, []);

  return (
    <div className={styles.menuWrapper}>
      <OptionBtn
        onClick={() => {
          navigate("/home");
          closeMenu();
        }}
      >
        Odabir jezika
      </OptionBtn>
      <OptionBtn
        onClick={() => {
          navigate("/home/account");
          closeMenu();
        }}
      >
        Upravljanje računom
      </OptionBtn>
      {userInfo !== null && Role[userInfo.role] === Role.ADMIN && (
        <>
          <OptionBtn
            onClick={() => {
              navigate("/home/addadmin");
              closeMenu();
            }}
          >
            Dodaj admina
          </OptionBtn>
          <OptionBtn
            onClick={() => {
              navigate("/word");
              closeMenu();
            }}
          >
            Dodaj riječ
          </OptionBtn>
        </>
      )}
      <OptionBtn accent={true} onClick={onLogoutClick}>
        Odjava
      </OptionBtn>
    </div>
  );
}
