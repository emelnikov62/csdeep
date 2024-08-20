import React from "react"
import {createRoot} from "react-dom/client"
import "$src/index.css"
import "$config/log"
import {Provider} from "react-redux"
import {store} from "$src/store"
import * as serviceWorkerRegistration from "$src/serviceWorkerRegistration"
import reportWebVitals from "$src/reportWebVitals"
import {Environment} from "$config/env"
import {HashRouter, Navigate, Route, Routes} from "react-router-dom"
import {AdminInfoApp} from "$src/apps/Admin/Info/AdminInfoApp";
import {AdminStartPane} from "$src/apps/Admin/AdminStartPane";
import {MainPage} from "$src/apps/Main/MainPage";
import SteamLogin from "$src/component/pages/steam/login"
import SteamRedirect from "$src/component/pages/steam/redirect"

const root = createRoot(document.getElementById("root") as HTMLElement)
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <HashRouter>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/admin" element={<AdminStartPane/>}/>
                    <Route path="/admin/info" element={<AdminInfoApp/>}/>
                    <Route path="/steam/login" element={<SteamLogin/>}/>
                    <Route path="/steam/login/redirect" element={<SteamRedirect/>}/>
                    <Route path="*" element={<Navigate replace to="/"/>}/>
                </Routes>
            </HashRouter>
        </Provider>
    </React.StrictMode>
)

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.register()

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
if (process.env.NODE_ENV === Environment.DEV)
    reportWebVitals()
