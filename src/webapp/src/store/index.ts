import {Environment} from "$config/env"
import {configApi} from "$src/client/api/configApi";
import {authApi} from "$src/client/api/authApi";
import applicationStateReducers from "$store/applicationStateReducers";
import {configureStore} from "@reduxjs/toolkit";

export const store = configureStore({
    reducer: {
        applicationState: applicationStateReducers,
        [configApi.reducerPath]: configApi.reducer,
        [authApi.reducerPath]: authApi.reducer
    },

    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([
        configApi.middleware,
        authApi.middleware
    ]),

    devTools: process.env.NODE_ENV === Environment.DEV
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
type SliceAction<T> = { [K in keyof T]: T[K] extends (...args: any[]) => infer A ? A : never }[keyof T]
export type ActionType<TypeOfSliceActionTypes> = SliceAction<TypeOfSliceActionTypes>
