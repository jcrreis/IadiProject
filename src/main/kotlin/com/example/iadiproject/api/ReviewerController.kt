package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reviewers")
class ReviewerController{

    var institution : InstitutionDTO = InstitutionDTO(1,"FCT","fct@gmail.com")

    @GetMapping("")
    fun getAllStudents(): ReviewerDTO =  ReviewerDTO(1,"João","joao@gmail.com",
            "Rua dos portugueses",institution)

    @GetMapping("/{id}")
    fun getOneStudent(@PathVariable id: Number) = ReviewerDTO(1,"João","joao@gmail.com",
            "Rua dos portugueses",institution)

    @PostMapping("")
    fun createNewStudent(): String = "created"

}