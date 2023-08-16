import React, {useEffect, useState} from 'react';
import Layout from "../components/layout/Layout";
import LoginForm from "../components/forms/LoginForm";
import logo from "../assets/logo/logo-512.png";
import RegisterForm from "../components/forms/RegisterForm";

export const AuthenticationType = {
    LOGIN: "login",
    REGISTER: "register"
}
function Authentication(props) {

    const [title, setTitle] = useState("Sign in to your account")

    useEffect(() => {
        setTitle(props.type === AuthenticationType.LOGIN ? "Sign in to your account" : "Create a new account")
    }, [props.type])

    return (
        <Layout>
            <div className="flex min-h-full flex-1 flex-col px-6 py-12 lg:px-8">

                <div className="sm:mx-auto sm:w-full sm:max-w-sm">

                    <img
                        className="mx-auto h-14 w-auto"
                        src={logo}
                        alt="App Name logo"
                    />
                    <h2 className="mt-4 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
                        { title }
                    </h2>
                    {props.type === AuthenticationType.LOGIN ? (<LoginForm />) : (<RegisterForm />)}
                </div>
            </div>
        </Layout>
    );
}

export default Authentication;