package com.example.iadiproject.services

import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class GrantCallService(val grantCalls: GrantCallRepository, val dataItems: DataItemRepository, val reviewers: ReviewerRepository) {

    fun getAll() : Iterable<GrantCallDAO> = grantCalls.findAll()

    fun getOne(id: Long): GrantCallDAO = grantCalls.findById(id).orElseThrow() {
        NotFoundException("Grant Call with id $id not found.")
    }

    fun addOne(grantCall: GrantCallDAO){
        grantCall.id = 0
        grantCalls.save(grantCall)
    }

    fun getAllGrantCallsBySponsorId(id: Long): Iterable<GrantCallDAO>{
        return grantCalls.findGrantCallDAOBySponsorId(id)
    }

    fun getCallsAssignedToReviewer(id: Long): List<GrantCallDAO?> {
        val reviewer: ReviewerDAO = reviewers.findById(id).orElseThrow(){
            NotFoundException("Reviewer with id $id not found.")
        }
        val panels: List<EvaluationPanelDAO> = reviewer.evaluationPanels
        val panelChairs: List<EvaluationPanelDAO> = reviewer.panelsChairs
        val allPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
        allPanels.addAll(panels)
        allPanels.addAll(panelChairs)
        val assignedCallsToReviewer: MutableList<GrantCallDAO?> = mutableListOf()
        for(eP in allPanels){
            assignedCallsToReviewer.add(eP.grantCall)
        }

        return assignedCallsToReviewer

    }
}