import { FormEvent, useEffect } from "react";
import { useHomeContext } from "./homeContext";
import { InputText } from "../components/form";
import { Button, ButtonType } from "../components/buttons";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles/dictionary.module.css"
import { ItemsList } from "./components/itemsList";
import { useParams } from "react-router-dom";
import { Autocomplete } from "./components/autocomplete";

export function Dictionary() {
    let { dict } = useParams();
    const { updateHomeText } = useHomeContext();

    useEffect(() => {
        if (dict === undefined) {
            updateHomeText('Dodavanje rječnika', 'Dodajte novi rječnik u aplikaciju.');
            return;
        }
        updateHomeText('Uređivanje rječnika', 'Uredite postojeći rječnik.');
    }, []);

    function handleItemClick(arg: number) {
        // TODO: open word editing
        console.log("Handle item click for argument: " + arg)
    }

    function handleItemIconClick(arg: number) {
        // TODO: remove word
        console.log("Handle Icon click for argument: " + arg);
    }

    const words = [
        { clickArg: 1, text: 'rijec1' },
        { clickArg: 2, text: 'rijec2' },
        { clickArg: 3, text: 'rijec3' },
    ]

    function handleSubmit(text: string) {
        // TODO: rename dict
        console.log('submited: ' + text);
    }

    return (
        <div>
            <Autocomplete placeholder='Ime rječnika' btnText='spremi' handleSubmit={handleSubmit}/>
            <ItemsList items={words} icon={faTrash} handleIconClick={handleItemIconClick} handleClick={handleItemClick} />
        </div>
    );
}

