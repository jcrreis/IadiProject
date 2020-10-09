package com.example.iadiproject.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/applications")
class ApplicationController(){
    var dataitems = listOf(DataItemDTO("Number",true),DataItemDTO("Number",true))
    var institution = InstitutionDTO(1, "FCT","fct@gmail.com")
    var student = StudentDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution,"cv")
    var call = GrantCallDTO(1,"ERASMUS","FCT","Something",300, Date(12/12/12), Date(12,12,12),dataitems)
    var reviewer = ReviewerDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution)
    var application = ApplicationDTO(1, student,call,listOf())
    var reviews = listOf(ReviewDTO(1,application,reviewer,1,"Observation..."))

    @GetMapping("")
    fun getAllApplications() =  listOf(ApplicationDTO(1,student,call,reviews))

     @GetMapping("/{id}")
     fun getOneApplication(@PathVariable id: Number) = ApplicationDTO(1,student,call,reviews)

    @PostMapping("")
    fun createNewApplication(@RequestBody application : ApplicationDTO) : String {
        return "CREATED"
    }




}