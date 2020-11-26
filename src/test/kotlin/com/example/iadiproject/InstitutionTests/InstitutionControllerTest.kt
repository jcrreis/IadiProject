package com.example.iadiproject.InstitutionTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.InstitutionRepository

import com.example.iadiproject.services.InstitutionService

import com.google.gson.Gson
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class InstitutionControllerTest {
    @Autowired
    lateinit var mvc: MockMvc


    @MockBean
    lateinit var institutions: InstitutionService


    companion object {
        const val institutionsPath: String = "/institutions"
        val institution = InstitutionDAO(1L,"FCT","FCT", mutableListOf())
        val gson: Gson = Gson()
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    fun `Test getOne()`() {
        Mockito.`when`(institutions.getOne(1L)).thenReturn(institution)

        mvc.perform(MockMvcRequestBuilders.get("$institutionsPath/1"))
                .andExpect(status().isOk)
                .andReturn()

    }

    @Test
    @WithMockUser(username = "user", password = "password")
    fun `Test getAll()`() {
        Mockito.`when`(institutions.getAll()).thenReturn(emptyList())

        mvc.perform(MockMvcRequestBuilders.get("$institutionsPath"))
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    @WithMockUser(username = "admin", password = "admin",authorities= arrayOf("ROLE_ADMIN"))
    fun `Test addOne()`(){
       val jsonObject = gson.toJson(institution)
        mvc.perform(post(institutionsPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(status().isCreated)
    }



}