import { RefObject, useState } from 'react';
import { Button, ButtonType } from '../../components/buttons';
import { InputText } from '../../components/form';
import { ItemsList, ItemsListElement } from './itemsList';
import styles from './styles/autocomplete.module.css'

type AutocompleteProps = {
    placeholder: string;
    inputRef?: RefObject<HTMLInputElement>;
    btnText?: string;
    options?: ItemsListElement[];
    handleSubmit: (clickArg: number|string) => void;
    handleInputChange?: (text: string) => void;
    className?: string;
}

export function Autocomplete({ placeholder, btnText, options, handleSubmit, handleInputChange, className, inputRef }: AutocompleteProps) {

    let [inputText, setInputText] = useState<string>('');

    function handleItemClick(arg: number) {
        handleSubmit(arg);
    }

    function onBtnClick() {
        handleSubmit(inputText);
    }

    function inputChange(text: string) {
        setInputText(text);
        if (handleInputChange) {
            handleInputChange(text);
        }
    }

    return (
        <div>
            <div className={styles.autocomplete + ' ' + className}>
                <InputText inputRef={inputRef} onChange={inputChange} name="dictionaryName" className={styles.input} placeholder={placeholder} />
                {
                    (btnText !== undefined) &&
                    <Button type={ButtonType.ACCENT} onClick={onBtnClick} className={styles.btn}>{btnText}</Button>
                }
            </div>
            {
                options !== undefined && options.length > 0 &&
                <ItemsList className={styles.itemsList} items={options} handleClick={handleItemClick} />
            }
        </div>
    );
}
