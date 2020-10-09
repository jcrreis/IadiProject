package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/reviews")
class ReviewController{
    var dataitems = listOf(DataItemDTO("Number",true),DataItemDTO("Number",true))
    var institution = InstitutionDTO(1, "FCT","fct@gmail.com")
    var student = StudentDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution,"cv")
    var call = GrantCallDTO(1,"ERASMUS","FCT","Something",300,Date(12/12/12), Date(12,12,12),dataitems)
    var application = ApplicationDTO(1, student,call,listOf())
    var reviewer = ReviewerDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution)




    @GetMapping("")
    fun getAllReviews(): List<ReviewDTO> =  listOf(ReviewDTO(1,application,reviewer,1,"Observation..."),ReviewDTO(1,application,reviewer,5,"Observation..."))

    @GetMapping("/{id}")
    fun getOneReview(@PathVariable id: Number) = ReviewDTO(1,application,reviewer,1,"Observation...")

    @PostMapping("")
    fun createNewReview(): String = "created"

}