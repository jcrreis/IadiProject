package com.example.iadiproject


import com.example.iadiproject.services.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
class ReviewerServiceTester{

    @Autowired
    lateinit var reviewers: ReviewerService



    @Test
    fun `basic test on getAll`(){
        Assert.assertEquals(reviewers.getAll().count(),0)
    }

    companion object{
        val reviewer = ReviewerDAO(0,"João","123","joao@gmail.com","Rua 123", InstitutionDAO(0,"FCT","fct@gmail.com", mutableListOf()), mutableListOf(), mutableListOf())
    }

    @Test
    fun `test addOne`(){
        reviewers.addOne(reviewer)
        Assert.assertEquals(reviewers.getAll().count(),1)
        Assert.assertEquals(reviewers.getOne(1),reviewer)
    }



    @Test
    fun `test getOne`(){
        Assert.assertEquals(reviewers.getAll().count(),1)
        var reviewerGetter: ReviewerDAO = reviewers.getOne(1)
        Assert.assertEquals(StudentServiceTester.student.id,reviewerGetter.id)
    }

}