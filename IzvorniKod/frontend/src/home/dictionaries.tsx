import { faPlus, faTrash, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Role, useUserContext } from "../userContext";
import { IconCard, ImgCard } from "./components/card";
import { useHomeContext } from "./homeContext";
import styles from "./styles/home.module.css";

export function Dictionaries() {
    const navigate = useNavigate();
    let { lang } = useParams();
    const { updateHomeText } = useHomeContext();
    let { userInfo } = useUserContext();
    let [dictionaries, setDictionaries] = useState<DictionaryMeta[]>([])

    async function findDicts() {
        setDictionaries(await fetch('/api/v1/languages/' + lang).then(res => res.json()));
    }

    useEffect(() => {
        updateHomeText(lang ?? '', 'Odaberite rječnik i pokrenite rješavanje kviza.');
        findDicts();
    }, []);

    function handleAdminIconCLick(dictId: number) {
        return () => {
            fetch('/api/v1/dictionaries/' + dictId, {
                method: 'DELETE'
            }).then(res => {
                if (res.ok) {
                    setDictionaries([...dictionaries.filter(dict => dict.id != dictId)]);
                }
            })
        };
    }

    function handleUserClick(dictId: number): () => void {
        // TODO: open quiz
        return () => {}
    }

    function handleAdminClick(dictId: number): () => void {
        return () => {
            navigate(`/dictionary/${lang}/${dictId}`);
        }
    }

    let icon: IconDefinition | undefined = faTrash;
    let handleClick = handleAdminClick;

    if (userInfo === null || Role[userInfo.role] !== Role.ADMIN) {
        icon = undefined;
        handleClick = handleUserClick;
    }

    return (
        <div className={styles.gridWrapper}>
            {
                userInfo !== null && Role[userInfo.role] === Role.ADMIN &&
                <IconCard
                    caption="Dodaj rječnik"
                    icon={faPlus}
                    handleClick={() => navigate('/dictionary/' + lang)} />
            }

            {
                dictionaries.map(dict =>
                    <ImgCard
                        key={dict.id}
                        caption={dict.name}
                        imageSrc="/images/book.jpg"
                        adminIcon={icon}
                        handleAdminIconClick={handleAdminIconCLick(dict.id)}
                        handleClick={handleClick(dict.id)} />)
            }
        </div>
    );
}
