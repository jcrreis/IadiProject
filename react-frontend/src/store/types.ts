import {GrantCallI, InstitutionI, UserLoginI} from "../DTOs";

export type UserLoginAction = {
    type: string
    user: UserLoginI
}

export type IStateStore = {
    user: UserLoginI | undefined,
    counter: number,
    institutions: InstitutionI[],
    grantCalls: GrantCallI[]
}