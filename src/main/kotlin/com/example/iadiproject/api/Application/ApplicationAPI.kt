package com.example.iadiproject.api.Application

import com.example.iadiproject.api.ApplicationDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="applications", description = "'Grant Application' management operations")
@RequestMapping("/applications")
interface ApplicationAPI {

    @ApiOperation("Get the list of all applications")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of applications")
        //ApiResponse(code = 401, message = RESPONSE_UNAUTHORIZED),
        //ApiResponse(code = 403, message = RESPONSE_FORBIDDEN)
    ])

    @GetMapping("")
    fun getAll(): List<ApplicationDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ApplicationDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody application: ApplicationDTO)

    @GetMapping("/grantcall/{idGrantCall}")
    fun getApplicationsByGrantCall(@PathVariable idGrantCall: Long): List<ApplicationDTO>

    @GetMapping("/student/{studentId}")
    fun getApplicationsByStudent(@PathVariable studentId: Long): List<ApplicationDTO>

}
