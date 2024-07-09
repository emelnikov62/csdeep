import React, {useState} from "react";
import {ErrorMessage, Form, Formik} from "formik";
import * as Yup from "yup";

import {TextField} from "$src/component/form/TextField";
import {PasswordField} from "$src/component/form/PasswordField";
import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import {JwtResponse, LoginParam, useLogin, useSignInMutation} from "$src/client/api/authApi";
import {dialogHeader} from "$src/App";

type Props = {
    link: string
}

const Login: React.FC<Props> = (props: Props) => {
    const [loading, setLoading] = useState<boolean>(false);
    const [message, setMessage] = useState<string | undefined>(undefined);
    const [modifyLogin] = useSignInMutation();

    const modifyLoginUnwrapped = (modifyParam: LoginParam) => modifyLogin(modifyParam).unwrap()
        .then((response: JwtResponse) => {
            setMessage(undefined);
            setLoading(true);

            const result = useLogin(response);
            if (result && result.code == 0) {
                if (result.userInformation) {
                    window.location.replace(props.link);
                    window.location.reload();
                } else {
                    setLoading(false);
                    setMessage('Не удалось получить информацию о пользователе');
                }
            } else {
                setLoading(false);
                setMessage(response.message);
            }

        }).catch(e => {
            setLoading(false);
            setMessage(e.data.error + ' ' + e.status);
        })

    const initialValues: {
        username: string;
        password: string;
    } = {
        username: "",
        password: "",
    };

    const validationSchema = Yup.object().shape({
        username: Yup.string().required("Поле не заполнено!"),
        password: Yup.string().required("Поле не заполнено!"),
    });

    const handleLogin = (formValue: { username: string; password: string }) => {
        const {username, password} = formValue;
        localStorage.removeItem('userInformation')
        modifyLoginUnwrapped({username, password});
    };

    return (
        <>
            <div className="flex justify-center px-8 pt-2">
                <div className="w-[400px]">
                    <Dialog header={dialogHeader('Авторизация')} footer="" className="app-background w-[300px]"
                            onHide={() => true} visible={true}>
                        <Formik
                            initialValues={initialValues}
                            validationSchema={validationSchema}
                            onSubmit={handleLogin}
                        >
                            <Form>
                                <div className="grid gap-2">
                                    <div className="col-12">
                                        <div className="grid gap-2 my-2">
                                            <TextField label="Логин" name="username" className="w-full"/>
                                            <ErrorMessage
                                                name="username"
                                                component="div"
                                                className="alert alert-danger"/>
                                        </div>
                                        <div className="grid gap-2 my-4">
                                            <PasswordField label="Пароль" className="w-full" name="password" toggleMask feedback={false}/>
                                            <ErrorMessage
                                                name="password"
                                                component="div"
                                                className="alert alert-danger"/>
                                        </div>
                                        <div className="grid grid-cols-12">
                                            <div className="col-span-6">
                                                <div className="flex justify-start">
                                                    <Button type="submit" label="Войти" disabled={loading}/>
                                                </div>
                                            </div>
                                            <div className="col-span-6">
                                                <div className="flex justify-end">
                                                    <Button type="button" label="Отмена"
                                                            onClick={(e) => window.location.replace('/')}/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {message && (
                                    <div className="form-group pt-4">
                                        <div className="alert alert-danger" role="alert">
                                            {message}
                                        </div>
                                    </div>
                                )}
                            </Form>
                        </Formik>
                    </Dialog>
                </div>
            </div>
        </>
    );
};

export default Login;