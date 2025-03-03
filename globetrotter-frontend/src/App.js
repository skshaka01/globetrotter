import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Landing from './components/Landing';
import InvitationPage from './components/InvitationPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/challenge" element={<InvitationPage />} />
    </Routes>
  );
}

export default App;
