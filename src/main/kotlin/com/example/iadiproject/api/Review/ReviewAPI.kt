package com.example.iadiproject.api.EvaluationPanel

import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.ReviewDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="reviews", description = "'Reviews' management operations")
@RequestMapping("/reviews")
interface ReviewAPI {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get the list of all reviews")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of reviews"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<ReviewDTO>

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get a review by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the review"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT_FOUND")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ReviewDTO

    @PreAuthorize("hasAuthority('ROLE_REVIEWER')")
    @ApiOperation("Create a new review")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a review"),
        ApiResponse(code = 403, message = "UNAUTHORIZED"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody review: ReviewDTO){

    }

}