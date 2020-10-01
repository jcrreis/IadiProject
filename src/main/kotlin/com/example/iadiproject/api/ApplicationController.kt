package com.example.iadiproject.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/applications")
class ApplicationController{

    @GetMapping("")
    fun getAllApplications() = emptyList<ApplicationDTO>()

     @GetMapping("/{id}")
     fun getOneApplication(@PathVariable id: Number) = ApplicationDTO(1,"Joao","Dog")
}