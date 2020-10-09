package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController{
    var institution : InstitutionDTO = InstitutionDTO(1,"FCT","fct@gmail.com")

    @GetMapping("")
    fun getAllStudents(): StudentDTO =  StudentDTO(1,"João","joao@gmail.com",
            "Rua dos portugueses",institution,"cv")

    @GetMapping("/{id}")
    fun getOneStudent(@PathVariable id: Number) = StudentDTO(1,"João","joao@gmail.com",
            "Rua dos portugueses",institution,"cv")

    @PostMapping("")
    fun createNewStudent(): String = "created"

}