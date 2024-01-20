import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, ButtonType } from "../components/buttons";
import { InputText } from "../components/form";
import { Autocomplete } from "./components/autocomplete";
import { ItemsList, ItemsListElement } from "./components/itemsList";
import { LanguageList } from "./components/languageList";
import { convertArrayToListItems } from "./helpers";
import { useHomeContext } from "./homeContext";
import { backendLangDataToLangData } from "./helpers";
import styles from './styles/word.module.css';

function dictMetaToItemsListElements(dict: DictionaryMeta[]): ItemsListElement[] {
    return dict.map(dict => ({ clickArg: dict.id, text: dict.name }));
}

export function Word() {
    const navigate = useNavigate();
    const { wordId } = useParams();
    const { updateHomeText } = useHomeContext();

    const [originalWord, setOriginalWord] = useState<string>('');
    const [translatedWord, setTranslatedWord] = useState<string>('');
    const [lang, setLang] = useState<LanguageData | null>(null);
    const [allLangs, setAllLangs] = useState<LanguageData[] | null>(null);
    const [searchLangInput, setSearchLangInput] = useState<string>('');
    const synonimeInputRef = useRef<HTMLInputElement>(null);
    const [synonimes, setSynonimes] = useState<string[]>([]);
    const [synonimeOptions, setSynonimeOptions] = useState<string[]>([]);
    const usagesInputRef = useRef<HTMLInputElement>(null);
    const [usages, setUsages] = useState<string[]>([]);
    const [usageOptions, setUsageOptions] = useState<string[]>([]);
    const [dicts, setDicts] = useState<ItemsListElement[]>([]);
    const [dictOptions, setDictOptions] = useState<ItemsListElement[] | null>(null);
    const [dictInput, setDictInput] = useState<string>('');
    const [error, setError] = useState<string | null>(null);

    async function setupGeneral() {
        let langs: BackendLanguageData[] = await fetch('/api/v1/languages').then(res => res.json()) || [];
        setAllLangs(backendLangDataToLangData(langs));
    }

    function setupAddWord() {
        updateHomeText('Dodavanje nove riječi', 'Dodajte novu riječ u aplikaciju.');
    }

    async function setupEditWord(wordId: number) {
        updateHomeText('Uređivanje riječi', 'Uredite postojeću riječ u aplikaciji.');

        let word: BackendWord = await fetch('/api/v1/word/' + wordId).then(res => res.json());

        let allLangs: LanguageData[] = backendLangDataToLangData(await fetch('/api/v1/languages').then(res => res.json()));
        let backendLang = allLangs.find(lang => lang.code === word.wordLanguageCode);
        setLang(backendLang ?? null);

        setOriginalWord(word.originalWord);
        setTranslatedWord(word.translatedWord);
        setUsages(word.wordDescription);
        //setSynonimes(word.wordSynonyms);
        let newDicts = dictMetaToItemsListElements(await fetch('/api/v1/languages/' + word.wordLanguageCode)
            .then(res => res.json()))
            .filter(dict => word.dictionaryIds.includes(dict.clickArg))

        setDicts(newDicts);
    }

    useEffect(() => {
        if (wordId === undefined) {
            setupAddWord();
        } else {
            setupEditWord(parseInt(wordId));
        }
        setupGeneral()
    }, []);

    // Dictionary options
    useEffect(() => {
        if (lang !== null) {
            refreshDictionaryOptions();
        }
        return () => setDictOptions(null);
    }, [lang])

    function submit() {
        if (lang === null) {
            throw new Error("Language not selected");
        }

        if (originalWord.length === 0) {
            setError('Upišite izvornu riječ.');
            return;
        }

        if (translatedWord.length === 0) {
            setError('Upišite prevedenu riječ.');
            return;
        }

        if (dicts.length === 0) {
            setError('Potrebno je odabrati barem jedan rječnik.');
            return;
        }

        let id = parseInt(wordId ?? '-1')

        let wordData: BackendWord = {
            id: wordId ? id : undefined,
            wordLanguageCode: lang.code,
            originalWord: originalWord,
            translatedWord: translatedWord,
            wordDescription: usages,
            wordSynonyms: synonimes,
            dictionaryIds: dicts.map(dict => dict.clickArg),
        }

        console.log(wordData);

        fetch('/api/v1/word', {
            method: wordId ? 'PUT' : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(wordData)
        }).then(res => {
            if (res.ok) {
                navigate('/home/' + lang.code);
            } else {
                console.error(res);
            }
        })
    }

    function onLangClick(langCode: string) {
        if (lang === null) {
            setLang(allLangs?.find(lang => lang.code === langCode) ?? null);
            setSearchLangInput('');
        } else {
            setLang(null);
        }
    }

    function setSynonimeSuggestions() {
        // Only if lang is english use api
    }

    function setUsageSuggestions() {
        // Only if lang is english use api
    }

    function addSynonim(submitArg: string | number) {
        if (typeof submitArg === 'string' && submitArg !== '') {
            setSynonimes([submitArg, ...synonimes]);
        }
        else if (typeof submitArg === 'number') {
            setSynonimes([synonimeOptions[submitArg], ...synonimes])
        }
        if (synonimeInputRef.current) {
            synonimeInputRef.current.value = '';
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

    function addUsage(submitArg: string | number) {
        if (typeof submitArg === 'string' && submitArg !== '') {
            setUsages([submitArg, ...usages]);
        }
        else if (typeof submitArg === 'number') {
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
        setUsages([...usages]);
        setUsageSuggestions();
    }

    function handleUsageRemove(clickArg: number) {
        usages.splice(clickArg, 1);
        setUsages([...usages]);
        setUsageSuggestions();
    }

    async function refreshDictionaryOptions() {
        if (lang === null) return;
        console.log(await fetch('/api/v1/languages/' + lang.code).then(res => res.json()))
        setDictOptions(
            dictMetaToItemsListElements(await fetch('/api/v1/languages/' + lang.code).then(res => res.json())));
    }

    function addDictionary(clickArg: number | string) {
        if (typeof clickArg === 'string')
            throw new Error('Autocomplete dict misusage');

        if (dictOptions === null) return;

        let pickedDictIdx = dictOptions?.findIndex(dict => dict.clickArg === clickArg)
        console.log(dictOptions);
        console.log(pickedDictIdx);
        if (pickedDictIdx !== -1) {
            setDicts([dictOptions[pickedDictIdx], ...dicts]);
            dictOptions.splice(pickedDictIdx, 1);
            setDictOptions([...dictOptions]);
        }
    }

    function handleAddedDictionaryClick(clickArg: number) {

        if (typeof clickArg === 'string')
            throw new Error('Autocomplete dict misusage');

        if (dicts === null) return;

        let pickedDictIdx = dicts?.findIndex(dict => dict.clickArg === clickArg)
        if (pickedDictIdx !== -1) {
            setDictOptions([dicts[pickedDictIdx], ...(dictOptions ?? [])]);
            dicts.splice(pickedDictIdx, 1)
            setDicts([...dicts]);
        }
    }

    let dictIds = dicts.map(dict => dict.clickArg);
    let activeOptions = dictOptions?.filter(option => !dictIds.includes(option.clickArg)) ?? [];

    return (
        <div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Jezik</h2>
                <div className={styles.sectionText}>Odaberite jezik izvorne riječi.</div>
                {
                    lang !== null &&
                    <LanguageList languages={[lang]} onLangClick={onLangClick} />

                }
                {
                    lang === null &&
                    <>
                        <InputText name="jezik" onChange={setSearchLangInput} placeholder="Ime jezika" />
                        <LanguageList
                            languages={allLangs?.filter(lang => lang.name.includes(searchLangInput)).splice(0, 3) ?? null}
                            onLangClick={onLangClick} />
                    </>
                }
            </div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Riječi</h2>
                <div className={styles.sectionText}>Upišite stranu riječ i njezin prijevod.</div>
                <div className={styles.wordWrapper}>
                    <InputText value={originalWord ?? ''} onChange={setOriginalWord} name="originalWord" placeholder="Izvorna riječ" />
                    <InputText value={translatedWord ?? ''} onChange={setTranslatedWord} name="translatedWord" placeholder="Prevedena riječ" />
                </div>
            </div>
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Sinonimi</h2>
                <div className={styles.sectionText}>Dodajte sinonime za stranu riječ na izvornom jeziku.</div>
                <Autocomplete
                    placeholder="Sinonim riječi"
                    btnText="dodaj"
                    options={convertArrayToListItems(synonimeOptions.filter(s => !synonimes.includes(s)))}
                    inputRef={synonimeInputRef}
                    handleSubmit={addSynonim}
                    handleInputChange={handleSynonimInputChange} />
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
                    handleInputChange={handleUsageInputChange} />
                <div>Odabrani primjeri korištenja</div>
                <ItemsList
                    className={styles.marginTopSmall}
                    items={convertArrayToListItems(usages)}
                    handleClick={handleAddedUsageClick}
                    icon={faTrash}
                    handleIconClick={handleUsageRemove} />
            </div>
            <div className={styles.section + " " + styles.marginTop}>
                <h2 className={styles.sectionTitle}>Rječnici</h2>
                <div className={styles.sectionText}>Dodajte riječ u neki od postojećih rječnika.</div>
                <Autocomplete
                    placeholder="Ime rječnika"
                    options={activeOptions?.filter(option => option.text.includes(dictInput)) ?? []}
                    handleSubmit={addDictionary}
                    handleInputChange={setDictInput} />
                <div>Odabrani rječnici</div>
                <ItemsList
                    className={styles.marginTopSmall}
                    items={dicts}
                    handleClick={handleAddedDictionaryClick} />
            </div>
            {
                error !== null &&
                <div className={styles.error}>{error}</div>
            }
            <Button type={ButtonType.ACCENT} className={styles.saveBtn} onClick={submit}>spremi</Button>
        </div>
    );
}

