import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { Role, useUserContext } from "../userContext";
import { ImgCard, IconCard } from "./components/card";
import { useHomeContext } from "./homeContext";
import { faPlus, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles/home.module.css";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { backendLangDataToLangData } from "./helpers";


export function Languages() {
    const navigate = useNavigate();
    let { userInfo } = useUserContext();
    const { updateHomeText } = useHomeContext();
    const [langs, setLangs] = useState<LanguageData[]>([])

    useEffect(() => {
        updateHomeText('Odabir jezika', 'Odaberite jezik koji želite vježbati.');

        (async function() {
            let addedLanguages: BackendLanguageData[] = await fetch('/api/v1/languages').then(resp => resp.json());
            setLangs(backendLangDataToLangData(addedLanguages));
        })();
    }, []);

    function openLang(lang: string) {
        return () => {
            navigate(`/home/${lang}`);
        }
    }

    function handleAdminIconCLick(langCode: string) {
        return () => {
            fetch('/api/v1/languages/' + langCode, {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                if (res.ok) {
                    setLangs([...langs.filter(lang=> lang.code !== langCode)]);
                }
            });
        }
    }

    let icon: IconDefinition | undefined = faTrashCan;

    if (userInfo === null || Role[userInfo.role] !== Role.ADMIN) {
        icon = undefined;
    }

    return (
        <div className={styles.gridWrapper}>
            {
                userInfo !== null && Role[userInfo.role] === Role.ADMIN &&
                <IconCard
                    caption="Dodaj jezik"
                    icon={faPlus}
                    handleClick={() => navigate('/add/language')} />
            }
            {
                langs.length !== 0 &&
                langs.map(lang =>
                    <ImgCard
                        key={lang.code}
                        caption={lang.name}
                        imageSrc={`/flags/${lang.flag.toLowerCase()}.png`}
                        handleClick={openLang(lang.code)}
                        adminIcon={icon}
                        handleAdminIconClick={handleAdminIconCLick(lang.code)} />)
            }
        </div>
    );
}
