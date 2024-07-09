export type DataBaseSetting = {
    serveName: String
    databaseName: String
}

export type ApplicationSetting = {
    dataBaseSetting?: DataBaseSetting
}

export interface ErrorMessageResponse {
    timestamp?: string
    status?: number
    error?: string
    exception?: string
    trace?: string
    message?: string
    path?: string
}