package com.example.iadiproject.api.Student

import com.example.iadiproject.api.*
import com.example.iadiproject.services.InstitutionService
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.StudentService
import com.example.iadiproject.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
class StudentController(val students: StudentService, val institutions: InstitutionService,val users: UserService) : StudentAPI {

    override fun getAll(): List<StudentDTO> = students.getAll().map {
        StudentDTO(it.id,it.name,it.email,it.address,SimpleInstitutionDTO(it.institution.id,it.institution.name,it.institution.contact),"",it.cv)
    }

    override fun getOne(id: Long): StudentDTO = students.getOne(id).let {
        StudentDTO(it.id,it.name,it.email,it.address,SimpleInstitutionDTO(it.institution.id,it.institution.name,it.institution.contact),"",it.cv)
    }

}