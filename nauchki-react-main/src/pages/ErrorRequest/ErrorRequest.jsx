import React from 'react';
import "./errorRequest.scss";

export const ErrorRequest = ({ children }) => {
    return (
        <div className='error__request'>
            <p className='error__request-message'>{children}</p>
        </div>
    )
}
