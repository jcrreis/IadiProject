package com.example.iadiproject.api.EvaluationPanel

import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.ReviewDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="reviews", description = "'Reviews' management operations")
@RequestMapping("/reviews")
interface ReviewAPI {

    @GetMapping("")
    fun getAll(): List<ReviewDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ReviewDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody review: ReviewDTO){

    }

}