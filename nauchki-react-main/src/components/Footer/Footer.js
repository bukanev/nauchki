import React from "react";
import { FaRegUserCircle } from "react-icons/fa";
import { AiOutlineHome } from "react-icons/ai";
import { NavLink } from "react-router-dom";


export const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer__content _container">
        <div className="footer__title"> Свяжитесь с нами: nauchki@yandex.ru
        </div>
        <div>
          <ul className="footer__iconsmenu">
          </ul>
        </div>
      </div>
    </footer>
  );
};
