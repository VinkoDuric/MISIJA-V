import styles from './styles/addLanguage.module.css'
import { FormEvent, useEffect, useState } from "react";
import { useHomeContext } from "./homeContext";
import { InputText } from "../components/form";
import { useNavigate } from 'react-router-dom';

type LanguageProps = {
    code: string;
    image: string;
    name: string;
    onClick: (langCode: string) => void;
};

function Language({ code, image, name, onClick }: LanguageProps) {

    return (
        <div className={styles.language} onClick={() => onClick(code)}>
            <img src={image} alt={name} width='50px' />
            <div className={styles.langName}>{name}</div>
        </div>
    );
}

type LangsRecord = Record<string, {
    name: string;
    flag: string;
}>;

export function AddLanguage() {
    const { updateHomeText } = useHomeContext();
    const [langs, setLangs] = useState<LangsRecord | null>(null);
    const [input, setInput] = useState<string|null>(null);
    const navigate = useNavigate();

    async function fetchLanguages() {
        fetch('/languages.json').then(res => res.json()).then(langs => setLangs(langs));
    }

    useEffect(() => {
        updateHomeText('Dodavanje jezika', 'Dodajte novi jezik u aplikaciju.');
        fetchLanguages();
    }, []);

    function onLangClick(langCode: string) {
        console.log('Language picked: ' + langCode);
        navigate('/home');
    }

    return (
        <div>
            <div>
                <InputText name='lang' placeholder='Ime jezika' onChange={setInput} />
            </div>
            <div className={styles.languagesWrapper}>
                {
                    langs !== null && Object.entries(langs).filter(([langCode, lang]) => lang.name.includes(input ?? '')).map(([langCode, lang]) =>
                        <Language onClick={onLangClick} key={langCode} code={langCode} image={`/flags/${lang.flag.toLowerCase()}.png`} name={lang.name} />
                    )
                }
            </div>
        </div>
    );
}

