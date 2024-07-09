import React from "react"

export interface Application {
    code: string
    name: string
    title: string
    titleWithVersion: string
    icon: string
    comment: string
    description: string
    linkTo: string
}

export const CSDEEP_APP_VERSION = "1.0"
export const CSDEEP_MAIN: Application = {
    code: 'CSDEEP_MAIN',
    name: "CSDEEP",
    title: "CSDEEP",
    titleWithVersion: `CSDEEP [${CSDEEP_APP_VERSION}]`,
    icon: "",
    comment: "",
    description: "",
    linkTo: ""
}

export const CSDEEP_ADMIN: Application = {
    code: 'CSDEEP_ADMIN',
    name: "CSDEEP_ADMIN",
    title: "CSDEEP - Авторизация",
    titleWithVersion: `CSDEEP - Авторизация [${CSDEEP_APP_VERSION}]`,
    icon: "",
    comment: "",
    description: "CSDEEP - Авторизация",
    linkTo: "#/admin"
}

export const CSDEEP_ADMIN_VERSION = CSDEEP_APP_VERSION
export const CSDEEP_ADMIN_INFO: Application = {
    code: "CSDEEP_ADMIN_INFO",
    name: "CSDEEP_ADMIN_INFO",
    title: "CSDEEP - Панель администрирования - Общая информация",
    titleWithVersion: `CSDEEP - Панель администрирования - Общая информация [${CSDEEP_ADMIN_VERSION}]`,
    icon: '/MZO_Icon.png',
    comment: "CSDEEP - Панель администрирования - Общая информация",
    description: "CSDEEP - Панель администрирования - Общая информация",
    linkTo: "#/admin/info"
}

export const APPLICATIONS = [
    CSDEEP_ADMIN, CSDEEP_ADMIN_INFO,
    CSDEEP_MAIN
]

export const AppContext = React.createContext<Application | undefined>(undefined)