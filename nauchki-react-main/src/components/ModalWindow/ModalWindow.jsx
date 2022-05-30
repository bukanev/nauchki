import React from 'react';
import { NavLink } from 'react-router-dom';
import './modalWindow.scss';

export const ModalWindow = ({ children, showModalClick, toggleShowModalClick, redirectPath }) => {

    return (
        <div className={"modal" + (showModalClick && " modal__open")}>
            <div className="modal__wrapper">
                <div className="modal__text">
                    {children}
                </div>

                <NavLink
                    to={redirectPath}
                    onClick={toggleShowModalClick}
                    className='modal__btn modal__btn-confirm'
                >
                    Ок
                </NavLink>
            </div>
        </div>
    )
}
