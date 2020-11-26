export interface StudentI{
    id: number
    name: string
    email: string
    password: string
    institution: InstitutionI
    cv: string
}

export interface InstitutionI{
    id: number
    name: string
    contact: string
}

export interface ReviewerI{
    id: number
    name: string
    email: string
    password: string
    institution: InstitutionI
}

export interface SponsorI{
    id: number
    name: string
    email: string
    contact: string

}

export interface AddUserI{
    id: number
    name: string
    password: string
    email: string
    address: string
    institutionId: number
    contact: string,
    type: string
}

