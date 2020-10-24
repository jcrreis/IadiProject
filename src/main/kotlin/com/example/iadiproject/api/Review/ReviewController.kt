package com.example.iadiproject.api.Review

import com.example.iadiproject.api.*
import com.example.iadiproject.api.EvaluationPanel.ReviewAPI
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*


@RestController
class ReviewController(val reviews: ReviewService, val applications: ApplicationService, val reviewers: ReviewerService) : ReviewAPI {

    override fun getAll(): List<ReviewDTO> = reviews.getAll().map{
        ReviewDTO(it.id,it.application.id,it.reviewer.id,it.score,it.observations)
    }

    override fun getOne(id: Long): ReviewDTO = reviews.getOne(id).let{
        ReviewDTO(it.id,it.application.id,it.reviewer.id,it.score,it.observations)
    }

    override fun addOne(review: ReviewDTO) {
        val application: ApplicationDAO =  applications.getOne(review.applicationId)
        val reviewer: ReviewerDAO = reviewers.getOne(review.reviewerId)
        reviews.addOne(ReviewDAO(review.id,application,reviewer,review.score,review.observations))
    }


}