import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { Button, ButtonType } from "../components/buttons";
import { InputText } from "../components/form";
import { Autocomplete } from "./components/autocomplete";
import { ItemsList, ItemsListElement } from "./components/itemsList";
import { LanguageList } from "./components/languageList";
import { convertArrayToListItems } from "./helpers";
import { useHomeContext } from "./homeContext";
import styles from './styles/word.module.css';

export function Word() {
    const { wordId } = useParams();
    const {updateHomeText} = useHomeContext();

    const [originalWord, setOriginalWord] = useState<string>('');
    const [translatedWord, setTranslatedWord] = useState<string>('');
    const [lang, setLang] = useState<LanguageData|null>(null);
    const [allLangs, setAllLangs] = useState<LanguageData[]|null>(null);
    const [searchLangInput, setSearchLangInput] = useState<string>('');
    const synonimeInputRef = useRef<HTMLInputElement>(null);
    const [synonimes, setSynonimes] = useState<string[]>([]);
    const [synonimeOptions, setSynonimeOptions] = useState<string[]>(['syn1']);
    const usagesInputRef = useRef<HTMLInputElement>(null);
    const [usages, setUsages] = useState<string[]>([]);
    const [usageOptions, setUsageOptions] = useState<string[]>([]);
    const [dicts, setDicts] = useState<ItemsListElement[]>([]);
    const [dictOptions, setDictOptions] = useState<ItemsListElement[]>([]);

    function setupAddWord() {
        updateHomeText('Dodavanje nove riječi', 'Dodajte novu riječ u aplikaciju.');
    }

    function setupEditWord(wordId: number) {
        updateHomeText('Uređivanje riječi', 'Uredite postojeću riječ u aplikaciji.');
        // TODO : fetch word data and populate it
    }

    useEffect(() => {
        if (wordId === undefined) {
            setupAddWord();
        } else {
            setupEditWord(parseInt(wordId));
        }

        fetch('/languages.json').then(res => res.json()).then((langs: LanguageData[]) => (console.log(langs), setAllLangs(langs)));
    }, []);

    function onLangClick(langCode: string) {
        if (lang === null) {
            setLang(allLangs?.find(lang => lang.code === langCode) ?? null);
            setSearchLangInput('');
            // TODO: fetch dicts
        } else {
            setLang(null);
            // TODO: clear dicts
        }
    }

    function setSynonimeSuggestions() {
        // Only if lang is english use api
    }

    function setUsageSuggestions() {
        // Only if lang is english use api
    }

    function addSynonim(submitArg: string|number) {
        if (typeof submitArg === 'string' && submitArg !== '') {
            setSynonimes([submitArg, ...synonimes]);
        }
        else if (typeof submitArg === 'number'){
            setSynonimes([synonimeOptions[submitArg], ...synonimes])
        }
    }

    function handleSynonimInputChange(input: string) {
        // TODO: filter suggestions by input
    }

    function handleAddedSynonimClick(clickArg: number) {
        let synonimeText = synonimes.splice(clickArg, 1);
        if (synonimeInputRef.current) {
            synonimeInputRef.current.focus();
            synonimeInputRef.current.value = synonimeText[0];
        }
        setSynonimes([...synonimes]);
        setSynonimeSuggestions();
    }

    function handleSynonimRemove(clickArg: number) {
        synonimes.splice(clickArg, 1);
        setSynonimes([...synonimes]);
        setSynonimeSuggestions();
    }

    function addUsage(submitArg: string|number) {
        if (typeof submitArg === 'string' && submitArg !== '') {
            setUsages([submitArg, ...usages]);
        }
        else if (typeof submitArg === 'number'){
            setUsages([usageOptions[submitArg], ...usages])
        }
    }

    function handleUsageInputChange(input: string) {
        // TODO: fetch suggestions from api
    }


    function handleAddedUsageClick(clickArg: number) {
        let usageText = usages.splice(clickArg, 1);
        if (usagesInputRef.current) {
            usagesInputRef.current.focus();
            usagesInputRef.current.value = usageText[0];
        }
        setUsages([...synonimes]);
        setUsageSuggestions();
    }

    function handleUsageRemove(clickArg: number) {
        usages.splice(clickArg, 1);
        setUsages([...usages]);
        setUsageSuggestions();
    }

    function addDictionary(clickArg: number) {
        // TODO: Add dictionary to dicts
    }

    function handleDictionaryInputChange(input: string) {
        // TODO: filer dicionaries by name, chgange suggestions
    }

    function handleAddedDictionaryClick(clickArg: number|string) {
        // TODO: remove dict from list of dicts
    }

    return (
        <div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Jezik</h2>
                <div className={styles.sectionText}>Odaberite jezik izvorne riječi.</div>
                {
                    lang !== null &&
                    <LanguageList languages={[{code: "hr", name: "hrvatski", flag: "hr"}]} onLangClick={onLangClick} />

                }
                {
                    lang === null &&
                    <>
                        <InputText name="jezik" onChange={setSearchLangInput} placeholder="Ime jezika"/>
                        {
                            searchLangInput !== '' &&
                            <LanguageList
                                languages={allLangs?.filter(lang => lang.name.includes(searchLangInput)).splice(0, 3) ?? null}
                                onLangClick={onLangClick} />
                        }
                    </>
                }
            </div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Riječi</h2>
                <div className={styles.sectionText}>Upišite stranu riječ i njezin prijevod.</div>
                <div className={styles.wordWrapper}>
                    <InputText onChange={setOriginalWord} name="originalWord" placeholder="Izvorna riječ" />
                    <InputText onChange={setTranslatedWord} name="translatedWord" placeholder="Prevedena riječ" />
                </div>
            </div>
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Sinonimi</h2>
                <div className={styles.sectionText}>Dodajte sinonime za stranu riječ na izvornom jeziku.</div>
                <Autocomplete
                    placeholder="Sinonim riječi"
                    btnText="dodaj"
                    options={convertArrayToListItems(synonimeOptions)}
                    inputRef={synonimeInputRef}
                    handleSubmit={addSynonim}
                    handleInputChange={handleSynonimInputChange}/>
                <div>Odabrani sinonimi</div>
                <ItemsList
                    className={styles.marginTopSmall}
                    items={convertArrayToListItems(synonimes)}
                    handleClick={handleAddedSynonimClick}
                    icon={faTrash}
                    handleIconClick={handleSynonimRemove} />
            </div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Primjeri korištenja</h2>
                <div className={styles.sectionText}>Dodajte primjere korištenja riječi u izvornom jeziku.</div>
                <Autocomplete
                    placeholder="Primjer korištenja"
                    btnText="dodaj"
                    inputRef={usagesInputRef}
                    options={convertArrayToListItems(usageOptions)}
                    handleSubmit={addUsage}
                    handleInputChange={handleUsageInputChange}/>
                <div>Odabrani primjeri korištenja</div>
                <ItemsList
                    className={styles.marginTopSmall}
                    items={convertArrayToListItems(usages)}
                    handleClick={handleAddedUsageClick}
                    icon={faTrash}
                    handleIconClick={handleUsageRemove} />
            </div>
            <div className={styles.section + " " + styles.marginTop }>
                <h2 className={styles.sectionTitle}>Rječnici</h2>
                <div className={styles.sectionText}>Dodajte riječ u neki od postojećih rječnika.</div>
                <Autocomplete
                    placeholder="Ime rječnika"
                    options={[]}
                    handleSubmit={addSynonim}
                    handleInputChange={handleDictionaryInputChange}/>
                <div>Odabrani rječnici</div>
                <ItemsList
                    className={styles.marginList}
                    items={dicts}
                    handleClick={handleAddedDictionaryClick} />
            </div>
            <Button type={ButtonType.ACCENT} className={styles.saveBtn}>spremi</Button>
        </div>
    );
}
