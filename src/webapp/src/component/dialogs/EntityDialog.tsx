import React, {useRef} from "react"
import {Dialog} from "primereact/dialog";
import {processBeforeMovement} from "$utils/dialogProcessing";
import {Form, Formik, FormikProps} from "formik";
import {dialogHeader} from "$src/App";
import {Button} from "primereact/button";

export enum MODE_DIALOG {
    ADD = 'ADD',
    UPDATE = 'UPDATE',
    REMOVE = 'REMOVE',
    UPDATE_COUNT = 'UPDATE_COUNT'
}

export const EntityDialog = (props: {
    visible: boolean
    onHide: () => void
    onSubmit: (values: any) => void
    header: string
    mode: MODE_DIALOG
    children: React.ReactNode
    data?: any
    className?: string
}): JSX.Element => {
    const formik = useRef<FormikProps<any>>(null)

    return (
        <>
            <Dialog header={dialogHeader(props.header)} visible={props.visible}
                    onHide={props.onHide}
                    className={props.className}
                    resizable={false}
                    onDragStart={() => processBeforeMovement()}
                    contentClassName="flex flex-col gap-2">
                <div className="w-full">
                    <Formik initialValues={props.data ? props.data : {}} innerRef={formik} enableReinitialize
                            onSubmit={(values, {setSubmitting}): void => {
                                props.onSubmit(values)
                                setSubmitting(false)
                            }}>
                        <Form>
                            <div className="grid grid-cols-12">
                                <div className="col-span-12">
                                    <div className="flex flex-row gap-0.1 my-2">
                                        {props.children}
                                    </div>
                                </div>
                            </div>
                            <div className="grid grid-cols-12 my-2">
                                <div className="col-span-6 justify-center flex">
                                    <Button className="fa self-center old-style-button" label="Сохранить" type="submit"/>
                                </div>
                                <div className="col-span-6 justify-center flex">
                                    <Button type="button" label="Закрыть" className="old-style-button" onClick={props.onHide}/>
                                </div>
                            </div>
                        </Form>
                    </Formik>
                </div>
            </Dialog>
        </>
    )
}