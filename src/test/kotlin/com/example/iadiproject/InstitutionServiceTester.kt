package com.example.iadiproject

import com.example.iadiproject.services.InstitutionDAO
import com.example.iadiproject.services.InstitutionService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class InstitutionServiceTester{

    @Autowired
    lateinit var institutions: InstitutionService



    @Test
    fun `basic test on getAll`(){
        Assert.assertEquals(institutions.getAll().count(),0)
    }

    companion object{
        val institution = InstitutionDAO(0,"FCT","fct@gmail.com", mutableListOf())
    }

    @Test
    fun `test addOne`(){
        institutions.addOne(institution)
        Assert.assertEquals(institutions.getAll().count(),1)
        Assert.assertEquals(institutions.getOne(1),institution)
    }



    @Test
    fun `test getOne`(){
        institutions.addOne(institution)
        Assert.assertEquals(institutions.getAll().count(),1)
        Assert.assertEquals(institutions.getOne(2),institution)
        var institutionGetter: InstitutionDAO = institutions.getOne(2)
        Assert.assertEquals(institution, institutionGetter)
    }

}