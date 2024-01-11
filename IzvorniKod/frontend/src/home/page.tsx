import styles from "./styles/home.module.css";
import { useUserContext } from "../userContext";
import { faBars, faHouse } from '@fortawesome/free-solid-svg-icons';
import { IconButton } from "../components/buttons";
import { Languages } from "./languages";
import { Dictionaries } from "./dictionaries";
import { Menu } from "./menu";
import { useState } from "react";

export enum HomePage {
    LANGUAGES,
    DICTIONARIES
}

type HomeProps = {
    page: HomePage;
}

export default function Home({ page }: HomeProps) {
    let { userInfo } = useUserContext();

    let [menuOpen, setMenuOpen] = useState<boolean>(false);
    let [title, setTitle] = useState<string>('');
    let [caption, setCaption] = useState<string | null>(null);

    function onMenuClick() {
        setMenuOpen(open => !open);
    }

    function updateHomeText(title: string, caption: string | null) {
        setTitle(title);
        setCaption(caption);
    }

    function renderContent(): JSX.Element {
        switch (page) {
            case HomePage.LANGUAGES: {
                return <Languages updateHomeText={updateHomeText} />;
            }
            case HomePage.DICTIONARIES: {
                return <Dictionaries updateHomeText={updateHomeText} />;
            }
        }
    }

    return (
        <div className={`${styles.contentWindow}`}>

            <div className={styles.contentWindowHeader}>
                <img
                    alt="FlipMemoLogo"
                    className={styles.logo}
                    src="/images/logo.svg"
                />
                <div className={styles.title}>{title}</div>
                <div className={styles.menu} onClick={onMenuClick}>
                    <IconButton icon={menuOpen ? faHouse : faBars} />
                </div>
            </div>
            <div className={styles.contentWindowMain}>
                {caption !== null && <div className={styles.pageText}>{caption}</div>}
                {menuOpen === false && renderContent()}
                {menuOpen === true && <Menu updateHomeText={updateHomeText} />}
                <div className={styles.userName}>{userInfo?.firstName || "Ivan Cvrk"}</div>
            </div>
        </div>
    );
}
