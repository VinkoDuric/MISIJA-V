import React, { useEffect } from 'react';
import styles from "../styles/home.module.css";

interface AudioTimerProps {
  isRunning: boolean;
  setIsRunning: React.Dispatch<React.SetStateAction<boolean>>;
  elapsedTime: number;
  setElapsedTime: React.Dispatch<React.SetStateAction<number>>;
}

const AudioTimer: React.FC<AudioTimerProps> = ({
  isRunning,
  setIsRunning,
  elapsedTime,
  setElapsedTime,
}) => {
  useEffect(() => {
    let intervalId: NodeJS.Timeout;

    if (isRunning) {
      intervalId = setInterval(() => setElapsedTime((prevElapsedTime) => prevElapsedTime + 1), 10);
    }

    return () => clearInterval(intervalId);
  }, [isRunning, setElapsedTime]);

  const hours = Math.floor(elapsedTime / 360000);
  const minutes = Math.floor((elapsedTime % 360000) / 6000);
  const seconds = Math.floor((elapsedTime % 6000) / 100);
  const milliseconds = elapsedTime % 100;

  return (
    <div className="text-[25px] mt-4 font-semibold">
      <div className={styles.audioText}>
        {hours}:{minutes.toString().padStart(2, '0')}:
        <span className={styles.audioText}> {seconds.toString().padStart(2, '0')}:</span>
        <span className={styles.audioText}>{milliseconds.toString().padStart(2, '0')}</span>
      </div>
    </div>
  );
};

export default AudioTimer;
