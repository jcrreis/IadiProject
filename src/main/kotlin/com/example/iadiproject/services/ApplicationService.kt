package com.example.iadiproject.services

import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class ApplicationService(val applications: ApplicationRepository,
                         val dataItems: DataItemService)
{

    fun getAll() : Iterable<ApplicationDAO> = applications.findAll()

    fun getOne(id: Long): ApplicationDAO = applications.findById(id).orElseThrow() {
        NotFoundException("Application with id $id not found.")
    }

    fun addOne(application: ApplicationDAO,answers: List<String>){
        application.id = 0
        val dataItems: List<DataItem> = application.grantCall.dataItems

        if(application.student.cv == null){
            throw BadRequestException("Student need to have a cv inorder to apply to a Grant Call")
        }

        if(answers.count() !== dataItems.count()){
            throw BadRequestException("Mismatch in data items and respective answers")
        }

        for((i, d) in dataItems.withIndex()){
            if(d.mandatory && answers[i] == ""){
                throw BadRequestException("A mandatory field was sent empty")
            }
            val answer = DataItemAnswer(0,d,answers[i])
            application.dataItemAnswers.add(answer)
            this.dataItems.addDataItemAnswer(answer)
        }
        applications.save(application)
        this.dataItems.addApplicationToAnswer(application)
    }

    fun getApplicationsByGrantCall(grantCallId: Long) = applications.findApplicationDAOByGrantCallId(grantCallId)

    fun getApplicationsByStudent(studentId: Long) = applications.findApplicationDAOByStudentId(studentId)

    fun deleteApplicationById(id: Long) = applications.deleteById(id)


    fun submitApplication(id :Long){
        val application: ApplicationDAO = applications.findById(id).orElseThrow(){
            NotFoundException("Application with id $id not found.")
        }
        application.status = 0
        applications.save(application)
    }

    fun editApplication(id: Long, a: ApplicationDTO){
        val application: ApplicationDAO = applications.findById(id).orElseThrow(){
            NotFoundException("Application with id $id not found.")
        }

        for((i,d) in application.dataItemAnswers.withIndex()){
            d.value = a.answers[i]
        }

        applications.save(application)

    }

}