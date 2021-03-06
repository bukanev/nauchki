import React from 'react';
import './header.scss';
import { FaRegUserCircle } from 'react-icons/fa';
import { AiOutlineHome } from 'react-icons/ai';
import { Link, NavLink } from 'react-router-dom';
import logo from '../../img/logo_nauchki.svg';

export const Header = () => {
  return (
    <header className="header">
      <div className="header__content _container">
        <div className="header__title">
          <Link to="/" className="header__logo">
            <img src={logo} className="header__App-logo" alt="logo" />
          </Link>
        </div>
        <div>
          <ul className="header__menu">
            <li className="header__menu-item">
              <NavLink to="aboutproject">О проекте</NavLink>
            </li>
            {/* <li className="header__menu-item">
              <NavLink to="tariff">Тарифы</NavLink>
            </li> */}
            <li className="header__menu-item">
              <NavLink to="source">Источники данных</NavLink>
            </li>
          </ul>
        </div>
        <div>
          <ul className="header__iconsmenu">
            <li className="header__iconsmenu-btn">
              <NavLink to="/articles">Статьи</NavLink>
            </li>
            <li>
              <NavLink to="/adminka">
                <AiOutlineHome className="icon-size" />
              </NavLink>
            </li>
            <li>
              <NavLink to="/personalArea">
                <FaRegUserCircle className="icon-size" />
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </header>
  );
};
