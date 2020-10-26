package com.example.iadiproject.services

import com.example.iadiproject.model.ApplicationDAO
import com.example.iadiproject.model.ApplicationRepository
import org.springframework.stereotype.Service

@Service
class ApplicationService(val applications: ApplicationRepository) {

    fun getAll() : Iterable<ApplicationDAO> = applications.findAll()

    fun getOne(id: Long): ApplicationDAO = applications.findById(id).orElseThrow() {
        NotFoundException("Application with id $id not found.")
    }

    fun addOne(application: ApplicationDAO){
        application.id = 0
        applications.save(application)
    }

    fun getApplicationsByGrantCall(grantCallId: Long) = applications.findApplicationDAOByGrantCallId(grantCallId)

    fun getApplicationsByStudent(studentId: Long) = applications.findApplicationDAOByStudentId(studentId)
}