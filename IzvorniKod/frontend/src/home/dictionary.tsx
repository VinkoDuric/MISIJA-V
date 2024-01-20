import { useEffect, useRef, useState } from "react";
import { useHomeContext } from "./homeContext";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { ItemsList, ItemsListElement } from "./components/itemsList";
import { useNavigate, useParams } from "react-router-dom";
import { Autocomplete } from "./components/autocomplete";

function wordMetaToItemsListElements(words: WordMeta[]): ItemsListElement[] {
    return words.map(word => ({ clickArg: word.id, text: word.text }));
}

export function Dictionary() {
    const navigate = useNavigate();
    let { lang, dict } = useParams();
    const inputRef = useRef<HTMLInputElement>(null);
    const { updateHomeText } = useHomeContext();
    let [words, setWords] = useState<WordMeta[]>([]);
    let [dictId, setDictId] = useState<number | null>(null);

    let newDict = (dict === undefined)

    async function loadExistingDict() {
        updateHomeText('Uređivanje rječnika', 'Uredite postojeći rječnik.');

        let dicts: DictionaryMeta[] = await fetch('/api/v1/languages/' + lang).then(res => res.json());
        if (inputRef.current && dict !== undefined) {
            let currentDict = dicts.find(d => d.id === parseInt(dict ?? '-1'));
            inputRef.current.value = currentDict?.name ?? '';
            setDictId(currentDict?.id ?? -1);
        }

        let words: {wordId: number; originalWord: string;}[] = await fetch('/api/v1/dictionaries/' + dict).then(res => res.json());
        setWords(words.map(word => ({ id: word.wordId?? -1, text: word.originalWord })));
    }

    useEffect(() => {
        if (newDict) {
            updateHomeText('Dodavanje rječnika', 'Dodajte novi rječnik u aplikaciju.');
            return;
        } else {
            loadExistingDict();
        }
    }, []);

    function handleItemClick(arg: number) {
        navigate('/word/' + arg);
    }

    function handleItemIconClick(arg: number) {
        fetch(`/api/v1/dictionaries/${dict}/${arg}`, {
            method: 'DELETE'
        }).then(res => {
            if (res.ok) {
                setWords(words.filter(word => word.id != arg));
            }
        })
    }

    function handleSubmit(arg: number | string) {
        if (typeof arg === 'number') {
            throw new Error('Clicked on autocomplete.');
        }

        if (newDict) { // create new dict
            fetch('/api/v1/dictionaries', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ langCode: lang, dictName: arg })
            }).then(res => {
                if (res.ok) {
                    navigate('/home/' + lang)
                }
            });
        } else {
            // Update existing dict
            fetch('/api/v1/dictionaries/' + dictId, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ dictLang: lang, dictName: arg, id: dictId ?? undefined, dictImage: 'empty' })
            }).then(res => {
                if (res.ok) {
                    navigate('/home/' + lang)
                }
            });
        }
    }

    return (
        <div>
            <Autocomplete inputRef={inputRef} placeholder="Ime rječnika" btnText="spremi" handleSubmit={handleSubmit} />
            <ItemsList items={wordMetaToItemsListElements(words)} icon={faTrash} handleIconClick={handleItemIconClick} handleClick={handleItemClick} />
        </div>
    );
}

