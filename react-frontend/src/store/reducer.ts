import { IStateStore, UserLoginAction} from "./types";
import {LOGIN_USER} from "./consts";
import {GrantCallI, InstitutionI, UserLoginI} from "../DTOs";
import axios from 'axios'



function fetchInstitutions(): InstitutionI[]{
    let institutions: InstitutionI[] = []
    axios.get('/institutions').then((r:any) => {

        r.data.forEach( (institution: InstitutionI) => {
            institutions.push(institution)
        })
    })
    return institutions
}

function fetchGrantCalls(): GrantCallI[]{
    let grantCalls: GrantCallI[] = []
    axios.get('/grantcalls').then((r:any) => {
        r.data.forEach( (grantCall: GrantCallI) => {
            grantCall.openingDate = new Date(grantCall.openingDate)
            grantCall.closingDate = new Date(grantCall.closingDate)
            grantCalls.push(grantCall)
        })
    })
    return grantCalls
}

const InitialState: IStateStore = {
    user: undefined,
    counter: 0,
    institutions: fetchInstitutions(),
    grantCalls: fetchGrantCalls()
}

const reducer = (
  state: IStateStore = InitialState,
  action: UserLoginAction
): IStateStore => {
    switch (action.type) {
        case LOGIN_USER:
            const user: UserLoginI = action.user
            console.log(action.user)
            return {
                ...state,
                user: user
            }
    }
    return state
}

export default reducer