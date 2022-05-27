import React from 'react';
import './modalWindow.scss';

export const ModalWindow = ({ children, showModalClick, toggleShowModalClick }) => {
    return (
        <div className={'modal' + (showModalClick ? "  modal__open" : " ")}>
            <div className="modal__wrapper">
                <div className="modal__text">
                    {children}
                </div>

                <button
                    onClick={toggleShowModalClick}
                    className='modal__btn modal__btn-confirm'
                > Ок</button>
            </div>
        </div>
    )
}
