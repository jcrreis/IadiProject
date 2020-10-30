package com.example.iadiproject.api.Student


import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize

import org.springframework.web.bind.annotation.*

@Api(value="reviewers", description = "'Reviewers' management operations")
@RequestMapping("/reviewers")
interface ReviewerAPI {

    @ApiOperation("Get the list of all reviewers")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of reviewers"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<ReviewerDTO>


    @ApiOperation("Get a reviewer by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the reviewer"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT_FOUND")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ReviewerDTO


    @ApiOperation("Create a new reviewer")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a reviewer"),
        ApiResponse(code = 403, message = "BAD REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody reviewer: AddUserDTO)

}