import './App.css';
import {Router} from "./constants/Router";
import {RouterProvider} from "react-router-dom";
import {Suspense} from "react";

function App() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <RouterProvider router={Router}/>
        </Suspense>
    );
}

export default App;
