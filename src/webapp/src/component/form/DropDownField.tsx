import {FieldHookConfig, useField, useFormikContext} from "formik";
import React, {useRef} from "react";
import {Dropdown, DropdownChangeParams, DropdownProps} from "primereact/dropdown";

export interface FieldProps {
    label?: string
    onFieldChange?: (fieldName: string, fieldValue: any) => void
    onFieldClick?: () => void
    showHelp?: boolean
    required?: boolean
    emptyOption?: boolean
}

export const DropdownField = (props: JSX.IntrinsicClassAttributes<Dropdown> & Readonly<DropdownProps> & FieldHookConfig<any> & FieldProps): JSX.Element => {
    const [field, meta] = useField(props);
    const {setFieldValue} = useFormikContext<{ [key: string]: any }>()
    const fieldRef = useRef<Dropdown & { show: () => void, hide: () => void }>(null)

    const onChange = (e: DropdownChangeParams) => {
        setFieldValue(field.name, e.value)
        if (props.onFieldChange) {
            props.onFieldChange(field.name, e.value)
        }
    }

    return (
        <>
            <div className="flex flex-col gap-1">
                <label htmlFor={field.name} className="font-bold">{props.label}</label>
                <Dropdown {...props} value={field.value === '' ? null : field.value} onChange={onChange} ref={fieldRef}/>
                {props.showHelp &&
                    <small id={field.name + '-help'}>
                        Введите {props.label}.
                    </small>
                }
            </div>
        </>
    )
}