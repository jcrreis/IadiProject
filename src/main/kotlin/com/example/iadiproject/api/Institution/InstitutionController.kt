package com.example.iadiproject.api.Institution



import com.example.iadiproject.api.*
import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.services.InstitutionService

import org.springframework.web.bind.annotation.*

@RestController
class InstitutionController(val institutions: InstitutionService): InstitutionAPI {


    fun transformDAOIntoDTO(it: InstitutionDAO): InstitutionDTO{
        return InstitutionDTO(it.id,it.name,it.contact,it.users.map {
            it1 -> it1.id
        })
    }

    override fun getAll(): List<InstitutionDTO> = institutions.getAll().map { transformDAOIntoDTO(it) }

    override fun getOne(id: Long): InstitutionDTO = transformDAOIntoDTO(institutions.getOne(id))


    override fun addOne(institution: SimpleInstitutionDTO) {
        institutions.addOne(InstitutionDAO(institution.id,institution.name,institution.contact, mutableListOf()))
    }


}