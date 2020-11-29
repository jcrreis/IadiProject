package com.example.iadiproject.StudentTests

import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.services.InstitutionService
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
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var institutions: InstitutionService

    @MockBean
    lateinit var students: StudentService


    companion object {
        const val studentsPath: String = "/students"
        val institution = InstitutionDAO(1L,"","", mutableListOf())
        val student = StudentDAO(1L,"joao","joao","joao","address", institution,ByteArray(0),mutableListOf())
    }

    @Test
    @WithMockUser(username = "joao", password = "joao", authorities=["ROLE_REVIEWER"])
    fun `Test getOne()`() {
        Mockito.`when`(students.getOne(1L)).thenReturn(student)

        mvc.perform(get("$studentsPath/1"))
                .andExpect(status().isOk)
                .andReturn()

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= ["ROLE_REVIEWER"])
    fun `Test getAll()`() {
        Mockito.`when`(students.getAll()).thenReturn(emptyList())
        mvc.perform(get("$studentsPath"))
                .andExpect(status().isOk)
                .andReturn()
    }

    fun <T>nonNullAny(t:Class<T>):T = Mockito.any(t)


}