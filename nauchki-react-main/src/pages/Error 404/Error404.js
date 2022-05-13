import React from 'react';
import { NavLink } from 'react-router-dom';
import './error404.scss';

export const Error404 = () => {
  return (
    <section className="error">
      <div className="error__info">
        <h1 className="error__title">404</h1>
        <p className="error__text">Видимо вы заблудились, такой страницы не существует</p>
        <NavLink to="/" className="error__link">
          <span>Главная</span>
        </NavLink>
      </div>
    </section>
  );
};
