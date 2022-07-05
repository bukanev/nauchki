import React from "react";
import { Outlet } from "react-router-dom";
import { AdminNavbar } from "../../components/AdminNavbar/AdminNavbar";
import "./adminka.scss";

export const Adminka = () => {
  return (
    <div className="admin">
      <h1>Админка</h1>
      <div className="admin__wrapper">
        <AdminNavbar />

        <div className="admin__content">
          <Outlet />
        </div>
      </div>
    </div >
  );
};