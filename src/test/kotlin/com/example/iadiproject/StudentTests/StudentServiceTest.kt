package com.example.iadiproject.StudentTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.model.StudentRepository
import com.example.iadiproject.services.StudentService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class StudentServiceTest{

    @Autowired
    lateinit var students: StudentService


    @MockBean
    lateinit var repo: StudentRepository

    @Test
    fun `basic test on getAll`(){
        val listStudents = emptyList<StudentDAO>()
        Mockito.`when`(repo.findAll()).thenReturn(listStudents)
        Assert.assertEquals(students.getAll().count(),listStudents.count())

    }
    companion object{
        val student = StudentDAO(0,"João","123","fct@gmail.com","Rua fct", InstitutionDAO(0,"Joao","262623231", mutableListOf()), ByteArray(0), mutableListOf())
    }

    @Test
    fun `test addOne`(){
        Mockito.`when`(repo.save(Mockito.any(StudentDAO::class.java)))
                .then {
                    val std:StudentDAO = it.getArgument(0)
                    Assert.assertEquals(std.id, 0L)
                    Assert.assertEquals(std.name, student.name)
                    Assert.assertEquals(std.cv, student.cv)
                    Assert.assertEquals(std.institution, student.institution)
                    Assert.assertEquals(std.applications, student.applications)
                    Assert.assertEquals(std.address, student.address)
                    Assert.assertEquals(std.email, student.email)
                    std
                }
    }


}