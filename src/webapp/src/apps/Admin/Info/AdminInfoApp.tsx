import React, {useEffect, useRef, useState} from "react";
import App from "$src/App";
import {CSDEEP_ADMIN, CSDEEP_ADMIN_INFO} from "$src/AppContext";
import {Toast} from "primereact/toast";
import {useNavigate} from "react-router-dom";
import {hasRoleByCodes, isApplicationPermitted} from "$src/model/User";
import * as AuthService from "$src/client/api/authApi";

export const AdminInfoApp = (): JSX.Element => {

    const link = document.querySelector("link[rel~='icon']") as HTMLAnchorElement | null
    const toast = useRef<Toast>(null)
    const [disabled, setDisabled] = useState(true)
    const navigate = useNavigate()
    const userInformation = AuthService.getCurrentUser()?.userInformation

    const onClose = () => {
        if (link) {
            link.href = '/favicon.ico'
        }
    }

    useEffect(() => {
        if (link) {
            link.href = CSDEEP_ADMIN_INFO.icon
        }
    })

    const exitAction = () => {
        onClose()
        navigate("/")
    }

    useEffect(() => {
        if (userInformation && userInformation.userAuthority) {
            const access = isApplicationPermitted(userInformation, CSDEEP_ADMIN_INFO)
            if (!access) {
                toast?.current?.show({
                    severity: 'error',
                    summary: CSDEEP_ADMIN_INFO.title,
                    detail: "Нет доступа",
                    life: 2000
                })
                setTimeout((e) => {
                    window.location.replace(CSDEEP_ADMIN.linkTo)
                }, 2000)
            } else {
                setDisabled(false)
            }

            if (!hasRoleByCodes(userInformation, ["AD"])) {
                toast?.current?.show({
                    severity: 'error',
                    summary: CSDEEP_ADMIN_INFO.title,
                    detail: "Нет доступа",
                    life: 2000
                })
            }
        } else {
            setTimeout((e) => {
                window.location.replace(CSDEEP_ADMIN.linkTo)
            }, 2000)
        }
    }, [userInformation])

    return (
        <>
            <Toast ref={toast}/>
            {!disabled && <App {...CSDEEP_ADMIN_INFO}>
                <div>CSDEEP_ADMIN_INFO</div>
            </App>}
            {disabled && <>
                <div className="loader-container">
                    <div className="spinner"></div>
                </div>
            </>}
        </>
    )
}