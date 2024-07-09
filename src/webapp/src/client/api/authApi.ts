import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {UserInformation} from "$src/model/User";

export interface JwtResponse {
    accessToken?: string
    username?: string
    email?: string
    roles?: string[]
    message?: string
    code?: number
    tokenType?: string
    userInformation?: UserInformation
}

export interface LoginParam {
    username: string
    password: string
}

export const authApi = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/services/api/auth",
        prepareHeaders: (headers) => {
            const user = authHeader()
            if (user && user.accessToken) {
                // include token in req header
                headers.set('Authorization', `Bearer ${user.accessToken}`)
                return headers
            } else
                return headers.set('Authorization', '')
        },
    }),
    tagTypes: ["JwtResponse", "UserInformation"],
    endpoints: (builder) => ({
        signIn: builder.mutation<JwtResponse, LoginParam>({
            query: (param: LoginParam) => ({
                url: "/sign-in",
                method: "POST",
                body: param
            }),
        }),
        loginInfo: builder.query<UserInformation, void>({
            query: () => ({
                url: "/login-info",
                method: "GET"
            }),
            providesTags: ["UserInformation"]
        }),
    })
})

export const {useSignInMutation, useLoginInfoQuery} = authApi

export const useLogin = (response: any) => {
    if (response && response?.accessToken) {
        localStorage.setItem("user", JSON.stringify(response));
        return response;
    }
    return undefined
};

export const logout = () => {
    localStorage.removeItem("user");
};

export const parseJwt = (token: any) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
};

export const getCurrentUser = () => {
    const userStr = localStorage.getItem("user");
    if (userStr) {
        const user = JSON.parse(userStr);
        const decodedJwt = parseJwt(user.accessToken);

        if (!decodedJwt || decodedJwt.exp * 1000 < Date.now()) {
            return null;
        }
        return user;
    }
    return null;
};

export const authHeader = () => {
    const userStr = localStorage.getItem("user");
    let user = null;
    if (userStr)
        user = JSON.parse(userStr);

    return user;
}