package com.example.iadiproject.api.Student


import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="reviewers", description = "'Student' management operations")
@RequestMapping("/reviewers")
interface ReviewerAPI {

    @ApiOperation("Get the list of all reviewers")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of reviewers")
        //ApiResponse(code = 401, message = RESPONSE_UNAUTHORIZED),
        //ApiResponse(code = 403, message = RESPONSE_FORBIDDEN)
    ])
    @GetMapping("")
    fun getAll(): List<ReviewerDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ReviewerDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody reviewer: AddUserDTO)

}