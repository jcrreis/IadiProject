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

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_REVIEWER')")
    @ApiOperation("Get the list of all reviewers")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of reviewers"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<ReviewerDTO>

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_REVIEWER')")
    @ApiOperation("Get a reviewer by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the reviewer"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT_FOUND")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ReviewerDTO

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_REVIEWER')")
    @ApiOperation("Get all id of all applications reviewed")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the list of applications id's"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT_FOUND")
    ])
    @GetMapping("/{id}/applications")
    fun getApplicationsReviewed(@PathVariable id: Long): List<Long>


}