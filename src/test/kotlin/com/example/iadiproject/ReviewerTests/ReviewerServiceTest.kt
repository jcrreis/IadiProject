package com.example.iadiproject.ReviewerTests


import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.model.ReviewerRepository
import com.example.iadiproject.services.*
import com.google.common.base.Predicates.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.mockito.Mockito
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class ReviewerServiceTest{

    @Autowired
    lateinit var reviewers: ReviewerService

    @Autowired
    lateinit var users: UserService


    @MockBean
    lateinit var repo: ReviewerRepository

    @Test
    fun `basic test on getAll`(){
        var listReviewers = emptyList<ReviewerDAO>()
        Mockito.`when`(repo.findAll()).thenReturn(listReviewers)
        Assert.assertEquals(reviewers.getAll().count(),listReviewers.count())
    }

    companion object{
        val institution = InstitutionDAO(0,"FCT","FCT", mutableListOf())
        val reviewer = ReviewerDAO(0,"João","123","joao@gmail.com","Rua 123",institution, mutableListOf(), mutableListOf())
    }




    @Test
    fun `test getOne`(){
        Mockito.`when`(repo.findById(1L)).thenReturn(Optional.of(reviewer));
        Assert.assertEquals(reviewers.getOne(1L), reviewer)

    }

}