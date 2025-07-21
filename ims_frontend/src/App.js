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
import AddEditproduct from "./pages/AddEditProduct";
import Purchase from "./pages/Purchase";
import Sell from "./pages/Sell";
import Transaction from "./pages/Transaction";
import TransactionDetails from "./pages/TransactionDetails";
import Dashboard from "./pages/Dashboard";

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
        <Route
          path="/add-product"
          element={<AdminRoute element={<AddEditproduct />} />}
        />
        <Route
          path="/edit-product/:productId"
          element={<AdminRoute element={<AddEditproduct />} />}
        />

        {/* ADMIN AND MANGER ROUTE*/}
        <Route
          path="/dashboard"
          element={<ProtectedRoute element={<Dashboard />} />}
        />
        <Route
          path="/profile"
          element={<ProtectedRoute element={<Profile />} />}
        />
        <Route
          path="/purchase"
          element={<ProtectedRoute element={<Purchase />} />}
        />
        <Route
          path="/transactions"
          element={<ProtectedRoute element={<Transaction />} />}
        />
        <Route
          path="/transactions/:transactionId"
          element={<ProtectedRoute element={<TransactionDetails />} />}
        />
        <Route path="/sell" element={<ProtectedRoute element={<Sell />} />} />
      </Routes>
    </Router>
  );
}

export default App;
