import { useEffect, useState } from "react";
import { useHomeContext } from "../home/homeContext";
import styles from "./styles/addAdmin.module.css";

const AddAdmin = () => {
  let { updateHomeText } = useHomeContext();
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [infoMessage, setInfoMessage] = useState("");

  useEffect(() => {
    updateHomeText("Dodaj admina", "Dodajte admina preko njegovo e-maila");
  }, []);

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setInfoMessage("Dodavanje admina u tijeku...");

    // TODO: promjeni ovaj api endpoint
    const response = fetch("http://localhost:3000/api/admins", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, name, lastName }),
    })
      .then((response) => response.json())
      .then(() => {
        setInfoMessage("Admin je uspješno dodan");
      })
      .catch((error) => {
        setInfoMessage("Došlo je do pogreške" + error?.message);
      });
  };

  return (
    <form className={styles.form} onSubmit={onSubmit}>
      <div className={styles.input}>
        <label htmlFor="name">Unesite ime</label>
        <input
          id="name"
          type="text"
          placeholder="Ivan"
          value={name}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setName(e.target.value);
          }}
        ></input>
      </div>

      <div className={styles.input}>
        <label htmlFor="lastName">Unesite ime</label>
        <input
          id="lastName"
          type="text"
          placeholder=" vrk"
          value={lastName}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setLastName(e.target.value);
          }}
        ></input>
      </div>

      <div className={styles.input}>
        <label htmlFor="email">Unesite e-mail</label>
        <input
          id="email"
          type="email"
          placeholder="ivan.cvrk@gmail.com"
          value={email}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setEmail(e.target.value);
          }}
        ></input>
      </div>

      <div style={{ display: "flex", gap: "20px" }}>
        <button type="submit"> Dodaj</button>
        <p>{infoMessage}</p>
      </div>
    </form>
  );
};

export default AddAdmin;
