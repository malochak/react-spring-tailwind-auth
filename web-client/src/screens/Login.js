import React from 'react';
import Layout from "../components/layout/Layout";
import LoginForm from "../components/forms/LoginForm";

function Login() {
    return (
        <Layout>
            <h1 className="text-2xl font-bold mb-3">Sign In</h1>
            <LoginForm />
        </Layout>
    );
}

export default Login;