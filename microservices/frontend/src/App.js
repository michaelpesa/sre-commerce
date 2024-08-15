import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Orders from './components/Orders';
import Navigation from './components/Navigation';

const App = () => {
  return (
    <Router>
      <div>
        <Navigation />
        <Routes>
          <Route path="/orderss" element={<Orders />} />
          <Route path="/" element={<Orders />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;

