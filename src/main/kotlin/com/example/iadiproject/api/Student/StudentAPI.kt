package com.example.iadiproject.api.Student

import com.example.iadiproject.api.CurriculumDTO
import com.example.iadiproject.api.StudentDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="students", description = "'Student' management operations")
@RequestMapping("/students")
interface StudentAPI {

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_REVIEWER') ")
    @ApiOperation("Get the list of all students")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of students"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<StudentDTO>


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_REVIEWER') or @securityService.isUserOwnerOfResource(authentication.principal,#id) ")
    @ApiOperation("Get a student by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of students"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): StudentDTO


    @ApiOperation("Get a student by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of students"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @PostMapping("/{id}/cv")
    @PreAuthorize( "@securityService.isUserOwnerOfResource(authentication.principal,#id)")
    fun addCvToStudent(@PathVariable id: Long, @RequestBody cv: CurriculumDTO)

}
