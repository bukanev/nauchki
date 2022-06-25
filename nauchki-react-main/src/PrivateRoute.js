import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { selectIsAuth } from './store/user/selectors';

export const PrivateRoute = () => {
  const isAuth = useSelector(selectIsAuth);
  let location = useLocation();

  return !isAuth ? <Navigate to="/login" state={{ from: location }} replace /> : <Outlet />;
};
