package com.example.iadiproject.services


import com.example.iadiproject.model.InstitutionRepository
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.model.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(val students: StudentRepository, val institutions: InstitutionRepository) {

    fun getAll() : Iterable<StudentDAO> = students.findAll()

    fun getOne(id: Long): StudentDAO = students.findById(id).orElseThrow() {
        NotFoundException("Student with id $id not found.")
    }

    fun addOne(student: StudentDAO){
        student.id = 0
        students.save(student)
    }
}