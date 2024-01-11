import { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Card } from "./components/card";
import { HomeHooks } from "./homeHooks";
import styles from "./styles/home.module.css";

export function Languages({ updateHomeText }: HomeHooks) {
    const navigate = useNavigate();
    useEffect(() => {
        updateHomeText('Odabir jezika', 'Odaberite jezik koji želite vježbati.');
    }, []);

    function openLang(lang: string) {
        console.log(lang)
        return () => {
            console.log('lang')
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
