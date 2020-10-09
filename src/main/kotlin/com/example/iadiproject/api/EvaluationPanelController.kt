package com.example.iadiproject.api

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/evaluationpanels")
class EvaluationPanelController{
    var institution = InstitutionDTO(1, "FCT","fct@gmail.com")
    var sponsor = SponsorDTO(1, "FCT","fct@gmail.com")
    var reviewers = listOf(ReviewerDTO(1,"Joao","joao@gmail.com","Rua dos portugueses",institution),ReviewerDTO(1,"Joao","joao@gmail.com","Rua dos portugueses",institution))
    var panelchair = ReviewerDTO(1,"Ze","ze@gmail.com","Rua dos portugueses",institution)
    var student = StudentDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution,"cv")
    var dataitems = listOf(DataItemDTO("Number",true),DataItemDTO("Number",true))
    var call = GrantCallDTO(1,"ERASMUS","FCT","Something",300, Date(12/12/12), Date(12,12,12),dataitems)
    var reviewer = ReviewerDTO(1,"Joao","joao@fct.com","Rua dos portugueses",institution)
    var application = ApplicationDTO(1, student,call,listOf())
    var reviews = listOf(ReviewDTO(1,application,reviewer,1,"Observation..."))

    @GetMapping("")
    fun getAllGrantCall(): List<EvaluationPanelDTO> =  listOf(EvaluationPanelDTO(1, reviewers, panelchair, ApplicationDTO(1,student,call,reviews),sponsor),EvaluationPanelDTO(2, reviewers, panelchair,ApplicationDTO(1,student,call,reviews),sponsor))

    @GetMapping("/{id}")
    fun getOneGrantCall(@PathVariable id: Number) = EvaluationPanelDTO(1, reviewers, panelchair, ApplicationDTO(1,student,call,reviews),sponsor)

    @PostMapping("")
    fun createNewGrantCall(): String = "created"


}