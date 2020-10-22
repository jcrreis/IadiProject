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
}