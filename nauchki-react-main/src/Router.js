import React from "react";
import { Route } from "react-router-dom";
import { Registration } from "./pages/Registration/Registration";
import { Main } from "./pages/Main/Main";
import { Login } from "./pages/Login/Login";
import { PersonalArea } from "./pages/PersonalArea/PersonalArea";
import { Adminka } from "./pages/Adminka/Adminka";
import { PrivateRoute } from "./PrivateRoute";
import { Articles } from "./pages/Articles/Articles";
import { Themes } from "./pages/Articles/Themes";
import { OneChildren } from "./pages/PersonalArea/OneChildren";
import { Error404 } from "./pages/Error 404/Error404";


export const Router = () => {
  return (
    <>
      <Route path="/registration" component={Registration} />
      <Route path="/login" component={Login} />

      <Route path="/articles" component={Articles} />

      <PrivateRoute exact path="/personalArea" component={PersonalArea} />
      <PrivateRoute exact path="/personalArea/:id" component={OneChildren} />

      <PrivateRoute path="/adminka" component={Adminka} />

      <Route exact path="/" component={Main} />
      <Route path="*" component={Error404}></Route>
    </>
  );
};
