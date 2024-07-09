import React, {useEffect, useState} from "react"
import {addLocale, locale} from "primereact/api";
import {Header} from "$src/component/Header";
import {AppContext, Application, CSDEEP_ADMIN} from "$src/AppContext"
import dayjs, {extend} from "dayjs"
import duration from "dayjs/plugin/duration"
import {useAppWindowBackgroundQuery, useHelloMutation} from "$src/client/api/configApi";
import LoginUser from "$src/model/User";
import * as AuthService from "$src/client/api/authApi";
import ErrorMessageDialog from "$src/component/ErrorMessageDialog";
import {LeftMenu} from "$src/component/LeftMenu";

extend(duration)

export const dialogHeader = (title?: string): JSX.Element => <Header label={title} isDialog/>
export const header = (title?: string, onClose?: () => void): JSX.Element => <Header label={title} onClose={onClose}/>

const App = (props: Application & {
    children: React.ReactNode
}) => {
    const [loginUser, setLoginUser] = useState<LoginUser | undefined>(undefined);
    const {data: appWindowBackground} = useAppWindowBackgroundQuery()

    addLocale('ru', {
        firstDayOfWeek: 1,
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['янв', 'фев', 'мар', 'апр', 'май', 'июн', 'июл', 'авг', 'сен', 'окт', 'ноя', 'дек'],
        today: 'Сегодня',
        clear: 'Очистить'
    })
    locale('ru')

    useEffect(() => {
        document.title = props.title
    }, [])

    const logOut = () => {
        AuthService.logout()
        setLoginUser(undefined)
        window.location.replace(CSDEEP_ADMIN.linkTo)
        window.location.reload()
    };

    const [heartbeat] = useHelloMutation()
    useEffect(() => {
        const loginInfo = AuthService.getCurrentUser()

        if (loginInfo) {
            setLoginUser(loginInfo)
        }

        const heartBeatId =
            setInterval(() => {
                    const loginInfoTest = AuthService.getCurrentUser()
                    if (loginInfoTest)
                        heartbeat()
                    else if (loginInfo) {
                        logOut()
                    }
                },
                dayjs.duration({minutes: 5}).asMilliseconds())
        return () => {
            clearInterval(heartBeatId)
        }
    }, [])

    return (
        <AppContext.Provider value={{...props}}>
            <div className="app-screen-background flex row h-screen" style={{background: appWindowBackground}}>
                {loginUser ? (
                    <>
                        <LeftMenu logout={logOut} activeItem={props.code}/>
                    </>
                ) : ''}
                <div className="flex flex-col w-full overflow-y-scroll">
                    <div className="h-screen">
                        {props.children}
                    </div>
                </div>
                <ErrorMessageDialog/>
            </div>
        </AppContext.Provider>
    )
}

export default App
