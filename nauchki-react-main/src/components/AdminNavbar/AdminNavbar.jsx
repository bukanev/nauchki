import React from 'react';
import { Link } from 'react-router-dom';
import './styles.scss';

export const AdminNavbar = () => {
    return (
        <div className='navbar'>
            AdminNavbar
            <Link to={`/adminka`}>Главная </Link>
            <Link to={`articles`}>Статьи</Link>
            <Link to={`stage`}>Стандарты состояния ребенка</Link>
        </div >
    )
}