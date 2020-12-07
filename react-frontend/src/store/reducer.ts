import { IStateStore, UserLoginAction} from "./types";
import {LOGIN_USER} from "./consts";
import {UserLoginI} from "../DTOs";

const InitialState: IStateStore = {
    user: undefined,
    counter: 0
}

const reducer = (
  state: IStateStore = InitialState,
  action: UserLoginAction
): IStateStore => {
    switch (action.type) {
        case LOGIN_USER:
            const user: UserLoginI = {
                id: action.user.id, // not really unique
                name: action.user.name,
                address: action.user.address,
                email: action.user.email,
                type: action.user.type
            }
            return {
                ...state,
                user: user
            }
    }
    return state
}

export default reducer