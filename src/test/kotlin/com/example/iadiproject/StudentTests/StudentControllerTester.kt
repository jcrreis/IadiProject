package com.example.iadiproject.StudentTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.StudentService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.google.gson.Gson


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTester {

    @Autowired
    lateinit var mvc: MockMvc

    //@MockBean
    //lateinit var reviewerRepository: ReviewerRepository

    @MockBean
    lateinit var students: StudentService


    companion object {
        const val studentsPath: String = "/students"
        val student = StudentDAO(1L,"joao","joao","joao","address", InstitutionDAO(1L,"FCT","FCT", mutableListOf()),ByteArray(0),mutableListOf())
        val gson: Gson = Gson()
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    fun `Test getOne()`() {
        Mockito.`when`(students.getOne(1L)).thenReturn(student)

        mvc.perform(get("$studentsPath/1"))
                .andExpect(status().isOk)
                .andReturn()

    }

    @Test
    @WithMockUser(username = "user", password = "password")
    fun `Test getAll()`() {
        Mockito.`when`(students.getAll()).thenReturn(emptyList())

        mvc.perform(get("$studentsPath"))
                .andExpect(status().isOk)
                .andReturn()
    }


    //GSON NOT WORKING
    @Test
    fun `Test addOne()`(){
        /*val jsonObject = gson.toJson(reviewer)
        mvc.perform(post(reviewersPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(status().isCreated)*/
    }
}