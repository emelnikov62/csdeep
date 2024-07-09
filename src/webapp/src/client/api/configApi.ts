import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import * as AuthService from "$src/client/api/authApi";
import {ApplicationSetting} from "$src/model/ApplicationSetting";

export const configApi = createApi({
    reducerPath: "configApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/services/api/config",
        prepareHeaders: (headers) => {
            const user = AuthService.authHeader()
            if (user && user.accessToken) {
                // include token in req header
                headers.set('Authorization', `Bearer ${user.accessToken}`)
                return headers
            } else
                return headers.set('Authorization', '')
        },
    }),
    tagTypes: ["ApplicationSetting"],
    endpoints: (builder) => ({
        appSetting: builder.query<ApplicationSetting, void>({
            query: () => ({
                url: "/appSetting",
                method: "GET"
            }),
            providesTags: ["ApplicationSetting"]
        }),
        appWindowBackground: builder.query<string, void>({
            query: () => ({
                url: "/appWindowBackground",
                method: "GET",
                responseHandler: "text"
            })
        }),
        hello: builder.mutation<string, void>({
            query: () => ({
                url: "/hello",
                method: "GET",
                responseHandler: "text"
            })
        })
    })
})

export const {useAppSettingQuery, useAppWindowBackgroundQuery, useHelloMutation} = configApi