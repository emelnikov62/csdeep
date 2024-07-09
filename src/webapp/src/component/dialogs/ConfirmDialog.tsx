import React from "react"
import {Dialog} from "primereact/dialog";
import {processBeforeMovement} from "$utils/dialogProcessing";
import {dialogHeader} from "$src/App";
import {Button} from "primereact/button";

export const ConfirmDialog = (props: {
    visible: boolean
    reject: () => void
    accept: () => void
    header: string
    message: string
}): JSX.Element => {

    return (
        <>
            <Dialog header={dialogHeader(props.header)} visible={props.visible}
                    onHide={props.reject}
                    onDragStart={() => processBeforeMovement()}
                    contentClassName="flex flex-col gap-2">
                <div className="w-[535px]">
                    <div className="grid grid-cols-12 my-2">
                        <div className="col-span-12">
                            <div className="flex flex-row gap-0.1 my-2 pb-6">
                                {props.message}
                            </div>
                        </div>
                    </div>
                    <div className="grid grid-cols-12 my-2">
                        <div className="col-span-6 justify-center flex">
                            <Button className="fa self-center old-style-button" label="Подтвердить" type="button" onClick={props.accept}/>
                        </div>
                        <div className="col-span-6 justify-center flex">
                            <Button type="button" label="Отменить" className="old-style-button" onClick={props.reject}/>
                        </div>
                    </div>
                </div>
            </Dialog>
        </>
    )
}