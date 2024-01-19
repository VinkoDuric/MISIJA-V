import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { useEffect } from "react";
import { useNavigate} from "react-router-dom";
import { useHomeContext } from "./homeContext";

export function StudyMode (){
    const navigate = useNavigate();
    const { updateHomeText } = useHomeContext();


    useEffect(() => {
        updateHomeText('Odaberite mod učenja:', '');
    }, []);

    let studyMode:string;

    function handleClick(mode: string){
        console.log(mode);
        navigate(`/home/Quiz/${mode}`);
    }

    return (
        <div className={styles.menuWrapper}>
            <OptionBtn onClick={() => { handleClick("ABC"); }}>Odabir odgovora među ponuđenima</OptionBtn>
            <OptionBtn onClick={() => { handleClick("text"); }}>Unos odgovora</OptionBtn>
            <OptionBtn onClick={() => { handleClick("voice"); }}>Snimanje izgovora</OptionBtn>
        </div>
    );

}