package com.example.iadiproject.api

import com.example.iadiproject.api.GrantCall.GrantCallAPI
import com.example.iadiproject.model.DataItem
import com.example.iadiproject.model.EvaluationPanelDAO
import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*

@RestController
class GrantCallController(val grantCalls: GrantCallService, val sponsors: SponsorService, val ePanels: EvaluationPanelService): GrantCallAPI{

    override fun getAll(): List<GrantCallDTO> {
        var grantCallList = grantCalls.getAll()
        return grantCallList.map{
            GrantCallDTO(it.id,it.title,it.description,it.requirements, it.funding, it.openingDate, it.closingDate, it.dataItems.map {
                DataItemDTO(it.mandatory,it.name,it.datatype)
            }, it.sponsor.id, 1)
        }
    }

    override fun getOne(id: Long): GrantCallDTO {
        var grantCall = grantCalls.getOne(id)

       return grantCall.let {
        GrantCallDTO(it.id, it.title, it.description, it.requirements, it.funding, it.openingDate, it.closingDate, it.dataItems.map {
            DataItemDTO(it.mandatory,it.name,it.datatype)
        }, it.sponsor.id,1)
    }
    }


    override  fun addOne(@RequestBody grantCall: GrantCallDTO){
        val sponsorDAO = sponsors.getOne(grantCall.sponsorId)
        val evaluationPanelDAO = EvaluationPanelDAO(0,null,0,null)
        ePanels.addOne(evaluationPanelDAO)
        val grantCallDAO = GrantCallDAO(grantCall.id,grantCall.title,grantCall.description,
                grantCall.requirements,grantCall.funding,grantCall.openingDate,grantCall.closingDate,grantCall.dataItems.map{
            DataItem(0,it.mandatory,it.name,it.datatype, GrantCallDAO(), mutableListOf())
        },sponsorDAO,evaluationPanelDAO, mutableListOf())
        grantCalls.addOne(grantCallDAO)
        ePanels.addGrantCallToPanel(grantCallDAO)

    }

    fun getSponsorGrantCalls(id: Long): List<GrantCallDTO> = grantCalls.getAllGrantCallsBySponsorId(id).map {
        GrantCallDTO(it.id,it.title,it.description,it.requirements,it.funding,it.openingDate,it.closingDate, it.dataItems.map {
            DataItemDTO(it.mandatory,it.name,it.datatype)
        },it.sponsor.id
        ,it.evaluationPanel.id)
    }


}