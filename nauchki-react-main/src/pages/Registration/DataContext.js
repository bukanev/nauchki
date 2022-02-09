import React, {createContext, useContext, useState} from "react";

const DataContext = createContext();

// стэйт между шагами
export const DataProvider = ({children}) => {
    const [data, setData] = useState({});

    const setValues = (values) => {
        setData(prevData => ({
            ...prevData,
            ...values
        }))
    }

    return (
        <DataContext.Provider value={{data, setValues}}>
            {children}
        </DataContext.Provider>
    );
};

// кастомный хук (обертка для более удобного использвания)
export const useData = () => useContext(DataContext);