package com.example.iadiproject.api

import java.util.*


data class ApplicationDTO(
        val id: Long,
        val submissionDate: Date,
        val status: Int,
        val decision: Boolean,
        val justification: String
)

open class EntityDTO(
        open val id:Long,
        open val name: String,
        open val contact: String
)

data class InstitutionDTO(
       override val id: Long,
       override val name: String,
       override val contact: String,
       val students: List<SimpleStudentDTO>
       //val reviewers: List<SimpleReviewerDTO>
) : EntityDTO(id,name,contact)


data class SimpleInstitutionDTO(
        val id: Long,
        val name: String,
        val contact: String
)

data class DataItemDTO(
        val name: String,
        val type: String,
        val mandatory: Boolean
)

data class GrantCallDTO(
        val id: Long,
        val title : String,
        val description : String,
        val requirements: String,
        val funding: Double,
        val openingDate: Date,
        val closingDate: Date,
        val dataItems: String,
        val sponsorId: Long,
        val evaluationPanelId: Long
)

open class UserDTO(
        open val id: Number,
        open val name : String,
        open val email : String,
        open val address: String,
        open val institution: SimpleInstitutionDTO
)

data class StudentCreateDTO(
    val studentDTO: StudentDTO,
    val password: String

)

data class StudentDTO(
        override val id: Long,
        override val name: String,
        override val email: String,
        override val address: String,
        override val institution: SimpleInstitutionDTO,
        val cv: String
) : UserDTO(id,name,email,address,institution)

data class SimpleStudentDTO(
        val id: Long,
        val name: String,
        val email: String,
        val address: String
)

data class AddStudentDTO(
        val id: Long,
        val name: String,
        val email: String,
        val address: String,
        val institutionId: Long,
        val cv: String
)

data class ReviewerDTO(
        override val id: Long,
        override val name: String,
        override val email: String,
        override val address: String,
        override val institution: SimpleInstitutionDTO
) : UserDTO(id,name,email,address,institution)


data class SimpleReviewerDTO(
        val id: Long,
        val name: String,
        val email: String,
        val address: String
)


data class AddUserDTO(
        val id: Long,
        val name: String,
        val password: String,
        val email: String,
        val address: String,
        val institutionId: Long
)

data class  SponsorDTO(
        override val id: Long,
        override val name: String,
        override val contact: String,
        val grantCalls: List<GrantCallDTO>
) : EntityDTO(id,name,contact)

data class AddSponsorDTO(
        val id: Long,
        val name: String,
        val contact: String
)



data class EvaluationPanelDTO(
        val id: Long,
        val reviewers: List<SimpleReviewerDTO>,
        //val panelchair: ReviewerDTO,
        //val winner: ApplicationDTO,
        val grantCallId: Long
)

data class ReviewDTO(
        val id: Number,
        val application: ApplicationDTO,
        val reviewer: ReviewerDTO,
        val score: Int,
        val observations: String
)


