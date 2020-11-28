package com.example.iadiproject.api.Sponsor

import com.example.iadiproject.api.*
import com.example.iadiproject.model.DataItem
import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.services.SponsorService
import org.springframework.web.bind.annotation.*
import javax.xml.crypto.Data


@RestController
class SponsorController(val sponsors : SponsorService) : SponsorAPI {

    override fun getAll(): List<SponsorDTO> {
        var sponsorsList = sponsors.getAll()

        return sponsorsList.map { SponsorDTO(it.id,it.name,it.contact,it.grantCalls.map{
            it.sponsor.id
        }) }
    }

    override fun getOne(id: Long): SponsorDTO = sponsors.getOne(id).let { SponsorDTO(it.id,it.name,it.contact,it.grantCalls.map{
       it.sponsor.id
    }) }

}