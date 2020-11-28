package com.example.iadiproject.services

import com.example.iadiproject.model.DataItem
import com.example.iadiproject.model.DataItemRepository
import com.example.iadiproject.model.GrantCallDAO
import com.example.iadiproject.model.GrantCallRepository
import org.springframework.stereotype.Service

@Service
class GrantCallService(val grantCalls: GrantCallRepository, val dataItems: DataItemRepository) {

    fun getAll() : Iterable<GrantCallDAO> = grantCalls.findAll()

    fun getOne(id: Long): GrantCallDAO = grantCalls.findById(id).orElseThrow() {
        NotFoundException("Grant Call with id $id not found.")
    }

    fun addOne(grantCall: GrantCallDAO){
        grantCall.id = 0
        grantCalls.save(grantCall)
        //print(grantCalls.findAll())
    }

    fun getAllGrantCallsBySponsorId(id: Long): Iterable<GrantCallDAO>{
        return grantCalls.findGrantCallDAOBySponsorId(id)
    }
}