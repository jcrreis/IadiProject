package com.example.iadiproject.InstitutionTests

import com.example.iadiproject.model.InstitutionDAO
import com.example.iadiproject.model.InstitutionRepository
import com.example.iadiproject.services.InstitutionService
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class InstitutionServiceTest{

    @Autowired
    lateinit var institutions: InstitutionService

    @MockBean
    lateinit var repo: InstitutionRepository


    companion object{
        val institution = InstitutionDAO(0,"FCT","fct@gmail.com", mutableListOf())
    }

    @Test
    fun `basic test on getAll`(){
        var listInstitutions = emptyList<InstitutionDAO>()
        Mockito.`when`(repo.findAll()).thenReturn(listInstitutions)
        Assert.assertEquals(institutions.getAll().count(),listInstitutions.count())
    }

    @Test
    fun `test addOne`(){
        Mockito.`when`(repo.save(Mockito.any(InstitutionDAO::class.java)))
                .then {
                    val inst:InstitutionDAO = it.getArgument(0)
                    Assert.assertEquals(inst.id, institution.id)
                    Assert.assertEquals(inst.name, institution.name)
                    Assert.assertEquals(inst.users, institution.users)
                    Assert.assertEquals(inst.contact, institution.contact)
                    inst
                }
        institutions.addOne(institution)
    }



    @Test
    fun `test getOne`(){
        Mockito.`when`(repo.findById(1L)).thenReturn(Optional.of(institution));
        Assert.assertEquals(institutions.getOne(1L), institution)

    }

}