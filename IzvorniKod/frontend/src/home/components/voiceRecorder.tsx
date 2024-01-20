import { useState } from 'react';
import { ReactMic } from 'react-mic';
import AudioTimer from './audioTimer'; 
import styles from "../styles/home.module.css";

const ReactRecorder: React.FC = () => {
  const [isRunning, setIsRunning] = useState(false);
  const [elapsedTime, setElapsedTime] = useState(0);
  const [voice, setVoice] = useState(false);
  const [recordBlobLink, setRecordBlobLink] = useState<string | null>(null);

  const onStop = (recordedBlob: { blobURL: string }) => {
    setRecordBlobLink(recordedBlob.blobURL);
    setIsRunning(false);
  };

  const startHandle = () => {
    console.log("Zapocinje snimanje")
    setElapsedTime(0);
    setIsRunning(true);
    setVoice(true);
  };

  const stopHandle = () => {
    console.log("Kraj snimanja")
    setIsRunning(false);
    setVoice(false);
  };

  const clearHandle = () => {
    setIsRunning(false);
    setVoice(false);
    setRecordBlobLink(null);
    setElapsedTime(0);
  };

  return (
    <div>
      <div className="max-w-sm border py-4 px-6 mx-auto bg-black">
        <AudioTimer isRunning={isRunning} setIsRunning={setIsRunning} elapsedTime={elapsedTime} setElapsedTime={setElapsedTime} />

        <ReactMic record={voice} className="sound-wave w-full" onStop={onStop} strokeColor="#000000" />
        <div className="">
          {recordBlobLink ? (
            <button onClick={clearHandle} className="text-[#fff] font-medium text-[16px]">
              Obriši
            </button>
          ) : (
            ''
          )}
        </div>
        <div className="mt-2">
          {!voice ? (
            <button onClick={startHandle} className="bg-[#fff] text-[#111] rounded-md py-1 px-3 font-semibold text-[16px]">
              Započni 
            </button>
          ) : (
            <button onClick={stopHandle} className="bg-[#fff] text-[#111] rounded-md py-1 px-3 font-semibold text-[16px]">
              Zaustavi
            </button>
          )}
        </div>
        <div className="">
          {recordBlobLink ? 
          <div className={styles.answersWrapper}><audio controls src={recordBlobLink} className="mt-6" />
          <button onClick={() => { }} className="bg-[#fff] text-[#111] rounded-md py-1 px-3 font-semibold text-[16px]">
              Predaj!
            </button>
          </div> : ''}
        </div>
      </div>
    </div>
  );
};

export default ReactRecorder;
