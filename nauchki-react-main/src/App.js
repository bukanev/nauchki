import React from "react";
import { Header } from "./components/Header/Header";
import { Router } from "./Router";

import '../src/css/main.scss';
function App() {
  return (
    <div  className="_wrapper">
      <Header />
      <div className="_container">
      <Router />
      </div>
    </div>
  );
}

export default App;
