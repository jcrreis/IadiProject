package com.example.iadiproject.api


import java.util.*



data class ApplicationDTO(
        val id: Long,
        val submissionDate: Date,
        val status: Int,
        val decision: Boolean,
        val justification: String,
        val grantCallId: Long,
        val studentId: Long,
        val reviews: List<Long>,
        val meanScores: Double,
        val answers: List<String>
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
       val users: List<Long>
       //val reviewers: List<SimpleReviewerDTO>
) : EntityDTO(id,name,contact)


data class SimpleInstitutionDTO(
        val id: Long,
        val name: String,
        val contact: String
)



data class GrantCallDTO(
        val id: Long,
        val title : String,
        val description : String,
        val requirements: String,
        val funding: Double,
        val openingDate: Date,
        val closingDate: Date,
        val dataItems: List<DataItemDTO>,
        val sponsorId: Long,
        val evaluationPanelId: Long
)

open class UserDTO(
        open val id: Number,
        open val name : String,
        open val email : String,
        open val address: String
)



data class StudentDTO(
        override val id: Long,
        override val name: String,
        override val email: String,
        override val address: String,
        val institution: SimpleInstitutionDTO,
        val password: String,
        val cv: ByteArray
) : UserDTO(id,name,email,address)



data class ReviewerDTO(
        override val id: Long,
        override val name: String,
        override val email: String,
        override val address: String,
        val institution: SimpleInstitutionDTO,
        val ePanels: List<Long>,
        val panelchairs: List<Long>,
        val reviews: List<Long>
) : UserDTO(id,name,email,address)


data class SimpleReviewerDTO(
        val id: Long,
        val name: String,
        val email: String,
        val address: String
)


data class AddUserDTO(
        val id: Long,
        val name: String,
        var password: String,
        val email: String,
        val address: String,
        val institutionId: Long,
        val contact: String,
        val type: String
){
    constructor(): this(1L,"","","","",1L,"",""){

    }
}

data class  SponsorDTO(
        override val id: Long,
        override val name: String,
        override val contact: String,
        val grantCalls: List<GrantCallDTO>
) : EntityDTO(id,name,contact)


data class EvaluationPanelDTO(
        val id: Long,
        val reviewers: List<SimpleReviewerDTO>,
        val panelChairId: Long?,
        //val winner: ApplicationDTO,
        val grantCallId: Long
)

data class ReviewDTO(
        val id: Long,
        val applicationId: Long,
        val reviewerId: Long,
        val score: Int,
        val observations: String

)

data class ChangePasswordDTO(
        val oldpassword: String,
        val newpassword: String
)

data class DataItemDTO(
        val mandatory: Boolean,
        val name: String,
        val datatype: String

)


data class UserSignInDTO(
        val name: String,
        val password: String
){
    constructor(): this("",""){

    }
}


