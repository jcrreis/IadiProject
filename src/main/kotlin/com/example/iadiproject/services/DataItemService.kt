package com.example.iadiproject.services



import com.example.iadiproject.model.*

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class DataItemService(val dataItems: DataItemRepository, val grantCalls: GrantCallRepository, val dataItemAnswers: DataItemAnswerRepository) {


    fun addOne(dataItem: DataItem){
        dataItem.id = 0
        dataItems.save(dataItem)
    }

    @Transactional
    fun addGrantCallToDataItems(grantCall: GrantCallDAO){
        val dataItemsIds: MutableList<Long> = grantCall.dataItems.map{it.id} as MutableList<Long>
        val newDataItems: MutableList<DataItem> = mutableListOf()
        for(i in dataItemsIds){
           val dataItem: DataItem = this.dataItems.getOne(i)
           dataItem.grantCall = grantCall
           newDataItems.add(dataItem)
           this.dataItems.save(dataItem)
        }
        val grantCallDAO = grantCalls.findById(grantCall.id).get()
        grantCallDAO.dataItems = newDataItems
        this.grantCalls.save(grantCallDAO)
    }


}