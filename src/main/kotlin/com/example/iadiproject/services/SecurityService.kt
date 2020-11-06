package com.example.iadiproject.services

import com.example.iadiproject.model.*
import com.example.iadiproject.securityconfig.CustomUserDetails
import org.springframework.stereotype.Service


@Service
class SecurityService(
        val applications: ApplicationRepository,
        val users: UserRepository,
        val ePanels: EvaluationPanelRepository,
        val review: ReviewRepository,
        val grantCalls: GrantCallRepository,
        val reviewers: ReviewerRepository,
        val students: StudentRepository
)
{

    fun isUserOwnerOfResource(principal: CustomUserDetails, id: Long): Boolean{
        return principal.getId() == id
    }

    fun doesReviewerBelongToGrantCallPanel(principal: CustomUserDetails, id: Long): Boolean{
        val ePanel = grantCalls.findById(id).get().evaluationPanel
        return ePanel.panelchair?.id == principal.getId() || ePanel.reviewers.contains(reviewers.findById(id).get())
    }

    fun doesReviewerBelongToPanel(principal: CustomUserDetails, id: Long): Boolean{
        val ePanel: EvaluationPanelDAO = ePanels.findById(id).get()
        return ePanel.panelchair?.id == principal.getId() || ePanel.reviewers.contains(reviewers.findById(id).get())
    }

    fun isStudentOwnerOfApplication(principal: CustomUserDetails, idApplication: Long): Boolean{
        val application: ApplicationDAO = applications.getOne(idApplication)
        return application.student.id == principal.getId()
    }








}

