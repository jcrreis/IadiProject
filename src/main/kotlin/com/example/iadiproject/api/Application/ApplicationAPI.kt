package com.example.iadiproject.api.Application

import com.example.iadiproject.api.ApplicationDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="applications", description = "'Grant Application' management operations")
@RequestMapping("/applications")
interface ApplicationAPI {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get the list of all applications")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of applications"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<ApplicationDTO>

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @securityService.isStudentOwnerOfApplication(principal, #id)")
    @ApiOperation("Get an application by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved application"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT FOUND")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ApplicationDTO


    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @ApiOperation("Add a new application")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a new application"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody application: ApplicationDTO)


    @PostAuthorize("@securityService.doesReviewerBelongToGrantCallPanel(authentication.principal, #idGrantCall) or hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get all applications of a single grant call")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved all applications of a grant call"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/grantcall/{idGrantCall}")
    fun getApplicationsByGrantCall(@PathVariable idGrantCall: Long): List<ApplicationDTO>


    @PostAuthorize("@securityService.isUserOwnerOfResource(authentication.principal, #studentId) or hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get all applications of a student")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved all applications of a student"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/student/{studentId}")
    fun getApplicationsByStudent(@PathVariable studentId: Long): List<ApplicationDTO>

    @ApiOperation("Delete an application")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully deleted an Application"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@securityService.isStudentOwnerOfApplication(authentication.principal, #id) or hasAuthority('ROLE_ADMIN')")
    fun deleteApplicationById(@PathVariable id: Long)


    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully submitted application"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT FOUND")
    ])
    @PreAuthorize("@securityService.isStudentOwnerOfApplication(authentication.principal, #id)")
    @PostMapping("/{id}")
    fun submitApplication(@PathVariable id: Long)

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully edited application"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT FOUND")
    ])
    @PreAuthorize("@securityService.isStudentOwnerOfApplication(authentication.principal, #id)")
    @PutMapping("/{id}")
    fun editApplication(@PathVariable id: Long, @RequestBody application: ApplicationDTO)
}
