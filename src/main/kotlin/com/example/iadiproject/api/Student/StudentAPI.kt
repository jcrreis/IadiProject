package com.example.iadiproject.api.Student

import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.api.StudentDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="students", description = "'Student' management operations")
@RequestMapping("/students")
interface StudentAPI {

    @ApiOperation("Get the list of all students")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of students"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<StudentDTO>


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
        ApiResponse(code = 201, message = "Successfully created a new student"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody student: AddUserDTO)

}
