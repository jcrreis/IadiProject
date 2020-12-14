package com.example.iadiproject.services



import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.InstitutionRepository
import org.springframework.stereotype.Service

@Service
class InstitutionService(val institutions: InstitutionRepository)
{

    fun getAll() : Iterable<InstitutionDAO> = institutions.findAll()

    fun getOne(id: Long): InstitutionDAO = institutions.findById(id).orElseThrow() {
        NotFoundException("Institution with id $id not found.")
    }

    fun addOne(institution: InstitutionDAO){
        institution.id = 0
        institutions.save(institution)
    }
}