import React, {useState, useEffect} from "react";
import {Route, Routes} from "react-router-dom";
import './App.css';
import Home from "./components/home/Home";
import Login from "./components/home/Login";
import Signup from "./components/home/Signup";
import Inventory from "./components/inventory/Inventory";
import Shipping from "./components/shipping/Shipping";
import AuthService from "./services/AuthService";

function App() {

  const [companyName, setCompanyName] = useState(AuthService.getCurrentCompanyName);
  const [isCompanyNameSet, setCompanyStatus] = useState(false);

  useEffect(() => {
    userAuthState();
  })

  const userAuthState = () => {
    const currentCompanyName = AuthService.getCurrentCompanyName();
    if(currentCompanyName === undefined || currentCompanyName === null) {
      setCompanyName(currentCompanyName);
      setCompanyStatus(false);
    } else {
      setCompanyName(currentCompanyName);
      setCompanyStatus(true);
    }
  }

  return (
      <div>
        <Routes>
          {isCompanyNameSet && (
              <>
                <Route path={"/"} element={<Home />} />
                <Route path={"/home"} element={<Home />} />
                <Route path={"/login"} element={<Login authenticate = {() => userAuthState()} />} />
                <Route path={"/signup"} element={<Signup />} />
                <Route path={"/inventory"} element={<Inventory />} />
                <Route path={"/shipping"} element={<Shipping />} />
              </>
          )}

          {!isCompanyNameSet && (
              <>
                <Route path={"/"} element={<Home />} />
                <Route path={"/home"} element={<Home />} />
                <Route path={"/login"} element={<Login authenticate = {() => userAuthState()} />} />
                <Route path={"/signup"} element={<Signup />} />
                <Route path={"/inventory"} element={<Home />} />
                <Route path={"/shipping"} element={<Home />} />
              </>
          )}
        </Routes>
      </div>
  );
}

export default App;
