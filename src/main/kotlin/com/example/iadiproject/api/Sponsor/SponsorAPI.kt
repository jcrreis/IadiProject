package com.example.iadiproject.api.Sponsor

import com.example.iadiproject.api.SponsorDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*


@Api(value="sponsors", description = "'Sponsor' management operations")
@RequestMapping("/sponsors")
interface SponsorAPI {

    @ApiOperation("Get the list of all sponsors")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of sponsors"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<SponsorDTO>


    @ApiOperation("Get a sponsor by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved a sponsor"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): SponsorDTO

}