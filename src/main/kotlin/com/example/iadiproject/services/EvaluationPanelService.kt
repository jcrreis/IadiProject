package com.example.iadiproject.services



import com.example.iadiproject.model.*

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class EvaluationPanelService(val ePanels: EvaluationPanelRepository, val grantCalls: GrantCallRepository, val reviewers: ReviewerService) {

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
        grantCallDAO.evaluationPanel = ePanel
        grantCalls.save(grantCallDAO)
    }

    @Transactional
    fun addReviewerToPanel(id: Long, reviewerId: Long){
        val ePanel = getOne(id)
        val reviewer: ReviewerDAO = reviewers.getOne(reviewerId)
        if(ePanel.reviewers.map{it.id}.contains(reviewer.id)|| (ePanel.panelchair?.id == reviewer.id)) {
            return throw ConflictException("This reviewer with id $reviewer.id already belongs to this Panel")
        }
        ePanel.reviewers.add(reviewer)
        reviewers.addPanelToReviewer(reviewer,ePanel)
        ePanels.save(ePanel)
    }

    @Transactional
    fun addPanelChairToPanel(id: Long, reviewerId: Long){
        val ePanel = getOne(id)
        val reviewer: ReviewerDAO = reviewers.getOne(reviewerId)
        if(ePanel.reviewers.contains(reviewer) || (ePanel.panelchair?.equals(reviewer)==true)) {
            return throw ConflictException("This reviewer with id $reviewer.id already belongs to this Panel")
        }

        reviewers.addPanelChairToReviewer(reviewer,ePanel)
        ePanel.panelchair = reviewer
        ePanels.save(ePanel)

    }
}