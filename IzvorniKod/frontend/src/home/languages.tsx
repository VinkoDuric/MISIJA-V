import { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { useUserContext } from "../userContext";
import { ImgCard, IconCard } from "./components/card";
import { useHomeContext } from "./homeContext";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles/home.module.css";

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

    return (
        <div className={styles.gridWrapper}>
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            <ImgCard caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')} />
            {
                //userInfo !== null && Role[userInfo.role] === Role.ADMIN &&
                <IconCard
                    caption="Dodaj rječnik"
                    icon={faPlus}
                    onClick={() => navigate('/add/language')} />
            }
        </div>
    );
}
