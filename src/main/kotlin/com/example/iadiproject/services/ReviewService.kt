package com.example.iadiproject.services


import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class ReviewService(val reviews : ReviewRepository, val ePanels: EvaluationPanelRepository, val applications: ApplicationRepository, val reviewers: ReviewerRepository) {

    fun getAll() : Iterable<ReviewDAO> = reviews.findAll()

    fun getOne(id: Long): ReviewDAO = reviews.findById(id).orElseThrow() {
        NotFoundException("Review with id $id not found.")
    }

    fun addOne(review: ReviewDAO){
        val application: ApplicationDAO = review.application
        val ePanel: EvaluationPanelDAO = this.ePanels.findEvaluationPanelDAOByGrantCallId(application.grantCall.id)
        val reviews: List<ReviewDAO> = application.reviews

        for(r in reviews) {
            if(r.reviewer.id == review.reviewer.id)
                throw ConflictException("This reviewer with ${review.reviewer.id} id already did a review for this application with id ${application.id}")
        }


        if(ePanel.reviewers.contains(review.reviewer) || (ePanel.panelchair?.equals(review.reviewer) == true)){
            review.id = 0
            this.reviews.save(review)
            application.updateMeanScores()
            applications.save(application)
        }
        else{
            throw ForbiddenException("This reviewer with ${review.reviewer.id} id doesn't belong to Evaluation Panel with id ${ePanel.id}")
        }

    }

}