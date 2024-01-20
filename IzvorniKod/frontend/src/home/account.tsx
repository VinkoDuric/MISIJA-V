import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { InputPassword, InputText } from "../components/form";
import { UserInfo, useUserContext } from "../userContext";
import { FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";


export function Account(){
    const { userInfo, updateUserInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
    let navigate = useNavigate();
    let [error, setError] = useState<boolean>(false);

    useEffect(() => {
        updateHomeText('Upravljanje računom', 'Promjenite osobne informacije svog računa.');
    }, []);

    async function handleFirstnameSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        const formData = new FormData(event.target as HTMLFormElement);

        formData.forEach((value, property) => console.log(value, property));
        let inputData = Object.fromEntries(Array.from(formData));

        let newFirstName = inputData.firstname?.toString();

        const newUser = {
            firstName: newFirstName || '',
        };

        fetch('/api/v1/account', {
            method: 'PUT',
            body: JSON.stringify(newUser),
            headers: new Headers({ 'Content-Type': 'application/json' })
        }).then(response => {
            console.log(response);
            if (response.ok) {
                return response.json()
            }
        }).then(json => {
            console.log(json);
            if (userInfo === null) return;
            let newUserInfo: UserInfo = {...userInfo};
            newUserInfo.firstName = newFirstName;
            updateUserInfo(newUserInfo);
        });
    }

    async function handleLastnameSubmit(event: FormEvent<HTMLFormElement>): Promise<void> {
        event.preventDefault();
        const formData = new FormData(event.target as HTMLFormElement);

        formData.forEach((value, property) => console.log(value, property));
        let inputData = Object.fromEntries(Array.from(formData));

        let newLastName = inputData.lastname?.toString() || '';

        const newUser = {
            lastName: newLastName,
        };

        fetch('/api/v1/account', {
            method: 'PUT',
            body: JSON.stringify(newUser),
            headers: new Headers({ 'Content-Type': 'application/json' })
        }).then(response => {
            console.log(response);
            if (response.ok) {
                return response.json()
            }
        }).then(json => {
            console.log(json);
            if (userInfo === null) return;
            let newUserInfo: UserInfo = {...userInfo};
            newUserInfo.lastName = newLastName;
            updateUserInfo(newUserInfo);
        });
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
                <p className={styles.accountText}>Ime:</p> <p></p>
                <form className={styles.inputWrapper} onSubmit={handleFirstnameSubmit}>
                    <InputText name = {"firstname"} placeholder={userInfo?.firstName}></InputText>
                    <OptionBtn submit={true} onClick={() => { }}>Spremi</OptionBtn>
                </form>

                <p className={styles.accountText}>Prezime:</p> <p></p>
                <form className={styles.inputWrapper} onSubmit={handleLastnameSubmit}>
                    <InputText name = {"lastname"} placeholder={userInfo?.lastName}></InputText>
                    <OptionBtn submit={true} onClick={() => { }}>Spremi</OptionBtn>
                </form>

                <p className={styles.accountText}>Lozinka:</p> <p></p>
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
