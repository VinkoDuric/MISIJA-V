import { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Card } from "./components/card";
import { useHomeContext } from "./homeContext";
import styles from "./styles/home.module.css";

export function Languages() {
    const navigate = useNavigate();
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
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
            <Card caption="Engleski" imageSrc="/images/uk.svg" onClick={openLang('en')}/>
        </div>
    );
}
