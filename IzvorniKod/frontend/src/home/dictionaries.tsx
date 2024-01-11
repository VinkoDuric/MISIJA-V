import { Card } from "./components/card";
import styles from "./styles/home.module.css"; 

export function Dictionaries() {
    return (
        <div className={styles.gridWrapper}>
          <Card caption="Rječnik 1" imageSrc="images/book.jpg" />  {/* treba zamijeniti slike */}
          <Card caption="Rječnik 2" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 3" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 4" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 5" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 6" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 7" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 8" imageSrc="images/book.jpg" />
          <Card caption="Rječnik 9" imageSrc="images/book.jpg" />
        </div>
    );
}
