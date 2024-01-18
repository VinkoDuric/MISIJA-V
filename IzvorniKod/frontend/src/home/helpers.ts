import { ItemsListElement } from "./components/itemsList";

export function convertArrayToListItems(list: string[]): ItemsListElement[] {
    let arr = [];
    for (let i = 0; i < list.length; i++) {
        arr.push({clickArg: i, text: list[i]})
    }
    return arr;
}

export function backendLangDataToLangData(backendLangData: BackendLanguageData[]): LanguageData[] {
    return backendLangData.map(lang => ({code: lang.langCode, flag: lang.languageImage, name: lang.languageName}));
}


