import { IStateStore, UserLoginAction} from "./types";
import {LOGIN_USER} from "./consts";
import {InstitutionI, UserLoginI} from "../DTOs";
import axios from 'axios'



function fetchInstitutions(): InstitutionI[]{
    let institutions: InstitutionI[] = []
    axios.get('/institutions').then(r => {

        r.data.forEach( (institution: InstitutionI) => {
            institutions.push(institution)
        })
    })
    return institutions
}

const InitialState: IStateStore = {
    user: undefined,
    counter: 0,
    institutions: fetchInstitutions()
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