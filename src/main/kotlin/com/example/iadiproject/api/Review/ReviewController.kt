package com.example.iadiproject.api.Review

import com.example.iadiproject.api.*
import com.example.iadiproject.model.ApplicationDAO
import com.example.iadiproject.model.ReviewDAO
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.services.*
import org.springframework.security.core.context.SecurityContextHolder
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
        val reviewer: ReviewerDAO = reviewers.getReviewerByName(SecurityContextHolder.getContext().authentication.name)
        print("application id: " + review.applicationId)
        val application: ApplicationDAO =  applications.getOne(review.applicationId)
        reviews.addOne(ReviewDAO(review.id,application,reviewer,review.score,review.observations))
    }





}