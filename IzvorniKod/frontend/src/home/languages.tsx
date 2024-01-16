import { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Role, useUserContext } from "../userContext";
import { ImgCard, IconCard } from "./components/card";
import { useHomeContext } from "./homeContext";
import { faPlus, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles/home.module.css";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";


export function Languages() {
    const navigate = useNavigate();
    let { userInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
    useEffect(() => {
        updateHomeText('Odabir jezika', 'Odaberite jezik koji želite vježbati.');
    }, []);

    function openLang(lang: string) {
        return () => {
            navigate(`/home/${lang}`);
        }
    }

    function handleAdminIconCLick() {
        console.log("admin icon clicked");
    }

    let icon: IconDefinition|undefined = faTrashCan;

    if (userInfo === null || Role[userInfo.role] !== Role.ADMIN) {
        icon = undefined;
    }

    return (
        <div className={styles.gridWrapper}>
            <ImgCard caption="Engleski" imageSrc="/flags/hr.png" handleClick={openLang('en')} adminIcon={icon} handleAdminIconClick={handleAdminIconCLick} />
            <ImgCard caption="Engleski" imageSrc="/flags/gb.png" handleClick={openLang('en')} adminIcon={icon} handleAdminIconClick={handleAdminIconCLick} />
            <ImgCard caption="Engleski" imageSrc="/flags/rs.png" handleClick={openLang('en')} adminIcon={icon} handleAdminIconClick={handleAdminIconCLick} />
            <ImgCard caption="Engleski" imageSrc="/flags/us.png" handleClick={openLang('en')} adminIcon={icon} handleAdminIconClick={handleAdminIconCLick} />
            {
                //userInfo !== null && Role[userInfo.role] === Role.ADMIN &&
                <IconCard
                    caption="Dodaj jezik"
                    icon={faPlus}
                    handleClick={() => navigate('/add/language')} />
            }
        </div>
    );
}
