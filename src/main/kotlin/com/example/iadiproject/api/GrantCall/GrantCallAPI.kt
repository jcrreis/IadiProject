package com.example.iadiproject.api.GrantCall

import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.InstitutionDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@Api(value="grantcalls", description = "'Grant Calls' management operations")
@RequestMapping("/grantcalls")
interface GrantCallAPI {

    @GetMapping("")
    fun getAll(): List<GrantCallDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): GrantCallDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody grantCall: GrantCallDTO){

    }
}