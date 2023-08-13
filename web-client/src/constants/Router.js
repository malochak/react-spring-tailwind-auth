import {createBrowserRouter} from "react-router-dom";
import {lazy} from "react";

const Home = lazy(() => import('../screens/Home'));
const Login = lazy(() => import('../screens/Login'));
const Register = lazy(() => import('../screens/Register'));

export const Router = createBrowserRouter([
        {
            path: '/',
            element: <Home/>
        },
        {
            path: '/login',
            element: <Login/>
        },
        {
            path: '/register',
            element: <Register/>
        }
    ]
)