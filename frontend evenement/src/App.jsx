// src/App.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ContainerTerminalExpertSystem from "./Pages/ContainerTerminalExpertSystem";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ContainerTerminalExpertSystem />} />
      </Routes>
    </Router>
  );
}
