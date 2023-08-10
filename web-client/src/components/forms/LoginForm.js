import React from 'react';

function LoginForm(props) {
    return (
        <form className="flex flex-col">
            <input type="text" placeholder="E-mail" className="input input-bordered input-primary w-full max-w-xs"/>
            <input type="password" placeholder="Password"
                   className="input input-bordered input-primary w-full max-w-xs"/>
            <button className="btn btn-primary w-fit ">Sign In</button>
        </form>
    );
}

export default LoginForm;