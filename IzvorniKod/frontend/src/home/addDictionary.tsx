import { useEffect } from "react";
import { useHomeContext } from "./homeContext";

export function AddDictionary() {
    const { updateHomeText } = useHomeContext();

    useEffect(() => {
        updateHomeText('Dodavanje rječnika', 'Dodajte novi rječnik u aplikaciju.');
    }, []);

    return (
        <>

        </>
    );
}
