import React from 'react';
import Header from "./Header";

function Layout(props) {
    return (
        <div>
            <Header />
            <div className="my-8 mx-5 content">
                {props.children}
            </div>
        </div>
    );
}

export default Layout;