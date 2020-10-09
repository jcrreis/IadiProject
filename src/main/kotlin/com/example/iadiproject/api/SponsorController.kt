package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sponsors")
class SponsorController{

    @GetMapping("")
    fun getAllSponsors(): SponsorDTO =  SponsorDTO(1,"FCT" ,"fct@gmail.com")

    @GetMapping("/{id}")
    fun getOneSponsor(@PathVariable id: Number) = SponsorDTO(1,"FCT","fct@gmail.com")

    @PostMapping("")
    fun createNewSponsor(): String = "created"

}