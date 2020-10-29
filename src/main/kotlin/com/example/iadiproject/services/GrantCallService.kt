package com.example.iadiproject.services

import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.model.GrantCallRepository
import org.springframework.stereotype.Service

@Service
class GrantCallService(val grantCalls: GrantCallRepository) {

    fun getAll() : Iterable<GrantCallDAO> = grantCalls.findAll()

    fun getOne(id: Long): GrantCallDAO = grantCalls.findById(id).orElseThrow() {
        NotFoundException("Grant Call with id $id not found.")
    }

    fun addOne(grantCall: GrantCallDAO){
        grantCall.id = 0
        grantCalls.save(grantCall)
    }

    fun getAllGrantCallsBySponsorId(id: Long): Iterable<GrantCallDAO>{
        return grantCalls.findGrantCallDAOBySponsorId(id)
    }
}