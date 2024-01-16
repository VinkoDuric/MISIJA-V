import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { useEffect } from "react";
import { useHomeContext } from "./homeContext";

export function StudyMode (){

    const { updateHomeText } = useHomeContext();


    useEffect(() => {
        updateHomeText('Odaberite mod učenja:', '');
    }, []);

    return (
        <div className={styles.menuWrapper}>
            <OptionBtn onClick={() => { }}>Odabir odgovora među ponuđenima</OptionBtn>
            <OptionBtn onClick={() => { }}>Unos odgovora</OptionBtn>
            <OptionBtn onClick={() => { }}>Snimanje izgovora</OptionBtn>
        </div>
    );

}