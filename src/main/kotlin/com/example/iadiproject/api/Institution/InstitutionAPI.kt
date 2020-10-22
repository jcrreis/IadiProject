package com.example.iadiproject.api.Institution

import com.example.iadiproject.api.InstitutionDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="institutions", description = "'Institution' management operations")
@RequestMapping("/institutions")
interface InstitutionAPI {

    @ApiOperation("Get the list of all institutions")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of institutions")
        //ApiResponse(code = 401, message = RESPONSE_UNAUTHORIZED),
        //ApiResponse(code = 403, message = RESPONSE_FORBIDDEN)
    ])

    @GetMapping("")
    fun getAll(): List<InstitutionDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): InstitutionDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody institution: SimpleInstitutionDTO)

}