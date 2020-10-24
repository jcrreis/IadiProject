package com.example.iadiproject.services


import com.example.iadiproject.model.InstitutionRepository
import com.example.iadiproject.model.ReviewerRepository
import org.springframework.stereotype.Service

@Service
class ReviewerService(val reviewers : ReviewerRepository, val institutions: InstitutionRepository) {

    fun getAll() : Iterable<ReviewerDAO> = reviewers.findAll()

    fun getOne(id: Long): ReviewerDAO = reviewers.findById(id).orElseThrow() {
        NotFoundException("Application with id $id not found.")
    }

    fun addOne(reviewer: ReviewerDAO){
        reviewer.id = 0
        reviewers.save(reviewer)

    }

    fun addPanelToReviewer(reviewerDAO: ReviewerDAO, epanel: EvaluationPanelDAO){
        reviewerDAO.evaluationPanels.add(epanel)
        reviewers.save(reviewerDAO)

    }

    fun addPanelChairToReviewer(reviewerDAO: ReviewerDAO, epanel: EvaluationPanelDAO){
        reviewerDAO.panelsChairs.add(epanel)
        reviewers.save(reviewerDAO)

    }
}