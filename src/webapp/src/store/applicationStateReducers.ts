import {createSlice, isRejected, SliceCaseReducers} from "@reduxjs/toolkit"
import {ActionType} from "$store/index";
import {ErrorMessageResponse} from "$src/model/ApplicationSetting";
import {useAppSelector} from "$store/useAppSelector";
import {getCurrentDate} from "$utils/formatDate";
import {FetchBaseQueryError} from "@reduxjs/toolkit/query";

export interface ApplicationState {
    executeResponse: ErrorMessageResponse;
}

const initialApplicationState: ApplicationState = {
    executeResponse: {
        timestamp: undefined,
        status: 200,
        error: "",
        exception: "",
        trace: "",
        message: "",
        path: ""
    }
};

const applicationStateSlice = createSlice<ApplicationState, SliceCaseReducers<ApplicationState>>({
    name: "applicationState",
    initialState: initialApplicationState,
    reducers: {
        reset: (state) => {
            state.executeResponse = initialApplicationState.executeResponse
        }
    },
    extraReducers: builder => {
        // Добавляем общий обработчик для rejected стадии любого асинхронного действия с помощью хелпер-функции isRejected
        builder.addMatcher<FetchBaseQueryError>(isRejected, (state, action) => {
            const payload = action.payload
            if (payload) {
                if (payload?.data) {
                    try {
                        state.executeResponse = (JSON.parse(payload.data) as ErrorMessageResponse)
                    } catch (e) {
                        state.executeResponse = (payload.data as ErrorMessageResponse)
                    }
                    state.executeResponse.path = action.meta.baseQueryMeta.request.url
                } else {
                    state.executeResponse.message = "Неизвестная ошибка!"
                    state.executeResponse.status = payload.status
                    state.executeResponse.timestamp = getCurrentDate().toString()
                }
            }
        })
    }
})

type ApplicationStateAction = ActionType<typeof applicationStateSlice.actions>
export const useApplicationState = () => useAppSelector(state => state.applicationState)
export default applicationStateSlice.reducer
export const applicationStateActions = {
    reset: (): ApplicationStateAction => applicationStateSlice.actions.reset(undefined)
}
