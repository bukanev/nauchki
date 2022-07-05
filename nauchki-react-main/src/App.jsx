import React from "react";
import { Header } from "./components/Header/Header";
import { Router } from "./Router";
import { Footer } from "./components/Footer/Footer";
import '../src/css/main.scss';
import { Outlet } from "react-router-dom";

function App() {
  return (
    <div className="_wrapper">
      <Header />
      <div className="_container">
        <Router />
      </div>

      <div className="_downApp">
        <Footer />
      </div>
      <Outlet />
    </div>
  );
}

export default App;
