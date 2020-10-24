package com.example.iadiproject.api.Application


import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.services.*
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationController(val applications: ApplicationService, val grantCalls: GrantCallService, val students: StudentService) : ApplicationAPI {

    override fun getAll(): List<ApplicationDTO> =
        applications.getAll().map {
            ApplicationDTO(it.id, it.submissionDate, it.status,
                it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
                it1 -> it1.id
            })
        }


    override fun getOne(@PathVariable id: Long) =
            applications.getOne(id).let {
                ApplicationDTO(it.id, it.submissionDate, it.status,
                    it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
                    it1 -> it1.id
                })
            }


    override fun addOne(application: ApplicationDTO) {
        val grantCall: GrantCallDAO = grantCalls.getOne(application.grantCallId)
        val studentDAO: StudentDAO = students.getOne(application.studentId)
        applications.addOne(ApplicationDAO(application.id, application.submissionDate,application.status,false,"",grantCall,studentDAO, mutableListOf()))
    }

    override fun getApplicationsByGrantCall(idGrantCall: Long) = applications.getApplicationsByGrantCall(idGrantCall).map {
        ApplicationDTO(it.id,it.submissionDate,it.status,it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
            it1-> it1.id
        })
    }

    override fun getApplicationsByStudent(studentId: Long): List<ApplicationDTO> = applications.getApplicationsByStudent(studentId).map{
        ApplicationDTO(it.id,it.submissionDate,it.status,it.decision,it.justification,it.grantCall.id,it.student.id, it.reviews.map{
            it1 -> it1.id
        })
    }



}