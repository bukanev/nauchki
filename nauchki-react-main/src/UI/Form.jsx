import React from "react";

export const Form = ({ children, ...props }) => {
  return (
    <form noValidate {...props}>
      {children}
    </form>
  );
};
