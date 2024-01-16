import { useRef, useState } from 'react';
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
    let inputRef = useRef<HTMLInputElement>(null);

    let [inputText, setInputText] = useState<string>('');

    function handleItemClick(arg: number) {
        if (options) {
            handleSubmit(options[arg])
        }
    }

    function onBtnClick() {
        handleSubmit(inputText);
    }

    return (
        <div>
            {/* TODO: show options where arg is id of item */}
            <ItemsList items={[{ clickArg: 0, text: 'abc' }]} handleClick={handleItemClick} />
            <div className={styles.autocomplete}>
                <InputText onChange={setInputText} name='dictionaryName' className={styles.input} placeholder={placeholder} />
                {
                    (btnText !== undefined) &&
                    <Button type={ButtonType.ACCENT} onClick={onBtnClick} className={styles.btn}>{btnText}</Button>
                }
            </div>
        </div>
    );
}
