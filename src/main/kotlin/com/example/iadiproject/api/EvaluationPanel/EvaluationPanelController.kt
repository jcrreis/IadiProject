package com.example.iadiproject.api

import com.example.iadiproject.api.EvaluationPanel.EvaluationPanelAPI
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
            },it.grantCall!!.id)
        }
    }

    override fun getOne(id: Long): String {
        return "ola"
    }
    override fun addOne(ePanel: EvaluationPanelDTO){

    }



}