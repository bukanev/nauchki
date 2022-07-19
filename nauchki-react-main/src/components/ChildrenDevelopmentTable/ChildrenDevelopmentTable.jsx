import React from "react";
import "./childrenDevelopmentTable.scss";

export const ChildrenDevelopmentTable = () => {
  return (
    <div className="development__table">
      <div className="development__name"><p>Дата</p></div>
      <div className="development__name"><p>Вес</p></div>
      <div className="development__name"><p>рост</p></div>

      <div className="development__data-value">01.01.2021</div>
      <div className="development__data-value">10.2</div>
      <div className="development__data-value">120</div>
    </div>
  );
};
