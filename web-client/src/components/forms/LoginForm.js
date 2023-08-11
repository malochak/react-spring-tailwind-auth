import React from 'react';
import {useForm} from "react-hook-form";

function LoginForm() {

    const {
        register,
        handleSubmit,
    } = useForm()

    // todo implement api call to authenticate with given credentials
    const onSubmit = (data) => console.log(data)

    return (
        <form className="flex flex-col" onSubmit={handleSubmit(onSubmit)}>
            <input type="email"
                   placeholder="E-mail"
                   className="input input-bordered input-primary w-full max-w-xs"
                   {...register("email", { required: true })}
            />
            <input type="password"
                   placeholder="Password"
                     {...register("password", { required: true })}
                   className="input input-bordered input-primary w-full max-w-xs"/>
            <button className="btn btn-primary w-fit ">Sign In</button>
        </form>
    );
}

export default LoginForm;