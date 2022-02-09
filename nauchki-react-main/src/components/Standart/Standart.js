import React from "react";
import { NavLink } from "react-router-dom";

export const Standart = () => {
  return (
    <section className="standart _wrapper">
      <button className="standart_profil">
        <NavLink to="/personalArea">
          Посмотреть информацию о своем ребенке
        </NavLink>
      </button>
    </section>
  );
};
