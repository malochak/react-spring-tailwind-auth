import React from 'react';

function Header() {
    return (
        <div className="navbar bg-base-100">
            <div className="flex-1">
                <a className="btn btn-ghost normal-case text-xl">App Name</a>
            </div>
            <div className="flex-none">
                <button className="btn btn-primary">Sign In</button>
            </div>
        </div>
    );
}

export default Header;