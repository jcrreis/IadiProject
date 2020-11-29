package com.example.iadiproject.api

import com.example.iadiproject.api.EvaluationPanel.EvaluationPanelAPI
import com.example.iadiproject.model.EvaluationPanelDAO
import com.example.iadiproject.services.EvaluationPanelService
import org.springframework.web.bind.annotation.*

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

    override fun addReviewerToPanel(id: Long, reviewerId: LongAsDTO) {
        print("Este é o id: " +reviewerId.id)
        ePanels.addReviewerToPanel(id,reviewerId.id)
    }

    override fun addPanelChairToPanel(id: Long, reviewerId: LongAsDTO) {
        ePanels.addPanelChairToPanel(id, reviewerId.id)
    }


}