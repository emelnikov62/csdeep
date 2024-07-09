import {Toolbar} from "primereact/toolbar";
import React, {useContext} from "react";
import {AppContext} from "$src/AppContext";
import {Button} from "primereact/button";
import {useNavigate} from "react-router-dom";

export const Header = (props: {
    label?: string
    iconUrl?: string
    isDialog?: boolean
    onClose?: () => void
}): JSX.Element => {

    const appContext = useContext(AppContext)

    const appInfo = (): JSX.Element => <div className="flex flex-row gap-2 p-1">
        <img src={props.iconUrl ?? appContext?.icon} alt={props.label ?? appContext?.name} className="w-5 hidden"/>
        <label className="text-base text-white">{props.label}</label>
    </div>

    const navigate = useNavigate()

    const btnExit = (): JSX.Element => <Button key="btExit" tooltip="Выход" className="pi pi-times button-app"
                                               onClick={() => {
                                                   props.onClose && props.onClose()
                                                   navigate("/")
                                               }}/>

    return props.isDialog ? appInfo() : <Toolbar left={appInfo} right={btnExit} className="p-0"/>

}