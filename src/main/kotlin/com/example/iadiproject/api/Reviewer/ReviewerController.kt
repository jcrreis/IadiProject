package com.example.iadiproject.api.Reviewer

import com.example.iadiproject.api.ReviewerDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import com.example.iadiproject.api.Student.ReviewerAPI
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*

@RestController
class ReviewerController (val reviewers: ReviewerService, val institutions: InstitutionService, val users: UserService): ReviewerAPI{

    fun transformDAOIntoDTO(it: ReviewerDAO): ReviewerDTO{
        return ReviewerDTO(it.id,it.name,it.email,it.address, SimpleInstitutionDTO(it.institution.id,it.institution.name,
                it.institution.contact),it.evaluationPanels.map{ it1 -> it1.id },it.panelsChairs.map{ it2 -> it2.id },it.reviews.map{
                it3 -> it3.id
        })
    }

    override fun getAll(): List<ReviewerDTO> = reviewers.getAll().map { transformDAOIntoDTO(it) }

    override fun getOne(id: Long): ReviewerDTO = transformDAOIntoDTO(reviewers.getOne(id))

    override fun getApplicationsReviewed(id: Long): List<Long> = reviewers.getAllApplicationsReviewed(id).map{it.id}


}