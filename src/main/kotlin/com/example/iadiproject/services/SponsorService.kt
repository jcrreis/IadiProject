package com.example.iadiproject.services


import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.model.SponsorRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SponsorService(val sponsors: SponsorRepository, val users: UserService) {

    fun getAll() : Iterable<SponsorDAO> = sponsors.findAll()

    fun getOne(id: Long): SponsorDAO = sponsors.findById(id).orElseThrow() {
        NotFoundException("Application with id $id not found.")
    }

    /*fun addOne(sponsor: SponsorDAO){
        sponsor.id = 0
        val encryptedPass: String = BCryptPasswordEncoder().encode(sponsor.password)
        sponsor.password = encryptedPass
        sponsors.save(sponsor)
    }*/
}