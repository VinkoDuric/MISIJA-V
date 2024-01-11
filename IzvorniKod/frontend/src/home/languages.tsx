import { Card } from "./components/card";
import { HomeHooks } from "./homeHooks";
import styles from "./styles/home.module.css"; 

export function Languages({updateHomeText}: HomeHooks) {
    updateHomeText('Odabir jezika', 'Odaberite jezik koji želite vježbati.');

    return (
        <div className={styles.gridWrapper}>
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
          <Card caption="Engleski" imageSrc="images/uk.svg" />
        </div>
    );
}