package com.example.iadiproject.services


import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.model.SponsorRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SponsorService(val sponsors: SponsorRepository,
                     val users: UserService)
{

    fun getAll() : Iterable<SponsorDAO> = sponsors.findAll()

    fun getOne(id: Long): SponsorDAO = sponsors.findById(id).orElseThrow() {
        NotFoundException("Sponsor with id $id not found.")
    }

    fun getSponsorByName(name: String): SponsorDAO{
        return sponsors.findSponsorDAOByName(name).orElseThrow(){
            NotFoundException("Sponsor with name $name not found.")
        }
    }

}