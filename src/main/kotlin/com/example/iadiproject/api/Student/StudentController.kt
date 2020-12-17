package com.example.iadiproject.api.Student

import com.example.iadiproject.api.*
import com.example.iadiproject.model.CVItemDAO
import com.example.iadiproject.model.CurriculumDAO
import com.example.iadiproject.services.InstitutionService
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.StudentService
import com.example.iadiproject.services.UserService
import org.apache.catalina.valves.StuckThreadDetectionValve
import org.springframework.web.bind.annotation.*

@RestController
class StudentController(val students: StudentService,
                        val institutions: InstitutionService,
                        val users: UserService) : StudentAPI
{

    fun transformDAOIntoDTO(it: StudentDAO): StudentDTO{
        return StudentDTO(it.id,it.name,it.email,it.address,SimpleInstitutionDTO(it.institution.id,it.institution.name,
            it.institution.contact),"", it.cv?.let { it1 -> CurriculumDTO(it1.id,it1.items.map
            { it1 -> CVItemDTO(it1.id,it1.item,it1.value) }) })
    }

    override fun getAll(): List<StudentDTO> = students.getAll().map { transformDAOIntoDTO(it) }

    override fun getOne(id: Long): StudentDTO = transformDAOIntoDTO(students.getOne(id))

    override fun addCvToStudent(id: Long, cv: CurriculumDTO) = students.addCvToStudent(CurriculumDAO(cv.id,cv.items.map{
        CVItemDAO(it.id,it.item,it.value)
    }),id)

    override fun updateStudentCV(id: Long, cv: CurriculumDTO) = students.updateStudentCV(CurriculumDAO(cv.id,cv.items.map{
        CVItemDAO(it.id,it.item,it.value)
    }), id)


}