package com.example.iadiproject.services

import com.example.iadiproject.model.*
import org.springframework.stereotype.Service

@Service
class CurriculumService(val curriculums: CurriculumRepository,
                        val students: StudentRepository,
                        val cvItems: CVItemRepository )
{

  fun addOne(curriculum: CurriculumDAO) {
    for(i in curriculum.items){
      addOneCvItem(i)
    }
    curriculums.save(curriculum)
    for(i in curriculum.items){
      i.cv = curriculum
      cvItems.save(i)
    }
  }

  fun addOneCvItem(cvItem: CVItemDAO)  = cvItems.save(cvItem)

  fun addCvToStudent(curriculum: CurriculumDAO, idStudent: Long) {
    val student: StudentDAO = students.getOne(idStudent)
    if(student.cv == null) {
      student.cv = curriculum
      curriculum.student = student
      students.save(student)
      curriculums.save(curriculum)
    }
    else{
      throw ConflictException("This student with id $idStudent already have a cv.")
    }
  }
}

