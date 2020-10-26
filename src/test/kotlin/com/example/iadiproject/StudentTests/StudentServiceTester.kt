package com.example.iadiproject.StudentTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.StudentService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import org.hamcrest.CoreMatchers.equalTo


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class StudentServiceTester{

    @Autowired
    lateinit var students: StudentService
    lateinit var institutions: InstitutionDAO

    @Test
    fun `basic test on getAll`(){
        Assert.assertEquals(students.getAll().count(),0)
    }
    companion object{
        val student = StudentDAO(0,"João","123","fct@gmail.com","Rua fct", InstitutionDAO(0,"Joao","262623231", mutableListOf()), mutableListOf(), "", mutableListOf())
    }

    @Test
    fun `test addOne`(){
        students.addOne(student)
        Assert.assertEquals(students.getAll().count(),1)
    }

    @Test
    fun `test getOne`(){
        Assert.assertEquals(students.getAll().count(),0)
        students.addOne(student)
        var studentGetter: StudentDAO
        studentGetter = students.getOne(2)
        Assert.assertThat(studentGetter,equalTo(student))



    }


}