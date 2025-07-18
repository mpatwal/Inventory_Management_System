import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ProtectedRoute, AdminRoute } from "./service/Guard";
import RegisterPage from "./pages/Register";
import LoginPage from "./pages/Login";
import Category from "./pages/Category";
import Profile from "./pages/Profile";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<ProtectedRoute />} />

        {/*Admin Routes*/}
        <Route
          path="/category"
          element={<AdminRoute element={<Category />} />}
        />
        <Route path="/profile" element={<AdminRoute element={<Profile />} />} />
      </Routes>
    </Router>
  );
}

export default App;
