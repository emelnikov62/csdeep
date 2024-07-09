import {InputText, InputTextProps} from "primereact/inputtext";
import {FieldHookConfig, useField, useFormikContext} from "formik";
import React from "react";
import {InputMask, InputMaskChangeParams, InputMaskProps} from "primereact/inputmask";

export interface FieldProps {
    label?: string
    onFieldChange?: (fieldName: string, fieldValue: any) => void
    onFieldClick?: () => void
    showHelp?: boolean
    required?: boolean
}

export const TextField = (props: JSX.IntrinsicClassAttributes<InputText> & Readonly<InputTextProps>
    & JSX.IntrinsicClassAttributes<InputMask> & Readonly<InputMaskProps>
    & FieldHookConfig<string> & FieldProps): JSX.Element => {
    const [field, meta] = useField(props)
    const {setFieldValue} = useFormikContext<{ [key: string]: any }>()

    const onChange = (e: React.ChangeEvent<HTMLInputElement> & InputMaskChangeParams) => {
        props.onFieldChange ? props.onFieldChange!(field.name, e.target?.value) : setFieldValue(field.name, e.target?.value)
    }

    const getValue = () => {
        return props.value ? props.value : field.value ? field.value : ''
    }

    return (
        <div className="flex flex-col gap-1">
            <label htmlFor={field.name} className="font-bold">{props.label}</label>
            <InputText aria-describedby={field.name + '-help'} {...field} {...props} value={getValue()} onChange={onChange}/>
            {props.showHelp &&
                <small id={field.name + '-help'}>
                    Введите {props.label}.
                </small>
            }
        </div>
    )
}