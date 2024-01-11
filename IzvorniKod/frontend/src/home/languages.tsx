import { Card } from "./card";
import styles from "./styles/home.module.css"; 

export function Languages() {
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