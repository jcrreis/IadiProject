package com.example.iadiproject.api.Sponsor

import com.example.iadiproject.api.AddSponsorDTO
import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.SponsorDTO
import com.example.iadiproject.services.SponsorDAO
import com.example.iadiproject.services.SponsorService
import org.springframework.web.bind.annotation.*


@RestController
class SponsorController(val sponsors : SponsorService) : SponsorAPI {

    override fun getAll(): List<SponsorDTO> {
        var sponsorsList = sponsors.getAll()

        return sponsorsList.map { SponsorDTO(it.id,it.name,it.contact,it.grantCalls.map{
            it1->GrantCallDTO(it1.id,it1.title,it1.description,it1.requirements,it1.funding,it1.openingDate,it1.closingDate,it1.dataItems,it1.sponsor.id,
               1)
        }) }
    }

    override fun getOne(id: Long): SponsorDTO = sponsors.getOne(id).let { SponsorDTO(it.id,it.name,it.contact,it.grantCalls.map{
        it->GrantCallDTO(it.id,it.title,it.description,it.requirements,it.funding,it.openingDate,it.closingDate,it.dataItems,it.sponsor.id,
            1)
    }) }

    override fun addOne(sponsor: AddSponsorDTO) {
        sponsors.addOne(SponsorDAO(sponsor.id,sponsor.name,sponsor.contact))
    }




}