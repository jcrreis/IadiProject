package com.example.iadiproject.api



import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/institutions")
class InstitutionController{



    @GetMapping("")
    fun getAllInstitutions(): InstitutionDTO =  InstitutionDTO(1,"FCT" ,"fct@gmail.com")

    @GetMapping("/{id}")
    fun getOneInstitution(@PathVariable id: Number) = InstitutionDTO(1,"FCT","fct@gmail.com")

    @PostMapping("")
    fun createNewInstitution(): String = "created"


}