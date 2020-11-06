package com.example.iadiproject.services

import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class ApplicationService(val applications: ApplicationRepository) {

    fun getAll() : Iterable<ApplicationDAO> = applications.findAll()

    fun getOne(id: Long): ApplicationDAO = applications.findById(id).orElseThrow() {
        NotFoundException("Application with id $id not found.")
    }

    fun addOne(application: ApplicationDAO){
        application.id = 0
        val dataItems: List<DataItem> = application.grantCall.dataItems
        applications.save(application)
    }

    fun getApplicationsByGrantCall(grantCallId: Long) = applications.findApplicationDAOByGrantCallId(grantCallId)

    fun getApplicationsByStudent(studentId: Long) = applications.findApplicationDAOByStudentId(studentId)

   // fun updateMeanScores(id: Long) = applications.findById(id).get().updateMeanScores()

    fun deleteApplicationById(id: Long) {
        applications.deleteById(id)
    }
}