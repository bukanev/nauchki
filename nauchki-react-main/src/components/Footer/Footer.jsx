import React from 'react';
import './footer.scss';

export const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer__content _container">
        <div className="footer__title"> Свяжитесь с нами: nau4ki@yandex.ru</div>
        <div>
          <ul className="footer__iconsmenu"></ul>
        </div>
      </div>
    </footer>
  );
};