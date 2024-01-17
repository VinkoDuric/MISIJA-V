import styles from "./styles/home.module.css";
import { useHomeContext } from "./homeContext";
import { useEffect, useState } from "react";
import { OptionBtn } from "./components/optionBtn";
import { InputText } from "../components/form"; 
import VoiceRecorder from "./components/voiceRecorder"

export enum modes{
    ABC= 'ABC',
    text = 'text',
    voice = 'voice'
};

export function Quiz (){
    const { updateHomeText } = useHomeContext();

    const [mode, setMode] = useState(modes.voice);

    useEffect(() => {
        updateHomeText('Pitanje broj ?', '');
    }, []);

    return (
        <div className ={styles.quizWrapper}>
            <div>Ovdje ide tekst pitanja:</div>
            <div style={{ display: mode === modes.ABC ? 'block' : 'none' }}>
                <div className = {styles.answersWrapper} >
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 1  lsodfivhsi</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 2</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 3</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 4</OptionBtn>
                </div>
            </div>
            <div style={{ display: mode === modes.text ? 'block' : 'none' }}>
                <div className = {styles.answersWrapper} >
                    <InputText name = {"odgovor"} placeholder={"Ovdje unesite svoj odgovor."}></InputText>
                    <OptionBtn submit={true} onClick={() => { }} >Predaj!</OptionBtn>
                </div>
            </div>
            <div style={{ display: mode === modes.voice ? 'block' : 'none' }}>
                <VoiceRecorder></VoiceRecorder>
            </div>
        </div>
    );
}