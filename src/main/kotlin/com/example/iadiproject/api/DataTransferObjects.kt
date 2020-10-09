package com.example.iadiproject.api

import java.util.*


data class ApplicationDTO(val id:Number, val student: StudentDTO, val call: GrantCallDTO, val reviews: List<ReviewDTO>)

open class EntityDTO(open val id:Number,open val name: String,open val contact: String)

data class InstitutionDTO(
       override val id: Number,
       override val name: String,
       override val contact: String
) : EntityDTO(id,name,contact)

data class  SponsorDTO(
        override val id: Number,
        override val name: String,
        override val contact: String
) : EntityDTO(id,name,contact)

data class DataItemDTO(val type: String, val mandatory: Boolean)

data class GrantCallDTO(val id: Number, val title : String,val description : String, val requirements: String,
                        val funding: Number,val openingDate: Date, val closingDate: Date,
                        val dataItems: List<DataItemDTO>
)

open class UserDTO(open val id: Number, open val name : String,open val email : String,open val address: String,open val institution: InstitutionDTO)

data class StudentDTO(
        override val id: Number,
        override val name: String,
        override val email: String,
        override val address: String,
        override val institution: InstitutionDTO,
        val cv: String
) : UserDTO(id,name,email,address,institution)

data class ReviewerDTO(
        override val id: Number,
        override val name: String,
        override val email: String,
        override val address: String,
        override val institution: InstitutionDTO
) : UserDTO(id,name,email,address,institution)

data class EvaluationPanelDTO(val id: Number, val reviewers: List<ReviewerDTO>, val panelchair: ReviewerDTO, val winner: ApplicationDTO, val sponsor: SponsorDTO)

data class ReviewDTO(val id: Number,  val application: ApplicationDTO, val reviewer: ReviewerDTO, val score: Int, val observations: String)


