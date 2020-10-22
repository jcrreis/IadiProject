package com.example.iadiproject.api.Application


import com.example.iadiproject.api.ApplicationDTO
import com.example.iadiproject.services.ApplicationDAO
import com.example.iadiproject.services.ApplicationService
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationController(val applications: ApplicationService) : ApplicationAPI {

    override fun getAll(): List<ApplicationDTO> =
        applications.getAll().map {
            ApplicationDTO(it.id, it.submissionDate, it.status,
                it.decision,it.justification)
        }


    override fun getOne(@PathVariable id: Long) =
            applications.getOne(id).let {
                ApplicationDTO(it.id, it.submissionDate, it.status,
                    it.decision,it.justification)
            }

    override fun addOne(application: ApplicationDTO) {
        applications.addOne(ApplicationDAO(application.id, application.submissionDate,application.status,false,""))
    }


}