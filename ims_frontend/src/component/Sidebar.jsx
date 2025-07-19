import React from "react";
import { Link } from "react-router-dom";
import APIService from "../service/APIService";

const logout = () => {
  APIService.logout();
};

const Sidebar = () => {
  const isAuth = APIService.isAuthenticated();
  const isAdmin = APIService.isAdmin();

  return (
    <div className="sidebar">
      <h1 className="ims">Inventory Management System</h1>
      <ul className="nav-links">
        {isAuth && (
          <li>
            <Link to="/dashboard">Dashboard</Link>
          </li>
        )}

        {isAuth && (
          <li>
            <Link to="/transactions">Transactions</Link>
          </li>
        )}

        {isAdmin && (
          <li>
            <Link to="/category">Category</Link>
          </li>
        )}

        {isAdmin && (
          <li>
            <Link to="/product">Product</Link>
          </li>
        )}

        {isAdmin && (
          <li>
            <Link to="/supplier">Supplier</Link>
          </li>
        )}

        {isAuth && (
          <li>
            <Link to="/purchase">Purchase</Link>{" "}
          </li>
        )}
        {isAuth && (
          <li>
            <Link to="/sell">Sell</Link>
          </li>
        )}

        {isAuth && (
          <li>
            <Link to="/profile">Profile</Link>
          </li>
        )}
        {isAuth && (
          <li>
            <Link onClick={logout} to="/login">
              Logout
            </Link>
          </li>
        )}
      </ul>
    </div>
  );
};

export default Sidebar;
