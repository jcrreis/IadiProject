package com.example.iadiproject.services

import com.example.iadiproject.model.*
import com.example.iadiproject.securityconfig.UserAuthToken
import org.springframework.stereotype.Service


@Service
class SecurityService(
        val applications: ApplicationRepository,
        val users: UserRepository,
        val ePanels: EvaluationPanelRepository,
        val reviews: ReviewRepository,
        val grantCalls: GrantCallRepository,
        val reviewers: ReviewerRepository,
        val students: StudentRepository,
        val sponsors: SponsorRepository
)
{

    fun isUserOwnerOfResource(principal: UserAuthToken, id: Long): Boolean{
        val user : UserDAO = users.findUserDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a user")
        }
        return user.id == id
    }

    fun doesReviewerBelongToGrantCallPanel(principal: UserAuthToken, id: Long): Boolean{
        val reviewer: ReviewerDAO = reviewers.findReviewerDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a reviewer")
        }
        val ePanel = grantCalls.findById(id).get().evaluationPanel
        return ePanel.panelchair?.id == reviewer.id || ePanel.reviewers.contains(reviewer)
    }

    fun doesReviewerBelongToPanel(principal: UserAuthToken, id: Long): Boolean{
        val reviewer: ReviewerDAO = reviewers.findReviewerDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a reviewer")
        }
        val ePanel: EvaluationPanelDAO = ePanels.findById(id).get()
        return ePanel.panelchair?.id == reviewer.id || ePanel.reviewers.contains(reviewer)
    }

    fun isStudentOwnerOfApplication(principal: UserAuthToken, idApplication: Long): Boolean{
        val student: StudentDAO = students.findStudentDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a student")
        }
        val application: ApplicationDAO = applications.getOne(idApplication)
        return application.student.id == student.id
    }

    fun doesReviewerBelongEpanelOfReview(principal: UserAuthToken, idReview: Long): Boolean{
        val review: ReviewDAO = reviews.getOne(idReview)
        val ePanel: EvaluationPanelDAO = review.application.grantCall.evaluationPanel
        val reviewer: ReviewerDAO = reviewers.findReviewerDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a reviewer")
        }

        return ePanel.panelchair?.id == reviewer.id || ePanel.reviewers.contains(reviewer)
    }

    fun isSponsorOwnerOfReviewGrantCall(principal: UserAuthToken, idReview: Long): Boolean{
        val review: ReviewDAO = reviews.getOne(idReview)
        val grantCall: GrantCallDAO = review.application.grantCall
        print(grantCall)
        val sponsor: SponsorDAO = sponsors.findSponsorDAOByName(principal.name).orElseThrow(){
            ForbiddenException("Not a Sponsor")
        }

        return grantCall.sponsor.id == sponsor.id
    }

}

