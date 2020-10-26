package com.example.iadiproject.InstitutionTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.services.InstitutionService
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
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
    }



    @Test
    fun `test getOne`(){
        Assert.assertEquals(institutions.getAll().count(),1)
        var institutionGetter: InstitutionDAO = institutions.getOne(1)
        Assert.assertThat(institutionGetter.id, equalTo(institution.id))

    }

}