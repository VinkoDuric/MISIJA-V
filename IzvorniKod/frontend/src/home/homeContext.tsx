import { createContext, FC, ReactNode, useContext, useState } from "react";

type HomeContextProps = {
    text: {
        title: string;
        caption: string|null;
    }
    updateHomeText: (title: string, caption: string) => void;
};

const HomeContext = createContext<HomeContextProps|undefined>(undefined);

export const HomeContextProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [title, setTitle] = useState<string>('');
    const [caption, setCaption] = useState<string|null>(null);

    const updateHomeText = (title: string, caption: string) => {
        console.log('Called updateHomeContext: ' + title + ' ' + caption);
        setTitle(title);
        setCaption(caption);
    };

    return (
        <HomeContext.Provider value={{ text: { title, caption }, updateHomeText }}>
            {children}
        </HomeContext.Provider>
    );
};

export const useHomeContext = (): HomeContextProps => {
    const context = useContext(HomeContext);
    if (!context) {
        throw new Error('useHomeContext must be used within a home page');
    }
    return context;
};

