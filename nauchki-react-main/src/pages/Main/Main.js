import React from "react";
import { Landing } from "../../components/Landing/Landing";
import { Information } from "../../components/Information/Informarion";
import { Standart } from "../../components/Standart/Standart";
import { Tariff } from "../../components/Tariff/Tariff";

export const Main = () => {
  return (
    <>
      <Landing />
      <Information />
      <Standart />
      <Tariff />
    </>
  );
};
