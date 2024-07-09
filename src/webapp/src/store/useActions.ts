import {bindActionCreators} from "redux"
import {useAppDispatch} from "$store/useAppDispatch";

export const useActions = () => bindActionCreators({}, useAppDispatch())
