package com.example.iadiproject.api.GrantCall

import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.model.ApplicationDAO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@Api(value="grantcalls", description = "'Grant Calls' management operations")
@RequestMapping("/grantcalls")
interface GrantCallAPI {

    @ApiOperation("Get the list of all grant calls")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of grant calls"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<GrantCallDTO>


    @ApiOperation("Get a grant call by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved a grant call"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): GrantCallDTO

    @PreAuthorize("hasAuthority('ROLE_SPONSOR')")
    @ApiOperation("Create a new grant call")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a grant call"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody grantCall: GrantCallDTO)


    @ApiOperation("Get the list of all grant calls assigned to a reviewer")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of grant calls assigned to a Reviewer"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/reviewer/{idReviewer}")
    @PreAuthorize("hasAuthority('ROLE_REVIEWER')")
    fun getAssignedCalls(@PathVariable idReviewer: Long): List<GrantCallDTO>

    @ApiOperation("Get the list of all applications funded")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of funded applications for a grantcall"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/{id}/fundedapplications")
    fun getFundedApplications(@PathVariable id: Long): List<ApplicationDTO>
}