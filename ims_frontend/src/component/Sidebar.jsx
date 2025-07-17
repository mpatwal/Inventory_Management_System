import React from 'react';
import { Link } from 'react-router-dom';
import ApiService from '../services/ApiService';

const logout = () => {
  
    ApiService.logout();

};

const Sidebar = () => {
    const isAuth = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();

    return (
        <div className="sidebar">
            <h1 className="ims">IMS</h1>
            <ul className='nav-links'>
                { isAuth && 
                  <li>
                    <Link to="/dashboard">Dashboard</Link></li>
                }

           {isAuth &&(
            <li>
             <Link to ="/transactions">Transactions</Link></li>
            )}

            {isAdmin && (
              <li>
                <Link to="/category">Category</Link></li>
            )}

            {isAdmin && (
              <li>
                <Link to="/product">Product</Link>
              </li>
            )}

            {isAdmin && (
            <li>
                <Link to="/suppliers">Supplier</Link></li>
            )}

            {isAuth && (
            <li>
              <Link to="/purchase">Purchase</Link> </li>
            )}
            {isAuth && (
                <li><Link to="/sell">Sell</Link></li>
            )}
            
            {isAuth && (
                <li><Link to="/profile">Profile</Link></li>
            )}
            {isAuth && (
                <li><Link onClick={logout} to="/login">Logout</Link></li>
            )}
            </ul>

        </div>
    );
};

export default Sidebar;
