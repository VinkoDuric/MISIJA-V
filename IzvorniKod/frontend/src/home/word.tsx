import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Button, ButtonType } from "../components/buttons";
import { InputText } from "../components/form";
import { Autocomplete } from "./components/autocomplete";
import { ItemsList } from "./components/itemsList";
import { useHomeContext } from "./homeContext";
import styles from './styles/word.module.css';

export function Word() {
    let { wordId } = useParams();
    let {updateHomeText} = useHomeContext();

    let [originalWord, setOriginalWord] = useState<string>('');
    let [translatedWord, setTranslatedWord] = useState<string>('');

    useEffect(() => {
        if (wordId === undefined) {
            updateHomeText('Dodavanje nove riječi', 'Dodajte novu riječ u aplikaciju.');
            return;
        }
        updateHomeText('Uređivanje riječi', 'Uredite postojeću riječ u aplikaciji.');
        // TODO : fetch word data
    }, []);

    function addSynonim(text: string) {

    }

    function addUsage(text: string) {

    }

    function handleSynonimClick() {

    }

    function handleSynonimRemove() {

    }

    function handleUsageClick() {

    }

    function handleUsageRemove() {

    }

    const synonimes = [
        {
            clickArg: 0,
            text: 'synonim1'
        },
        {
            clickArg: 1,
            text: 'synonim1'
        }
    ]

    return (
        <div>
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Riječi</h2>
                <div className={styles.sectionText}>Upišite stranu riječ i njezin prijevod.</div>
                <div className={styles.wordWrapper}>
                    <InputText onChange={setOriginalWord} name='originalWord' placeholder='Izvorna riječ' />
                    <InputText onChange={setTranslatedWord} name='translatedWord' placeholder='Prevedena riječ' />
                </div>
            </div>
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Sinonimi</h2>
                <div className={styles.sectionText}>Dodajte sinonime za stranu riječ na izvornom jeziku.</div>
                <Autocomplete placeholder='Sinonim riječi' btnText='dodaj' options={[]} handleSubmit={addSynonim}/>
                <ItemsList items={synonimes} handleClick={handleSynonimClick} icon={faTrash} handleIconClick={handleSynonimRemove} />
            </div>
            <div className={styles.section + ' ' + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Primjeri korištenja</h2>
                <div className={styles.sectionText}>Dodajte primjere korištenja riječi u izvornom jeziku.</div>
                <Autocomplete placeholder='Fraza' btnText='dodaj' options={[]} handleSubmit={addSynonim}/>
                <ItemsList items={synonimes} handleClick={handleUsageClick} icon={faTrash} handleIconClick={handleUsageRemove} />
            </div>
            <div className={styles.section + ' ' + styles.marginTop }>
                <h2 className={styles.sectionTitle}>Rječnici</h2>
                <div className={styles.sectionText}>Dodajte riječ u neki od postojećih rječnika.</div>
                <Autocomplete placeholder='Ime rječnika' options={[]} handleSubmit={addSynonim}/>
                <ItemsList items={synonimes} handleClick={handleUsageClick} icon={faTrash} handleIconClick={handleUsageRemove} />
            </div>
            <Button type={ButtonType.ACCENT} className={styles.saveBtn} >spremi</Button>
        </div>
    );
}
