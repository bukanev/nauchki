import React from "react";
import { Landing } from "../../components/Landing/Landing";
import { Information } from "../../components/Information/Informarion";
import { Standart } from "../../components/Standart/Standart";
import { NavLink } from "react-router-dom";
// import { Tariff } from "../../components/Tariff/Tariff";

const standartsStages = {
  id: 15,
  gender: "Жен",
  medianHeightMinus3: 44.2,
  medianHeightMinus2: 46.1,
  medianHeightMinus1: 48,
  medianHeight: 49.9,
  medianHeightPlus1: 51.8,
  medianHeightPlus2: 53.7,
  medianHeightPlus3: 55.6,

  medianWeightMinus3: 2.1,
  medianWeightMinus2: 2.5,
  medianWeightMinus1: 2.9,
  medianWeight: 3.3,
  medianWeightPlus1: 3.9,
  medianWeightPlus2: 4.4,
  medianWeightPlus3: 5,
  skills: "Это самое начало вашего знакомства с малышом. В это время ребенок спит большую часть времени, около 16 часов в сутки. У него ярко выражены рефлексы, которые относятся к категории безусловных, такие, например, как реакция на свет, близкий резкий звук - ребенок закрывает глазки. Если пальцем нажать на ладошку, то сжимаются пальцы. Также и на ножках, пальцы сожмутся при нажатии на подошву. При поднятии ребенка в положении на животе, то он будет вытягивать ноги, стараться поднять голову. Руки малыша сгибаются, если локти его развести в стороны, а при повороте головы направо поднимается подбородок и вытягивается правая рука, левая может сгибаться."
}

export const Main = () => {
  return (
    <>
      <Landing />
      <Information />
      <section className="standart" key={standartsStages?.id}>
        <Standart standartsStages={standartsStages} />
        <NavLink className="standart_profile" to="/personalArea">
          Посмотреть информацию о своем ребенке
        </NavLink>
        <p className="standart__notice">
          Представленная информация сформирована на основе открытых источников , не является
          диагнозом, планом лечения или развития. Носит исключительно ознакомительный харктер для
          подготовки к осмотру ребёнка профильными медицинскими специалистами.
        </p>
      </section>

      {/*<Tariff />*/}
    </>
  );
};

