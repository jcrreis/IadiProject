import {GrantCallI, InstitutionI, UserLoginI} from "../DTOs";

export type UserLoginAction = {
    type: string
    user: UserLoginI
}

export type IStateStore = {
    user: UserLoginI | undefined,
    institutions: InstitutionI[],
    grantCalls: GrantCallI[]
}