package com.example.iadiproject.services


import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class ReviewService(val reviews : ReviewRepository, val ePanels: EvaluationPanelRepository, val applications: ApplicationRepository) {

    fun getAll() : Iterable<ReviewDAO> = reviews.findAll()

    fun getOne(id: Long): ReviewDAO = reviews.findById(id).orElseThrow() {
        NotFoundException("Review with id $id not found.")
    }

    fun addOne(review: ReviewDAO){
        val application: ApplicationDAO = review.application
        val ePanel: EvaluationPanelDAO = ePanels.findEvaluationPanelDAOByGrantCallId(application.grantCall.id)
        if(ePanel.reviewers.contains(review.reviewer) || (ePanel.panelchair?.equals(review.reviewer) == true)){
            review.id = 0
            reviews.save(review)
        }
        else{
            throw ConflictException("This reviewer with ${review.reviewer.id} id doesn't belong to Evaluation Panel with id ${ePanel.id}")
        }

    }

}