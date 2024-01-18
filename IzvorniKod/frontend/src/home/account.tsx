import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { InputPassword, InputText } from "../components/form";
import { UserInfo, useUserContext } from "../userContext";
import { FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";

type AccountProps = {
    closeAccount: () => void;
}

export function Account({ closeAccount }: AccountProps){
    const { userInfo, updateUserInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
    let navigate = useNavigate();
    let [error, setError] = useState<boolean>(false);

    useEffect(() => {
        updateHomeText('Upravljanje računom', '');
    }, []);

    async function handleFirstnameSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        const formData = new FormData(event.target as HTMLFormElement);

        formData.forEach((value, property) => console.log(value, property));
        let inputData = Object.fromEntries(Array.from(formData));

        const newUser: UserInfo = {
            lastName: userInfo?.lastName || '',
            firstName: inputData.firstname?.toString() || '',
            id: userInfo?.id || 0,
            email: userInfo?.email  || '',
            role: userInfo?.role || "NONE",
            tokenVersion: userInfo?.tokenVersion || 0
        };

        updateUserInfo(newUser);
    }

    async function handleLastnameSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        const formData = new FormData(event.target as HTMLFormElement);

        formData.forEach((value, property) => console.log(value, property));
        let inputData = Object.fromEntries(Array.from(formData));

        const newUser: UserInfo = {
            firstName: userInfo?.firstName || '',
            lastName: inputData.lastname?.toString() || '',
            id: userInfo?.id || 0,
            email: userInfo?.email  || '',
            role: userInfo?.role || "NONE",
            tokenVersion: userInfo?.tokenVersion || 0
        };
        
        updateUserInfo(newUser);
    }

    async function handleSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        const formData = new FormData(event.target as HTMLFormElement);

        formData.forEach((value, property) => console.log(value, property));
        let inputData = Object.fromEntries(Array.from(formData));

        if (inputData.password1 != inputData.password2) {
            setError(true);
            return;
        }

        let data = {
            email: userInfo?.email,
            firstName: userInfo?.firstName,
            lastName: userInfo?.lastName,
            password: inputData.password1
        }

        fetch('/api/v1/account', {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: new Headers({ 'Content-Type': 'application/json' })
        }).then(response => {
            console.log(response);
            if (response.ok) {
                return response.json()
            }
        }).then(json => {
            console.log(json);
            updateUserInfo(json);
            navigate('/home');
        });
    }

    return (
        <div className={styles.quizWrapper}>    
            <div className={styles.accountWrapper}>
                <p className={styles.audioText}>Ime:</p> <p></p>
                <form className={styles.inputWrapper} onSubmit={handleFirstnameSubmit}>
                    <InputText name = {"firstname"} placeholder={userInfo?.firstName}></InputText>
                    <OptionBtn submit={true} onClick={() => { }}>Spremi</OptionBtn>
                </form>

                <p className={styles.audioText}>Prezime:</p> <p></p>
                <form className={styles.inputWrapper} onSubmit={handleLastnameSubmit}>
                    <InputText name = {"lastname"} placeholder={userInfo?.lastName}></InputText>
                    <OptionBtn submit={true} onClick={() => { }}>Spremi</OptionBtn>
                </form>

                <p className={styles.audioText}>Lozinka:</p> <p></p>
                <form onSubmit={handleSubmit}>
                    <InputPassword name='password1' placeholder="Unesite novu lozinku" />
                    <InputPassword name="password2" placeholder="Ponovite novu lozinku" />
                    {error === true && <div className={styles.errorMsg}>Unesene lozinke su različite</div>}
                    <OptionBtn submit={true} onClick={() => { }}>Promijeni lozinku</OptionBtn>
                </form>
                
            </div>
        </div>
    );
}