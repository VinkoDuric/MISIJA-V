import styles from "./styles/home.module.css";
import { useHomeContext } from "./homeContext";
import { useEffect, useState } from "react";
import { OptionBtn } from "./components/optionBtn";
import { InputText } from "../components/form"; 
import VoiceRecorder from "./components/voiceRecorder"

interface QuizProps {
  studyMode: string;
}

export function Quiz ({ studyMode }: QuizProps){
    const { updateHomeText } = useHomeContext();

    useEffect(() => {
        updateHomeText('Pitanje broj ?', '');
    }, []);

    return (
        <div className ={styles.quizWrapper}>
            <div>Ovdje ide tekst pitanja</div>
            <div>
            {
                studyMode === "ABC" &&
                <div className = {styles.answersWrapper} >
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 1  lsodfivhsi</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 2</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 3</OptionBtn>
                    <OptionBtn answer={true} onClick={() => { }}>Odgovor 4</OptionBtn>
                </div>
            }
            {
                studyMode === "text" &&
                <div className = {styles.quizWrapper} >
                    <InputText name = {"odgovor"} placeholder={"Ovdje unesite svoj odgovor."}></InputText>
                    <OptionBtn submit={true} onClick={() => { }} >Predaj!</OptionBtn>
                </div>
            }
            {
                studyMode === "voice" &&
                <VoiceRecorder></VoiceRecorder>
            }
            </div>
        </div>
    );
}
