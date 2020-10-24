package com.example.iadiproject.api

import com.example.iadiproject.api.EvaluationPanel.EvaluationPanelAPI
import com.example.iadiproject.services.EvaluationPanelDAO
import com.example.iadiproject.services.EvaluationPanelService
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
class EvaluationPanelController(val ePanels: EvaluationPanelService) : EvaluationPanelAPI{

    override fun getAll(): List<EvaluationPanelDTO> {
        return ePanels.getAll().map {
            EvaluationPanelDTO(it.id,it.reviewers.map{
                it1 -> SimpleReviewerDTO(it1.id,it1.name,it1.email,it1.address)
            }, it.panelchair?.id,it.grantCall!!.id)
        }
    }

    override fun getOne(id: Long): EvaluationPanelDTO {
        return ePanels.getOne(id).let{EvaluationPanelDTO(it.id,it.reviewers.map{
            it1 -> SimpleReviewerDTO(it1.id,it1.name,it1.email,it1.address)
        }, it.panelchair?.id,it.grantCall!!.id)
        }
    }

    override fun addOne(ePanel: EvaluationPanelDTO){
        ePanels.addOne(EvaluationPanelDAO(ePanel.id,null,-1,null))
    }

    override fun addReviewerToPanel(id: Long, reviewerId: Long) {
        ePanels.addReviewerToPanel(id,reviewerId)
    }

    override fun addPanelChairToPanel(id: Long, reviewerId: Long) {
        ePanels.addPanelChairToPanel(id, reviewerId)
    }







}