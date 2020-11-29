package com.example.iadiproject.ReviewerTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.services.InstitutionService

import com.example.iadiproject.services.ReviewerService
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


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewerControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    //@MockBean
    //lateinit var reviewerRepository: ReviewerRepository
    @MockBean
    lateinit var institutions: InstitutionService
    @MockBean
    lateinit var reviewers: ReviewerService


    companion object {
        const val reviewersPath: String = "/reviewers"
        val reviewer = ReviewerDAO(1L,"joao","joao","joao","address", InstitutionDAO(1L,"FCT","FCT", mutableListOf()),mutableListOf(), mutableListOf())
  }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= ["ROLE_REVIEWER"])
    fun `Test getOne()`() {
        Mockito.`when`(reviewers.getOne(1L)).thenReturn(reviewer)

        mvc.perform(get("$reviewersPath/1"))
                .andExpect(status().isOk)
                .andReturn()

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= ["ROLE_REVIEWER"])
    fun `Test getAll()`() {
        Mockito.`when`(reviewers.getAll()).thenReturn(emptyList())

        mvc.perform(get("$reviewersPath"))
                .andExpect(status().isOk)
                .andReturn()
    }



}
