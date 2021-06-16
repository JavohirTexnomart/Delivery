package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.service.OrdersService

object OrdersCommon {
    val retrofitService: OrdersService
        get() = Client.getClient(C.url1c).create(OrdersService::class.java)
}