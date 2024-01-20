import { OptionBtn } from "./components/optionBtn";
import styles from "./styles/home.module.css";
import { InputPassword, InputText } from "../components/form";
import { UserInfo, useUserContext } from "../userContext";
import { FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useHomeContext } from "./homeContext";

export function AddAdmin() {
  const { userInfo, updateUserInfo } = useUserContext();
  const { updateHomeText } = useHomeContext();
  let navigate = useNavigate();
  let [error, setError] = useState<boolean>(false);

  useEffect(() => {
    updateHomeText("Dodaj admina", "");
  }, []);

  async function handleSubmit(
    event: FormEvent<HTMLFormElement>
  ): Promise<void> {
    event.preventDefault();
    const formData = new FormData(event.target as HTMLFormElement);
    const inputData = Object.fromEntries(Array.from(formData));

    const response = await fetch(`/api/v1/account?email=${inputData.email}`);
    if (!response.ok) {
      setError(true);
      return;
    }

    const userData: UserInfo = await response.json();

    if (
      userData.firstName === inputData.firstname &&
      userData.lastName === inputData.lastname &&
      userData.email === inputData.email
    ) {
      const updatedUser: UserInfo = {
        ...userData,
        role: "ADMIN",
      };

      const updateResponse = await fetch("/api/v1/account", {
        method: "PUT",
        body: JSON.stringify(updatedUser),
        headers: { "Content-Type": "application/json" },
      });

      if (updateResponse.ok) {
        updateUserInfo(updatedUser);
        navigate("/home");
      } else {
        setError(true);
      }
    } else {
      setError(true);
    }
  }

  return (
    <div className={styles.quizWrapper}>
      <div className={styles.accountWrapper}>
        <form className={styles.inputWrapper} onSubmit={handleSubmit}>
          <p></p>
          <p></p>
          <p className={styles.audioText}>Ime:</p>
          <InputText
            name={"firstname"}
            placeholder={"Ime novog admina"}
          ></InputText>
          <p className={styles.audioText}>Prezime:</p>
          <InputText
            name={"lastname"}
            placeholder={"Prezime novog admina"}
          ></InputText>
          <p className={styles.audioText}>Email:</p>
          <InputText name={"email"} placeholder={"admin@fer.hr"}></InputText>
          <p></p>
          <OptionBtn submit={true} onClick={() => {}}>
            Dodaj
          </OptionBtn>
        </form>
        {error && <p>Error: Invalid input or user not found</p>}
      </div>
    </div>
  );
}
