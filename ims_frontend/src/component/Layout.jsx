import React from "react";
import Sidebar from "./Sidebar";

const Layout = ({ page }) => {
    return (
        <div className="Layout">
        <Sidebar />
        <div className="main-content">
            {page}
        </div>
        </div>
    );
    }
    export default Layout;

