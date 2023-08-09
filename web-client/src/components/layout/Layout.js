import React from 'react';
import Header from "./Header";

function Layout(props) {
    return (
        <div className="App">
            <Header />
            <div className="my-8 mx-5">
                {props.children}
            </div>
        </div>
    );
}

export default Layout;