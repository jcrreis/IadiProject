package com.example.iadiproject.api.Application


import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.model.ApplicationDAO
import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationController(val applications: ApplicationService, val grantCalls: GrantCallService, val students: StudentService) : ApplicationAPI {

    override fun getAll(): List<ApplicationDTO> =
        applications.getAll().map {
            ApplicationDTO(it.id, it.submissionDate, it.status,
                it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
                it1 -> it1.id
            },it.meanScores,it.dataItemAnswers.map { it1 -> it1.value})
        }


    override fun getOne(@PathVariable id: Long) =
            applications.getOne(id).let {
                ApplicationDTO(it.id, it.submissionDate, it.status,
                    it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
                    it1 -> it1.id
                },it.meanScores,it.dataItemAnswers.map { it1 -> it1.value})
            }


    override fun addOne(application: ApplicationDTO) {
        val grantCall: GrantCallDAO = grantCalls.getOne(application.grantCallId)
        val studentDAO: StudentDAO = students.getOne(application.studentId)
        applications.addOne(ApplicationDAO(application.id, application.submissionDate,application.status,false,"",grantCall,studentDAO, mutableListOf(),0.0, mutableListOf()),application.answers)
    }

    override fun getApplicationsByGrantCall(idGrantCall: Long) = applications.getApplicationsByGrantCall(idGrantCall).map {
        ApplicationDTO(it.id,it.submissionDate,it.status,it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
            it1-> it1.id
        },it.meanScores,it.dataItemAnswers.map { it1 -> it1.value})
    }

    override fun getApplicationsByStudent(studentId: Long): List<ApplicationDTO> = applications.getApplicationsByStudent(studentId).map{
        ApplicationDTO(it.id,it.submissionDate,it.status,it.decision,it.justification,it.grantCall.id,it.student.id, it.reviews.map{
            it1 -> it1.id
        },it.meanScores,it.dataItemAnswers.map { it1 -> it1.value})
    }

    override fun deleteApplicationById(id: Long) = applications.deleteApplicationById(id)




}