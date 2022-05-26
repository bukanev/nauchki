import { Close } from '@material-ui/icons';
import React from 'react';
import './modalWindow.scss';

export const ModalWindow = ({ children, showModalClick, toggleShowModalClick }) => {
    return (
        <div className={'modal' + (showModalClick ? "  modal__open" : " ")}>
            <div className="modal__wrapper">
                <button
                    onClick={toggleShowModalClick}
                    className='modal__btn modal__btn-close'
                ><Close /></button>
                <div className="modal__text">
                    {children}
                </div>

                <button
                    onClick={toggleShowModalClick}
                    className='modal__btn modal__btn-confirm'
                >Подтвердить</button>
            </div>
        </div>
    )
}
