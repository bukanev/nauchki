import React from "react";
import { Route } from "react-router-dom";
import { useSelector } from "react-redux";
import { LogIn } from "./pages/LogIn/LogIn";

export const PrivateRoute = ({ component: Component, ...rest }) => {
  const isAuth = useSelector((state) => state.user.isAuth);

  return (
    <Route
      {...rest}
      render={(props) => {
        if (isAuth === true) {
          return <Component {...props} />;
        } else {
          return <LogIn {...props} />;
        }
      }}
    />
  );
};

