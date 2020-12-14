export interface StudentI{
    id: number
    name: string
    email: string
    password: string
    institution: InstitutionI
    cv: CurriculumI
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

export interface UserLoginI{
    id: number,
    name: string,
    email: string,
    address: string,
    type: string,
    token: string
}


export interface GrantCallI{
    id: number,
    title: string,
    description: string,
    requirements: string,
    funding: number,
    openingDate: Date,
    closingDate: Date,
    dataItems: DataItemI[],
    sponsorId: number,
    evaluationPanelId: number,
    applications: number[]
}

export interface DataItemI{
    mandatory: boolean,
    name: string,
    datatype: string
}


export interface ApplicationI{
    id: number,
    submissionDate: Date,
    status: number,
    decision: boolean,
    grantCallId: number,
    studentId: number,
    reviews: number[],
    meanScores: number,
    answers: string[],
    justification: string
}

export interface ReviewI{
    id: number,
    applicationId: number,
    reviewerId: number,
    score: number,
    observations: string
}

export interface CurriculumI{
    id: number,
    items: CvItemI[]
}

export interface CvItemI{
    id: number,
    item: string,
    answer: string
}
