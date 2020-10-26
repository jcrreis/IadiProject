package com.example.iadiproject.ReviewerTests


import com.example.iadiproject.StudentTests.StudentServiceTester
import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.InstitutionRepository
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.services.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class ReviewerServiceTester{

    @Autowired
    lateinit var reviewers: ReviewerService

    lateinit var institutions: InstitutionRepository


    @Test
    fun `basic test on getAll`(){
        Assert.assertEquals(reviewers.getAll().count(),0)
    }

    companion object{
        val reviewer = ReviewerDAO(0,"João","123","joao@gmail.com","Rua 123", InstitutionDAO(1,"FCT","fct@gmail.com", mutableListOf()), mutableListOf(), mutableListOf(), mutableListOf())
    }

    @Test
    fun `test addOne`(){
        reviewers.addOne(reviewer)
        Assert.assertEquals(reviewers.getAll().count(),1)
        Assert.assertEquals(reviewers.getOne(1), reviewer)
    }



    @Test
    fun `test getOne`(){
        Assert.assertEquals(reviewers.getAll().count(),1)
        var reviewerGetter: ReviewerDAO = reviewers.getOne(1)
        Assert.assertEquals(StudentServiceTester.student.id,reviewerGetter.id)
    }

}