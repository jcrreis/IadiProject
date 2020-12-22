import {IStateStore, UserAction} from "./types";
import {LOGIN_USER, LOGOUT_USER} from "./consts";
import {GrantCallI, InstitutionI, UserLoginI} from "../DTOs";
import axios, {AxiosResponse} from 'axios'
import {store} from "../index";



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

export function fetchUserFromStorage(){
    let user: UserLoginI
    let userJson: string | null = localStorage.getItem('LOGIN_USER')

    axios.get('/users/current').then((r: AxiosResponse) => {
        if(userJson == null)
            return undefined
        user = JSON.parse(userJson)
        store.dispatch({type: LOGIN_USER,user: user})
    })
    store.dispatch({type: LOGIN_USER,user: undefined})
}

function cleanUserFromStorage(key: string) {
    localStorage.removeItem(key);
}

const InitialState: IStateStore = {
    user: undefined,
    institutions: fetchInstitutions(),
    grantCalls: fetchGrantCalls()
}

const reducer = (
  state: IStateStore = InitialState,
  action: UserAction
): IStateStore => {
    switch (action.type) {
        case LOGIN_USER:
            const user: UserLoginI = action.user
            return {
                ...state,
                user: user
            }
        case LOGOUT_USER:
            cleanUserFromStorage("LOGIN_USER")
            return{
                ...state,
                user: action.user
            }
    }
    return state
}

export default reducer