import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ProtectedRoute, AdminRoute } from "./service/Guard";
import RegisterPage from "./pages/Register";
import LoginPage from "./pages/Login";
import Category from "./pages/Category";
import Profile from "./pages/Profile";
import Supplier from "./pages/Supplier";
import AddEditSupplier from "./pages/AddEditSupplier";
import Product from "./pages/Product";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />

        {/*Admin Routes*/}
        <Route
          path="/category"
          element={<AdminRoute element={<Category />} />}
        />
        <Route
          path="/profile"
          element={<ProtectedRoute element={<Profile />} />}
        />
        <Route
          path="/supplier"
          element={<AdminRoute element={<Supplier />} />}
        />
        <Route
          path="/add-supplier"
          element={<AdminRoute element={<AddEditSupplier />} />}
        />
        <Route
          path="/edit-supplier/:supplierId"
          element={<AdminRoute element={<AddEditSupplier />} />}
        />
        <Route path="/product" element={<AdminRoute element={<Product />} />} />
      </Routes>
    </Router>
  );
}

export default App;
