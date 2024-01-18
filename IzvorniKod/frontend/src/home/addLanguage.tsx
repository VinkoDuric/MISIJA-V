import { useEffect, useState } from "react";
import { useHomeContext } from "./homeContext";
import { InputText } from "../components/form";
import { useNavigate } from 'react-router-dom';
import { LanguageList } from './components/languageList';

export function AddLanguage() {
    const { updateHomeText } = useHomeContext();
    const [langs, setLangs] = useState<LanguageData[] | null>(null);
    const [input, setInput] = useState<string | null>(null);
    const navigate = useNavigate();

    async function fetchLanguages() {
        let allLangsResp = fetch('/languages.json').then(resp => resp.json());
        let addedLanguagesResp = fetch('/api/v1/languages').then (resp => resp.json());

        let allLangs: LanguageData[] = await allLangsResp;
        let addedLanguages: BackendLanguageData[] = await addedLanguagesResp;
        console.log(addedLanguages);

        let addedLanguageCodes = addedLanguages.map((l) => l.langCode);
        setLangs(allLangs.filter(l => !addedLanguageCodes.includes(l.code)));
    }

    useEffect(() => {
        updateHomeText('Dodavanje jezika', 'Dodajte novi jezik u aplikaciju.');
        fetchLanguages();
    }, []);

    async function onLangClick(langCode: string) {
        if (langs === null) {
            return;
        }

        let lang = langs.filter(l => l.code === langCode)[0];
        let response = await fetch('/api/v1/languages', {
            method: "POST",
            body: JSON.stringify({
                langCode: lang.code,
                languageName: lang.name,
                languageImage: lang.flag
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })

        if (response.ok) {
            navigate('/home');
        } else {
            console.error("Error while trying to add language: ", response);
        }
    }

    return (
        <div>
            <div>
                <InputText name="lang" placeholder="Ime jezika" onChange={setInput} />
            </div>
            <LanguageList languages={langs?.filter(lang => lang.name.includes(input ?? '')) ?? null} onLangClick={onLangClick} />
        </div>
    );
}

