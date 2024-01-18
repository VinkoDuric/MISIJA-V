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

    let newDict = (dict === undefined)

    async function loadExistingDict() {
        updateHomeText('Uređivanje rječnika', 'Uredite postojeći rječnik.');

        let dicts: DictionaryMeta[] = await fetch('/api/v1/languages/' + lang).then(res => res.json());
        if (inputRef.current && dict !== undefined) {
            inputRef.current.value = dicts.find(d => d.id === parseInt(dict ?? '-1'))?.name ?? '';
        }

        let words = await fetch('/api/v1/dictionaries/' + dict).then(res => res.json());
        console.log(words)
        setWords(words);
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
        // TODO: open word editing
        console.log("Handle item click for argument: " + arg)
    }

    function handleItemIconClick(arg: number) {
        // TODO: remove word
        console.log("Handle Icon click for argument: " + arg);
    }

    function handleSubmit(arg: number | string) {
        if (typeof arg === 'number') {
            throw new Error('Clicked on autocomplete.');
        }

        if (newDict) { // create new dict
            fetch('/api/v1/dictionaries', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({languageCode: lang, dictionaryName: arg})
            }).then(res => {
                if (res.ok) {
                    navigate('/home/' + lang)
                }
            });
        } else { // update existing dict
            throw new Error("unimplemented");
        }
    }

    return (
        <div>
            <Autocomplete inputRef={inputRef} placeholder="Ime rječnika" btnText="spremi" handleSubmit={handleSubmit} />
            <ItemsList items={wordMetaToItemsListElements(words)} icon={faTrash} handleIconClick={handleItemIconClick} handleClick={handleItemClick} />
        </div>
    );
}

