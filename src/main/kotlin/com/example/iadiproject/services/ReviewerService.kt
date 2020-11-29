package com.example.iadiproject.services


import com.example.iadiproject.model.EvaluationPanelDAO
import com.example.iadiproject.model.InstitutionRepository
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.model.ReviewerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ReviewerService(val users: UserService,val reviewers : ReviewerRepository, val institutions: InstitutionRepository) {

    fun getAll() : Iterable<ReviewerDAO> = reviewers.findAll()

    fun getOne(id: Long): ReviewerDAO = reviewers.findById(id).orElseThrow() {
        NotFoundException("Reviewer with id $id not found.")
    }

    fun getReviewerByName(name: String): ReviewerDAO = reviewers.findReviewerDAOByName(name).orElseThrow(){
        NotFoundException("Reviewer with id $name not found.")
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