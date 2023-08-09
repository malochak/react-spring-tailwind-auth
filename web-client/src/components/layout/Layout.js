import React from 'react';
import Header from "./Header";

function Layout(props) {
    return (
        <div className="App">
            <Header />
            <div className="my-8 mx-5"  style={{
                minHeight: "calc(100vh - 64px - 220px - 57px)"
            }}>
                {props.children}
            </div>
        </div>
    );
}

export default Layout;