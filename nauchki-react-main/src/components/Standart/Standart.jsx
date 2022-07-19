import React from 'react';
import './standart.scss';
// import padlock from '../../img/iconPadlock.svg';

export const Standart = ({ standartsStages, gender }) => {
  return (
    <div className="standart__content">
      <h3 className="standart__title">Стандарты и нормы развития {gender}</h3>
      <div className="standart__data">
        <div className="standart__data-items">
          <div className="standart__data-title">
            <h4>Вес (кг)</h4>
          </div>

          <div className="standart__data-levels">
            <div className="level">
              <p className="level__title">Ур 3</p>
              <div className="level__color level__color-three">{standartsStages?.medianWeightMinus3}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 2</p>
              <div className="level__color  level__color-two">{standartsStages?.medianWeightMinus2}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 1</p>
              <div className="level__color level__color-one">{standartsStages?.medianWeightMinus1}</div>
            </div>

            <div className="level">
              <p className="level__title">Медиана</p>
              <div className="level__color level__color-mediana">{standartsStages?.medianWeight}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 1</p>
              <div className="level__color level__color-one">{standartsStages?.medianWeightPlus1}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 2</p>
              <div className="level__color level__color-two">{standartsStages?.medianWeightPlus2}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 3</p>
              <div className="level__color level__color-three">{standartsStages?.medianWeightPlus3}</div>
            </div>
          </div>
        </div>

        <div className="standart__data-items">
          <div className="standart__data-title">
            <h4>Рост (см)</h4>
          </div>

          <div className="standart__data-levels">
            <div className="level">
              <p className="level__title">Ур 3</p>
              <div className="level__color  level__color-three">{standartsStages?.medianHeightMinus3}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 2</p>
              <div className="level__color  level__color-two">{standartsStages?.medianHeightMinus2}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 1</p>
              <div className="level__color  level__color-one">{standartsStages?.medianHeightMinus1}</div>
            </div>

            <div className="level">
              <p className="level__title">Медиана</p>
              <div className="level__color level__color-mediana">{standartsStages?.medianHeight}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 1</p>
              <div className="level__color level__color-one">{standartsStages?.medianHeightPlus1}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 2</p>
              <div className="level__color level__color-two">{standartsStages?.medianHeightPlus2}</div>
            </div>
            <div className="level">
              <p className="level__title">Ур 3</p>
              <div className="level__color level__color-three">{standartsStages?.medianHeightPlus3}</div>
            </div>
          </div>
        </div>
        <div className="standart__data-items">
          <div className="standart__data-title">
            <h5> Что должен уметь ваш ребенок</h5>
          </div>

          <div className="standart__data-skills ">
            {standartsStages?.skills}
          </div >

          {/* <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink> */}
        </div>

        {/* <div className="standart__data-items">
            <div className="standart__data-title">
              <h5>Оценка по шкале развития ГНОМ</h5>
            </div>

            <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink>
          </div> */}

        {/* <div className="standart__data-items">
            <div className="standart__data-title">
              <h5>Оценка по зарубежным методикам</h5>
            </div>

            <NavLink to="/" className="standart__data-lock">
              <img src={padlock} alt="padlock" />
            </NavLink>
          </div> */}
      </div>
    </div>

  );
};
