package com.example.iadiproject.api.Application


import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.model.ApplicationDAO
import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class  ApplicationController(val applications: ApplicationService,
                             val grantCalls: GrantCallService,
                             val students: StudentService) : ApplicationAPI
{


    fun transformDAOIntoDTO(it: ApplicationDAO): ApplicationDTO{
        return ApplicationDTO(it.id, it.submissionDate, it.status,
                it.decision,it.justification,it.grantCall.id,it.student.id,it.reviews.map{
            it1 -> it1.id
        },it.meanScores,it.dataItemAnswers.map { it1 -> it1.value})
    }

    override fun getAll(): List<ApplicationDTO> = applications.getAll().map { transformDAOIntoDTO(it) }


    override fun getOne(@PathVariable id: Long) =
            applications.getOne(id).let { transformDAOIntoDTO(it) }


    override fun addOne(application: ApplicationDTO) {
        val student: StudentDAO = students.getStudentByName(SecurityContextHolder.getContext().authentication.name)

        val grantCall: GrantCallDAO = grantCalls.getOne(application.grantCallId)
        applications.addOne(ApplicationDAO(application.id, Date(),application.status,false,application.justification,
                grantCall,student, mutableListOf(), 0.0, mutableListOf()),application.answers)
    }

    override fun getApplicationsByGrantCall(idGrantCall: Long) = applications.getApplicationsByGrantCall(idGrantCall).map{ transformDAOIntoDTO(it) }

    override fun getApplicationsByStudent(studentId: Long): List<ApplicationDTO> = applications.getApplicationsByStudent(studentId).map{ transformDAOIntoDTO(it) }

    override fun deleteApplicationById(id: Long) = applications.deleteApplicationById(id)

    override fun submitApplication(id: Long) = applications.submitApplication(id)

    override fun editApplication(id: Long, application: ApplicationDTO) = applications.editApplication(id, application)


}