package com.example.iadiproject.api.Sponsor

import com.example.iadiproject.api.*
import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.services.SponsorService
import org.springframework.web.bind.annotation.*


@RestController
class SponsorController(val sponsors : SponsorService) : SponsorAPI {

    fun transformDAOIntoDTO(it: SponsorDAO): SponsorDTO{
        return SponsorDTO(it.id,it.name,it.contact,it.grantCalls.map{
            it.sponsor.id
        })
    }

    override fun getAll(): List<SponsorDTO> = sponsors.getAll().map { transformDAOIntoDTO(it) }


    override fun getOne(id: Long): SponsorDTO = transformDAOIntoDTO(sponsors.getOne(id))

}