import {createBrowserRouter} from "react-router-dom";
import {lazy} from "react";
import {AuthenticationType} from "../screens/Authentication";

const Home = lazy(() => import('../screens/Home'));
const Authentication = lazy(() => import('../screens/Authentication'));

export const Router = createBrowserRouter([
        {
            path: '/',
            element: <Home/>
        },
        {
            path: '/login',
            element: <Authentication type={AuthenticationType.LOGIN}/>
        },
        {
            path: '/register',
            element: <Authentication type={AuthenticationType.REGISTER}/>
        }
    ]
)