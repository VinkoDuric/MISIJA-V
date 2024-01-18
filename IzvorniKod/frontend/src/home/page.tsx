import styles from "./styles/home.module.css";
import { useUserContext } from "../userContext";
import { faBars, faHouse } from "@fortawesome/free-solid-svg-icons";
import { Menu } from "./menu";
import { ReactNode, useState } from "react";
import { AppWindow } from "../components/structure";
import { HomeContextProvider, useHomeContext } from "./homeContext";

type HomeProps = {
  children: ReactNode;
};

function HomePage({ children }: HomeProps) {
  let { userInfo, updateUserInfo } = useUserContext();
  let { text, updateHomeText } = useHomeContext();

  let [menuOpen, setMenuOpen] = useState<boolean>(false);

  function onMenuClick() {
    setMenuOpen((open) => !open);
  }

  return (
    <AppWindow
      title={text.title}
      footer={userInfo?.firstName + " " + userInfo?.lastName}
      icon={menuOpen ? faHouse : faBars}
      handleIconClick={onMenuClick}
    >
      {text.caption !== null && (
        <div className={styles.pageCaption}>{text.caption}</div>
      )}
      {menuOpen === false && children}
      {menuOpen === true && <Menu closeMenu={() => setMenuOpen(false)} />}
    </AppWindow>
  );
}

export default function Home({ children }: HomeProps) {
  return (
    <HomeContextProvider>
      <HomePage>{children}</HomePage>
    </HomeContextProvider>
  );
}
