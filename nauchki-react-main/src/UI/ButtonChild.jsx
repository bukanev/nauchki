import React from "react";
import classes from "./ButtonChild.module.css";

const ButtonChild = ({ children, ...props }) => {
  return (
    <button {...props} className={classes.childButton}>
      {children}
    </button>
  );
};

export default ButtonChild;
