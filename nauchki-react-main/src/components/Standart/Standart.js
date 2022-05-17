import React from 'react';
import './standart.scss';
import { NavLink } from 'react-router-dom';
import padlock from '../../img/iconPadlock.svg';

export const Standart = () => {
  return (
    <section className="standart">
      <h3 className="standart__title">Стандарты и нормы развития новорожденного мальчика</h3>

      <div className="standart__content">
        <div className="standart__data">
          <div className="standart__data-items">
            <div className="standart__data-title">
              <h4>Вес</h4>
            </div>

            <div className="standart__data-levels">
              <div className="level">
                <p className="level__title">Ур 3</p>
                <div className="level__color"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 2</p>
                <div className="level__color  level__color-two"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 1</p>
                <div className="level__color level__color-one"></div>
              </div>

              <div className="level">
                <p className="level__title">Медиана</p>
                <div className="level__color level__color-mediana"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 1</p>
                <div className="level__color level__color-one"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 2</p>
                <div className="level__color level__color-two"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 3</p>
                <div className="level__color level__color-three"></div>
              </div>
            </div>
          </div>

          <div className="standart__data-items">
            <div className="standart__data-title">
              <h4>Рост</h4>
            </div>

            <div className="standart__data-levels">
              <div className="level">
                <p className="level__title">Ур 3</p>
                <div className="level__color  level__color-three"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 2</p>
                <div className="level__color  level__color-two"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 1</p>
                <div className="level__color  level__color-one"></div>
              </div>

              <div className="level">
                <p className="level__title">Медиана</p>
                <div className="level__color level__color-mediana"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 1</p>
                <div className="level__color level__color-one"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 2</p>
                <div className="level__color level__color-two"></div>
              </div>
              <div className="level">
                <p className="level__title">Ур 3</p>
                <div className="level__color level__color-three"></div>
              </div>
            </div>
          </div>
          <div className="standart__data-items">
            <div className="standart__data-title">
              <h5> Что должен уметь ваш ребенок</h5>
            </div>

            <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink>
          </div>

          <div className="standart__data-items">
            <div className="standart__data-title">
              <h5>Оценка по шкале развития ГНОМ</h5>
            </div>

            <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink>
          </div>

          <div className="standart__data-items">
            <div className="standart__data-title">
              <h5>Оценка по зарубежным методикам</h5>
            </div>

            <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink>
          </div>
        </div>
        <NavLink className="standart_profile" to="/personalArea">
          Посмотреть информацию о своем ребенке
        </NavLink>
      </div>

      <p className="standart__notice">
        Представленная информация сформирована на основе открытых источников , не является
        диагнозом, планом лечения или развития. Носит исключительно ознакомительный харктер для
        подготовки к осмотру ребёнка профильными медицинскими специалистами.
      </p>
    </section>
  );
};
