import React, { createContext, useContext, useState } from "react";

const LogDataContext = createContext();

// стэйт между шагами
export const LogDataProvider = ({ children }) => {
  const [data, setData] = useState({});

  const setValues = (values) => {
    setData((prevData) => ({
      ...prevData,
      ...values,
    }));
  };

  return (
    <LogDataContext.Provider value={{ data, setValues }}>
      {children}
    </LogDataContext.Provider>
  );
};

// кастомный хук (обертка для более удобного использвания)
export const useData = () => useContext(LogDataContext);
