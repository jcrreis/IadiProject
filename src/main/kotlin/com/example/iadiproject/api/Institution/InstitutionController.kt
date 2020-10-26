package com.example.iadiproject.api.Institution



import com.example.iadiproject.api.*
import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.services.InstitutionService

import org.springframework.web.bind.annotation.*

@RestController
class InstitutionController(val institutions: InstitutionService): InstitutionAPI {



    override fun getAll(): List<InstitutionDTO> = institutions.getAll().map { InstitutionDTO(it.id,it.name,it.contact,it.users.map {
        it1 -> SimpleStudentDTO(it1.id,it1.name,it1.email,it1.address)
    })
    }

    override fun getOne(id: Long): InstitutionDTO = institutions.getOne(id).let {  InstitutionDTO(it.id,it.name,it.contact,it.users.map {
    it1 -> SimpleStudentDTO(it1.id,it1.name,it1.email,it1.address)
    })
    }


    override fun addOne(institution: SimpleInstitutionDTO) {
        institutions.addOne(InstitutionDAO(institution.id,institution.name,institution.contact, mutableListOf()))
    }


}