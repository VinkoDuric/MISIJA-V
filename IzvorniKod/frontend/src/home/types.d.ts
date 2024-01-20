type LanguageData = {
    code: string;
    name: string;
    flag: string;
};

type BackendLanguageData = {
    langCode: string;
    languageName: string;
    languageImage: string;
}

type WordMeta = {
    id: number;
    text: string;
}

type DictionaryMeta = {
    id: number;
    name: string;
}

type BackendWord = {
    id?: number;
    wordLanguageCode: string;
    originalWord: string;
    translatedWord: string;
    wordDescription: string[];
    wordSynonyms: string[];
    dictionaryIds: number[];
};
