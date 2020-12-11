package com.example.iadiproject.api

import com.example.iadiproject.api.GrantCall.GrantCallAPI
import com.example.iadiproject.model.DataItem
import com.example.iadiproject.model.EvaluationPanelDAO
import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.services.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class GrantCallController(val grantCalls: GrantCallService,
                          val sponsors: SponsorService,
                          val ePanels: EvaluationPanelService,
                          val dataItems: DataItemService
): GrantCallAPI{

    override fun getAll(): List<GrantCallDTO> {
        var grantCallList = grantCalls.getAll()
        return grantCallList.map{
            GrantCallDTO(it.id,it.title,it.description,it.requirements, it.funding, it.openingDate, it.closingDate,it.dataItems.map{
                it1->DataItemDTO(it1.mandatory,it1.name,it1.datatype)
            }, it.sponsor.id, it.evaluationPanel.id,it.applications.map { it.id })
        }
    }

    override fun getOne(id: Long): GrantCallDTO {
        var grantCall = grantCalls.getOne(id)
       return grantCall.let {
        GrantCallDTO(it.id, it.title, it.description, it.requirements, it.funding, it.openingDate, it.closingDate,it.dataItems.map{
            it1->
            DataItemDTO(it1.mandatory,it1.name,it1.datatype)
        },it.sponsor.id,it.evaluationPanel.id,it.applications.map { it.id })
    }
    }


    override  fun addOne(@RequestBody grantCall: GrantCallDTO){
        val sponsor: SponsorDAO = sponsors.getSponsorByName(SecurityContextHolder.getContext().authentication.name)
        //print(grantCall.dataItems)
        val evaluationPanelDAO = EvaluationPanelDAO(0,null,0,null)
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

    fun getSponsorGrantCalls(id: Long): List<GrantCallDTO> = grantCalls.getAllGrantCallsBySponsorId(id).map {
        GrantCallDTO(it.id,it.title,it.description,it.requirements,it.funding,it.openingDate,it.closingDate,it.dataItems.map{
            it1->DataItemDTO(it1.mandatory,it1.name,it1.datatype)
        },it.sponsor.id
        ,it.evaluationPanel.id,it.applications.map{it.id})
    }

    override fun getAssignedCalls(idReviewer: Long): List<GrantCallDTO> = grantCalls.getCallsAssignedToReviewer(idReviewer).map{
        GrantCallDTO(it!!.id,it!!.title,it!!.description,it!!.requirements,it!!.funding,it!!.openingDate,it!!.closingDate,
                it!!.dataItems.map{ DataItemDTO(it.mandatory,it.name,it.datatype)},
                it!!.sponsor.id,it!!.evaluationPanel.id,it!!.applications.map{ it.id })
    }


}