import React, { createContext, useContext, ReactNode, FC, useState } from "react";

export enum Role {
    USER,
    ADMIN,
    NONE
}

interface RoleContextProps {
    role: Role
    updateRole: (role: Role) => void;
}

const RoleContext = createContext<RoleContextProps | undefined>(undefined);

const RoleContextProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [role, setRole] = useState<Role>(Role.NONE);

    const updateRole = (newRole: Role) => {
        setRole(newRole);
    };

    return (
        <RoleContext.Provider value= {{ role, updateRole }}>
        { children }
        </RoleContext.Provider>
    );
};

export const useRoleContext = (): RoleContextProps => {
    const context = useContext(RoleContext);
    if (!context) {
        throw new Error('useRoleContext must be used within a RoleContextProvider');
    }
    return context;
};

export { RoleContextProvider };