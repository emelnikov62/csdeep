import React from "react";
import App from "$src/App";
import {CSDEEP_MAIN} from "$src/AppContext";
import {Button} from "primereact/button";

export const MainPage = (): JSX.Element => {

    return (
        <>
            <App {...CSDEEP_MAIN}>
                <div className="flex flex-col">
                    <div className="h-[70px] bg-blue-50">
                        <div className="flex justify-end h-full" style={{alignItems: 'center'}}>
                            <div>
                                <Button type="button" onClick={(e) => window.location.replace('/#/admin')}
                                        label="Панель администратора" className="mr-2"/>
                            </div>
                        </div>
                    </div>
                    <div>
                        CSDEEP_MAIN
                    </div>
                </div>
            </App>
        </>
    )
}