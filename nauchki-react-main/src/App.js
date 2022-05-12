import React from "react";
import { Header } from "./components/Header/Header";
import { Router } from "./Router";
import { Footer } from "./components/Footer/Footer";
import '../src/css/main.scss';

function App() {
  return (
    <div  className="_wrapper">
      <Header />
      <div className="_container">
      <Router />
    </div>

        <div className="_downApp">
            <Footer/>
      </div>

    </div>
  );
}

export default App;
