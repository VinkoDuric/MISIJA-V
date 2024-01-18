import styles from './styles/addLanguage.module.css'
import { useEffect, useState } from "react";
import { useHomeContext } from "./homeContext";
import { InputText } from "../components/form";
import { useNavigate } from 'react-router-dom';
import { LanguageList } from './components/languageList';

export function AddLanguage() {
    const { updateHomeText } = useHomeContext();
    const [langs, setLangs] = useState<LanguageData[] | null>(null);
    const [input, setInput] = useState<string|null>(null);
    const navigate = useNavigate();

    async function fetchLanguages() {
        fetch('/languages.json').then(res => res.json()).then((langs: LanguageData[]) => setLangs(langs));
    }

    useEffect(() => {
        updateHomeText('Dodavanje jezika', 'Dodajte novi jezik u aplikaciju.');
        fetchLanguages();
    }, []);

    function onLangClick(langCode: string) {
        // TODO: add language to app
        console.log('Language picked: ' + langCode);
        navigate('/home');
    }

    return (
        <div>
            <div>
                <InputText name="lang" placeholder="Ime jezika" onChange={setInput} />
            </div>
            <LanguageList languages={langs?.filter(lang => lang.name.includes(input??'')) ?? null} onLangClick={onLangClick}/>
        </div>
    );
}

