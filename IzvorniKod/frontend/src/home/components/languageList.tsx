import styles from './styles/language.module.css';

type LangClickCallback = (langCode: string) => void;

type LanguageProps = {
    code: string;
    image: string;
    name: string;
    onClick: LangClickCallback;
};

export function Language({ code, image, name, onClick }: LanguageProps) {
    return (
        <div className={styles.language} onClick={() => onClick(code)}>
            <img src={image} alt={name} width='50px' />
            <div className={styles.langName}>{name}</div>
        </div>
    );
}

type LanguageListProps = {
    languages: LanguageData[]|null;
    onLangClick: LangClickCallback;
}

export function LanguageList({languages, onLangClick}: LanguageListProps) {
    return (
        <div className={styles.languagesWrapper}>
            {
                languages !== null && languages.map(lang =>
                    <Language onClick={onLangClick} key={lang.code} code={lang.code} image={`/flags/${lang.flag.toLowerCase()}.png`} name={lang.name} />
                )
            }
        </div>
    );
}
