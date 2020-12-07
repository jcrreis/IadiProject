import { IStateStore, UserLoginAction} from "./types";
import {LOGIN_USER} from "./consts";
import {UserLoginI} from "../DTOs";

const InitialState: IStateStore = {
    user: undefined,
    counter: 0,
    institutions: []
}

const reducer = (
  state: IStateStore = InitialState,
  action: UserLoginAction
): IStateStore => {
    switch (action.type) {
        case LOGIN_USER:
            const user: UserLoginI = action.user
            console.log("O USER")
            console.log(action.user)
            return {
                ...state,
                user: user
            }
    }
    return state
}

export default reducer