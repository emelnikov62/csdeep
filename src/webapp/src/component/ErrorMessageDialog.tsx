import React, {useContext, useEffect, useState} from "react";

import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import {AppContext} from "$src/AppContext";
import {dialogHeader} from "$src/App";
import {processBeforeMovement} from "$utils/dialogProcessing";
import {applicationStateActions} from "$store/applicationStateReducers";
import {ErrorMessageResponse} from "$src/model/ApplicationSetting";
import {useDispatch, useSelector} from "react-redux";
import {RootState, store} from "$src/store";
import {InputTextarea} from "primereact/inputtextarea";
import {logout} from "$src/client/api/authApi";
import {useNavigate} from "react-router-dom";

type Props = {}

const ErrorMessageDialog: React.FC<Props> = () => {
    const dispatch = useDispatch<typeof store.dispatch>();
    const [showErrorDialog, setShowErrorDialog] = useState(false)
    const executeResponse = useSelector<RootState>(
        (state) => state.applicationState.executeResponse
    ) as ErrorMessageResponse;
    const navigate = useNavigate();

    useEffect(() => {
        const goToHomePage = () => navigate('/');

        if (executeResponse != null && executeResponse.status == 401) {
            console.log("LOGOUT! LOGOUT!")
            logout()
            goToHomePage()
        } else {
            setShowErrorDialog(executeResponse != null && executeResponse.status !== 200)
        }
    }, [executeResponse])

    const appContext = useContext(AppContext)
    const getDialogHeader = () => {
        return appContext?.code !== "ROOT_APP" ? dialogHeader("Ошибка") : "Ошибка"
    }

    const resetError = () => {
        setShowErrorDialog(false)
        dispatch(applicationStateActions.reset())
    }
    const footer = () => (
        <div className="flex justify-end h-[40px]">
            <Button label="Закрыть" type="button" onClick={resetError} autoFocus/>
        </div>
    )

    return (
        <Dialog visible={showErrorDialog} onHide={resetError} header={getDialogHeader}
                onDragStart={() => processBeforeMovement()}
                contentClassName="flex flex-col gap-2" footer={footer}>
            <div>
                {executeResponse.timestamp && <p className="mb-3">
                    <label style={{fontWeight: "bold"}}>Timestamp:</label> <br/> {executeResponse.timestamp}
                </p>}
                {executeResponse.message && <p className="mb-3">
                    <label style={{fontWeight: "bold"}}>ErrorMessage:</label> <br/>
                    <InputTextarea value={executeResponse.message}
                                   className="text-area-no-resize w-full flatInputText  overflow-y-scroll" disabled autoResize={true}/>
                </p>}
                {executeResponse.path && <p className="mb-3">
                    <label style={{fontWeight: "bold"}}>Path:</label> <br/> {executeResponse.path}
                </p>}
                {executeResponse.trace &&
                    <div className="h-full mb-3">
                        <label style={{fontWeight: "bold"}}>Trace:</label> <br/>
                        <InputTextarea autoResize={false} className="text-area-no-resize w-full h-full mt-2 overflow-y-scroll"
                                       value={executeResponse.trace} disabled/>
                    </div>}
            </div>
        </Dialog>
    )
};

export default ErrorMessageDialog;