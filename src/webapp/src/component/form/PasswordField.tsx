import {FieldHookConfig, useField, useFormikContext} from "formik";
import React from "react";
import {Password, PasswordProps} from "primereact/password";
import {FieldProps} from "$src/component/form/TextField";

export const PasswordField = (props: JSX.IntrinsicClassAttributes<Password> & Readonly<PasswordProps> & FieldHookConfig<string> & FieldProps): JSX.Element => {
    const [field, meta] = useField(props);
    const {setFieldValue} = useFormikContext<{ [key: string]: any }>()

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        props.onFieldChange ? props.onFieldChange!(field.name, e.target?.value) : setFieldValue(field.name, e.target?.value)
    }

    const getValue = () => {
        return props.value ? props.value : field.value ? field.value : ''
    }

    return (
        <div className="flex flex-col gap-1">
            <label htmlFor={field.name} className="font-bold">{props.label}</label>
            <Password {...field} {...props} name={props.name} required={false} value={getValue()} onChange={onChange}/>
            {props.showHelp &&
                <small id={field.name + '-help'}>
                    Введите {props.label}.
                </small>
            }
        </div>
    )
}