import { FormEvent, useEffect } from "react";
import { useHomeContext } from "./homeContext";
import { InputText } from "../components/form";
import { Button, ButtonType } from "../components/buttons";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles/dictionary.module.css"
import { ItemsList } from "./components/listItems";
import { useParams } from "react-router-dom";

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

    async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        // TODO: rename dict
    }

    function handleItemClick(arg: number) {
        console.log("Handle item click for argument: " + arg)
    }

    function handleItemIconClick(arg: number) {
        console.log("Handle Icon click for argument: " + arg);
    }

    const words = [
        { clickArg: 1, text: 'rijec1' },
        { clickArg: 2, text: 'rijec2' },
        { clickArg: 3, text: 'rijec3' },
    ]

    return (
        <div>
            <form onSubmit={handleSubmit} className={styles.nameForm}>
                <InputText name='dictionaryName' className={styles.nameText} placeholder='Ime rječnika' />
                <Button type={ButtonType.ACCENT} className={styles.saveBtn}>spremi</Button>
            </form>
            <ItemsList items={words} icon={faTrash} handleIconClick={handleItemIconClick} handleClick={handleItemClick} />
        </div>
    );
}

