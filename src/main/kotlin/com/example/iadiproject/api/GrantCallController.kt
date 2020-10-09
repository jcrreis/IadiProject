package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/grantcalls")
class GrantCallController{

    var dataitems = listOf(DataItemDTO("Number",true),DataItemDTO("Number",true))


    @GetMapping("")
    fun getAllGrantCall(): List<GrantCallDTO> =  listOf(GrantCallDTO(1,"ERASMUS","FCT","fct@gmail.com",300, Date(12/12/12), Date(12/12/12),dataitems))


    @GetMapping("/{id}")
    fun getOneGrantCall(@PathVariable id: Number) = GrantCallDTO(1,"ERASMUS","FCT","fct@gmail.com",300, Date(12/12/12), Date(12/12/12),dataitems)

    @PostMapping("")
    fun createNewGrantCall(): String = "created"


}