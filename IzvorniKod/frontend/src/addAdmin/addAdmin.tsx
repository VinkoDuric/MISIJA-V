import { useEffect } from "react";
import { useHomeContext } from "../home/homeContext";

const AddAdmin = () => {
  let { text, updateHomeText } = useHomeContext();

  useEffect(() => {
    updateHomeText("Dodaj admina", "Dodajte admina preko njegovo e-maila");
  }, []);

  return <div></div>;
};

export default AddAdmin;
