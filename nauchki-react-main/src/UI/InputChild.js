import React from "react";
import classes from "./InputChild.module.css";

const InputChild = (props) => {
  return <input className={classes.inputChild} {...props} />;
};

export default InputChild;
