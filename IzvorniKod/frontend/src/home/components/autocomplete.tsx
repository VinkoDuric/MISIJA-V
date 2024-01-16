import { Button, ButtonType } from '../../components/buttons';
import { InputText } from '../../components/form';
import { ItemsList } from './itemsList';
import styles from './styles/autocomplete.module.css'

type AutocompleteProps = {
    placeholder: string;
    btnText?: string;
    options?: Array<string>;
    handleSubmit: (text: string) => void;
}

export function Autocomplete({ placeholder, btnText, options, handleSubmit }: AutocompleteProps) {

    function handleItemClick(arg: number) {

    }

    return (
        <div>
            <ItemsList items={[{ clickArg: 0, text: 'abc' }]} handleClick={handleItemClick} />
            <div className={styles.autocomplete}>
                <InputText name='dictionaryName' className={styles.input} placeholder={placeholder} />
                {
                    (btnText !== undefined) &&
                    <Button type={ButtonType.ACCENT} className={styles.btn}>{btnText}</Button>
                }
            </div>
        </div>
    );
}
