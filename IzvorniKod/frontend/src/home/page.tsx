import { useNavigate } from "react-router-dom";
import { Button } from "../components/form";
import { useEffect, useState } from "react";

export default function Home() {
    let navigate = useNavigate();

    let [serverText, setServerText] = useState<String|null>(null);

    useEffect(() => {
        fetch('/api/v1/secured/admin')
        .then(response => {
            if (response.ok) {
                return response.text()
            }
            throw new Error('Failed authentication')
        })
        .then(text => setServerText(text))
        .catch(error => console.log(error));
    }, []);

    function onClick() {
        fetch('/api/v1/auth/logout').then(() => {
            navigate('/');
        });
    }
    
    return (
    <>
        {serverText !== null ||
        <div>
            <span>{serverText}</span>
            <Button onClick={onClick}>Log Out</Button>
        </div>}
    </>
    );
}