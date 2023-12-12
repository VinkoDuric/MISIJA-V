import { createContext, useContext, ReactNode, FC, useState } from "react";

export enum Role {
    USER,
    ADMIN,
    UNVERIFIED_USER,
    NONE,
}

export type UserInfo = {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    role: keyof typeof Role,
    tokenVersion: number
};

interface UserContextProps {
    userInfo: UserInfo|null 
    updateUserInfo: (userInfo: UserInfo|null) => void;
}

const UserContext = createContext<UserContextProps | undefined>(undefined);

const UserContextProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [userInfo, setUserInfo] = useState<UserInfo|null>(null);

    const updateUserInfo = (newUserInfo: UserInfo|null) => {
        setUserInfo(newUserInfo);
    };

    return (
        <UserContext.Provider value= {{ userInfo, updateUserInfo }}>
        { children }
        </UserContext.Provider>
    );
};

export const useUserContext = (): UserContextProps => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error('useRoleContext must be used within a UserContextProvider');
    }
    return context;
};

export { UserContextProvider };