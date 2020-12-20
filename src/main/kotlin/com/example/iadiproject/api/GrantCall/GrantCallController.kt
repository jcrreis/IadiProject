package com.example.iadiproject.api

import com.example.iadiproject.api.GrantCall.GrantCallAPI
import com.example.iadiproject.model.*
import com.example.iadiproject.services.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class  GrantCallController(val grantCalls: GrantCallService,
                          val sponsors: SponsorService,
                          val ePanels: EvaluationPanelService,
                          val dataItems: DataItemService
): GrantCallAPI{

    fun transformDAOIntoDTO(it: GrantCallDAO): GrantCallDTO{
        return GrantCallDTO(it.id,it.title,it.description,it.requirements, it.funding, it.openingDate, it.closingDate,it.dataItems.map{
            it1->DataItemDTO(it1.mandatory,it1.name,it1.datatype)
        }, it.sponsor.id, it.evaluationPanel.id,it.applications.map { it.id })
    }

    override fun getAll(): List<GrantCallDTO> {
        var grantCallList = grantCalls.getAll()
        return grantCallList.map{ transformDAOIntoDTO(it) }
    }

    override fun getOne(id: Long): GrantCallDTO {
        var grantCall = grantCalls.getOne(id)
       return transformDAOIntoDTO(grantCall)
    }


    override  fun addOne(@RequestBody grantCall: GrantCallDTO){
        val sponsor: SponsorDAO = sponsors.getSponsorByName(SecurityContextHolder.getContext().authentication.name)
        val evaluationPanelDAO = EvaluationPanelDAO(0,null,null)
        ePanels.addOne(evaluationPanelDAO)

        val dataItemsDAO: List<DataItem> =  grantCall.dataItems.map {
            DataItem(0, it.mandatory, it.name, it.datatype, mutableListOf())
        }
        for(d in dataItemsDAO)
            dataItems.addOne(d)

        val grantCallDAO = GrantCallDAO(grantCall.id,grantCall.title,grantCall.description,
                grantCall.requirements,grantCall.funding,grantCall.openingDate,grantCall.closingDate,dataItemsDAO,sponsor,evaluationPanelDAO, mutableListOf())
        grantCalls.addOne(grantCallDAO)
        ePanels.addGrantCallToPanel(grantCallDAO)
        dataItems.addGrantCallToDataItems(grantCallDAO)
    }

    override fun getAssignedCalls(idReviewer: Long): List<GrantCallDTO> = grantCalls.getCallsAssignedToReviewer(idReviewer).map{ transformDAOIntoDTO(it!!) }

    override fun getFundedApplications(id: Long): List<ApplicationDTO> = grantCalls.getFundedApplications(id).map{
        ApplicationDTO(it.id,it.submissionDate,it.status,it.decision,it.justification,it.grantCall.id,
                it.student.id, mutableListOf(),it.meanScores, mutableListOf())
    }


}