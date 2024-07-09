import React, {useContext} from "react"
import {dialogHeader} from "$src/App";
import {Dialog} from "primereact/dialog";
import {Button} from "primereact/button";
import {AppContext} from "$src/AppContext";
import {processBeforeMovement} from "$utils/dialogProcessing";

export const Messagebox = (props: {
    visible: boolean
    onHide: () => void
    children?: React.ReactNode
    title?: string
    message?: string
}): JSX.Element => {

    const footer = () => (
        <div className="flex justify-center">
            <Button label="Закрыть" onClick={props.onHide} type="button"/>
        </div>
    )

    const appContext = useContext(AppContext)

    return (
        <Dialog visible={props.visible} onHide={props.onHide} header={dialogHeader(props.title ?? appContext?.name)}
                onDragStart={() => processBeforeMovement()} className="whitespace-pre"
                footer={footer}>
            <div className="gap-2 my-2">
                {props.children ?? props.message}
            </div>
        </Dialog>
    )

}