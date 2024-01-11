import { useNavigate } from "react-router-dom";
import styles from "./styles/home.module.css"; 
import { useUserContext } from "../userContext";

import { faBars } from '@fortawesome/free-solid-svg-icons';
import { IconButton } from "../components/buttons";
import { Languages } from "./languages";

export default function Home() {
  let {userInfo, updateUserInfo} = useUserContext();
  let navigate = useNavigate();

  function onLogoutClick() {
    fetch("/api/v1/auth/logout").then(() => {
      console.log("logout");
      updateUserInfo(null);
    });
  }

  function onSettingsClick() {
    navigate("/user");
  }

  return (
    <div className={styles.contentWindow}>

      <div className={styles.contentWindowHeader}>
        <img
          alt="FlipMemoLogo"
          className={styles.logo}
          src="images/logo-icon.svg"
        />
        <div className={styles.title}>{userInfo?.firstName || "Odabir jezika"}</div>
        <div className={styles.settings}>
          <IconButton icon={faBars}/>
        </div>
      </div>
      <div className={styles.contentWindowMain}>
        <div className={styles.pageText}>Odaberite jezik koji želite vježbati.</div>
        <Languages/>
        <div className={styles.userName}>{userInfo?.firstName || "Ivan Cvrk"}</div>
      </div>
    </div>
  );
}
