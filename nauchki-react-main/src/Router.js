import React from 'react';
import { Route, Routes } from 'react-router-dom';

import { Main } from './pages/Main/Main';
import { Login } from './pages/Login/Login';
import { Registration } from './pages/Registration/Registration';
import { ResetPassword } from './pages/ResetPassword/ResetPassword';
import { RecoveryPassword } from './pages/RecoveryPassword/RecoveryPassword';

import { PersonalArea } from './pages/PersonalArea/PersonalArea';
import { PrivateRoute } from './PrivateRoute';
import { Adminka } from './pages/Adminka/Adminka';
// import { Articles } from './pages/Articles/Articles';
import { OneChildren } from './pages/PersonalArea/OneChildren';
import { Error404 } from './pages/Error 404/Error404';
import { AdminArticlesTable } from './components/AdminArticlesTable/AdminArticlesTable';
import AdminArticlesForm from './components/AdminArticlesForm/AdminArticlesForm';

export const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<Main />}></Route>

      <Route path="/registration" component={Registration} />
      <Route path="/login" element={<Login />} />
      <Route exact path="/recoverypass" component={RecoveryPassword} />
      <Route exact path="/resetpass" component={ResetPassword} />

      <Route exact path="/personalArea" element={<PrivateRoute />}>
        <Route path="" element={<PersonalArea />}></Route>
        <Route path=":id" element={<OneChildren />}></Route>
      </Route>

      <Route path={`/adminka`} element={<PrivateRoute />}>
        <Route path="" element={<Adminka />}>
          <Route index path="articles" element={<AdminArticlesTable />}></Route>
          <Route index path="articles/edit/:id" element={<AdminArticlesForm />}></Route>
          <Route index path="articles/add" element={<AdminArticlesForm />}></Route>

          <Route index path="stages" element={<AdminArticlesTable />}></Route>
          <Route index path="stages/edit/:id" element={<AdminArticlesForm />}></Route>
          <Route index path="stages/add" element={<AdminArticlesForm />}></Route>
        </Route>
      </Route>

      {/* <Route path={`/articles`} element={<PrivateRoute />}>
        <Route path="" element={<Articles />}></Route>
        <Route path=":id" element={<Articles />}></Route>
      </Route> */}

      <Route path={`/*`} element={<Error404 />} />
    </Routes>
  );
};
