import {Application} from "$src/AppContext";

export type User = {
    id: number
    profile: string
    name: string
    surname: string
    lastname: string
    active: boolean
    email: string
    idClient: number
    ldts: string
}

export type Authority = {
    id: number
    code: string
}

export type ApplicationAuthority = {
    [code: string]: boolean
}

export default interface LoginUser {
    username: string,
    password: string,
    roles?: Array<string>
}

export type UserInformation = {
    user: User
    userAuthority?: Array<Authority>
    applicationAuthority?: ApplicationAuthority
}

export const hasRoleByCodes = (user: UserInformation | undefined, roleCodes: String[], or = true): boolean => {
    if (!user)
        return false;

    let result = false;
    for (const rc of roleCodes) {
        result = hasRoleByCode(user, rc)
        if (or && result)
            break;
        else if (!or && !result)
            break;
    }
    return result;
}
export const hasRoleByCode = (user: UserInformation | undefined, roleCode: String): boolean => {
    return !!user && !!user.userAuthority?.find((ua) => ua.code == roleCode);

}

export const isApplicationPermitted = (user: UserInformation, application: Application): boolean => {
    return user.applicationAuthority ? user.applicationAuthority[application.code] : true
}
