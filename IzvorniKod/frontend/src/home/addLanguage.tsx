import { FormEvent, useEffect } from "react";
import { useHomeContext } from "./homeContext";
import homeStyles from './styles/home.module.css';
import styles from './styles/addLanguage.module.css';
import { InputText } from "../components/form";

export function AddDictionary() {
    const { updateHomeText } = useHomeContext();

    useEffect(() => {
        updateHomeText('Dodavanje rječnika', 'Dodajte novi rječnik u aplikaciju.');
    }, []);

    async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {

    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <InputText name='originalWord' placeholder='Originalna rijec' />
                <InputText name='originalWord' placeholder='Primjer korištenja u izvornom jeziku'/>
                <InputText name='originalWord' placeholder='Prevedena rijec'/>
                <InputText name='originalWord' placeholder='Primjer korištenja prevedene riječi'/>
            </form>
        </div>
    );
}

