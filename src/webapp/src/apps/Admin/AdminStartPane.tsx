import React from "react";
import App from "$src/App";
import {CSDEEP_ADMIN, CSDEEP_ADMIN_INFO} from "$src/AppContext";
import Login from "$src/component/Login";

export const AdminStartPane = (): JSX.Element => {

    return (
        <App {...CSDEEP_ADMIN}>
            <div className="flex justify-around content-center px-20 gap-7 h-full">
                <Login link={CSDEEP_ADMIN_INFO.linkTo}/>
            </div>
        </App>
    )
}