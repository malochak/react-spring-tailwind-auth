import React from 'react';
import {Link} from "react-router-dom";

function Header() {
    return (
        <div className="navbar bg-base-100">
            <div className="flex-1">
                <Link to="/">
                    <span className="btn btn-ghost normal-case text-xl">App Name</span>
                </Link>
            </div>
            <div className="flex-none">
                <Link to="/login">
                    <button className="btn">Sign In</button>
                </Link>
            </div>
        </div>
    );
}

export default Header;