package com.example.iadiproject.api.Institution

import com.example.iadiproject.api.InstitutionDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="institutions", description = "'Institution' management operations")
@RequestMapping("/institutions")
interface InstitutionAPI {


    @ApiOperation("Get the list of all institutions")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of institutions"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<InstitutionDTO>


    @ApiOperation("Get a institution by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the institution"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN"),
        ApiResponse(code = 404, message = "NOT_FOUND")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): InstitutionDTO


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Create a new institution")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a new institution"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody institution: SimpleInstitutionDTO)

}