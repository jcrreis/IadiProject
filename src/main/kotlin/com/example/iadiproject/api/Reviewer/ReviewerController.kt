package com.example.iadiproject.api.Reviewer

import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.api.ReviewerDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import com.example.iadiproject.api.Student.ReviewerAPI
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*

@RestController
class ReviewerController (val reviewers: ReviewerService, val institutions: InstitutionService, val ePanels: EvaluationPanelService): ReviewerAPI{



    override fun getAll(): List<ReviewerDTO> = reviewers.getAll().map {
        ReviewerDTO(it.id,it.name,it.email,it.address, SimpleInstitutionDTO(it.institution.id,it.institution.name,it.institution.contact))
    }

    override fun getOne(id: Long): ReviewerDTO = reviewers.getOne(id).let {
        ReviewerDTO(it.id,it.name,it.email,it.address, SimpleInstitutionDTO(it.institution.id,it.institution.name,it.institution.contact))
    }

    override fun addOne(reviewer: AddUserDTO) {
        reviewers.addOne(ReviewerDAO(reviewer.id,reviewer.name,reviewer.password,reviewer.email,reviewer.address,
                institutions.getOne(reviewer.institutionId)))
    }



}