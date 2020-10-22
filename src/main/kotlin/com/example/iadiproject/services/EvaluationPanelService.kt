package com.example.iadiproject.services



import com.example.iadiproject.model.EvaluationPanelRepository
import com.example.iadiproject.model.GrantCallRepository

import org.springframework.stereotype.Service

@Service
class EvaluationPanelService(val ePanels: EvaluationPanelRepository, val grantCalls: GrantCallRepository) {

    fun getAll() : Iterable<EvaluationPanelDAO> = ePanels.findAll()

    fun getOne(id: Long): EvaluationPanelDAO = ePanels.findById(id).orElseThrow() {
        NotFoundException("Evaluation Panel with id $id not found.")
    }

    fun addOne(ePanel: EvaluationPanelDAO){
        ePanel.id = 0
        ePanels.save(ePanel)
    }

    fun addGrantCallToPanel(grantCall: GrantCallDAO){
        val ePanel = ePanels.findById(grantCall.evaluationPanel.id).get()
        ePanel.grantCall = grantCall
        ePanels.save(ePanel)
        val grantCallDAO = grantCalls.findById(grantCall.id).get()
        //print(grantCallDAO)
        grantCallDAO.evaluationPanel = ePanel
        grantCalls.save(grantCallDAO)
    }
}