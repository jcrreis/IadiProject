import {LOGIN_USER} from "./consts"
import {UserLoginI} from "../DTOs";
import {UserLoginAction} from "./types";


export function userLogin(user: UserLoginI) {
    const action: UserLoginAction = {
        type: LOGIN_USER,
        user,
    }
}