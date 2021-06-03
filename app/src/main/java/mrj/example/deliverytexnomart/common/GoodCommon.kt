package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.service.OrderService

object GoodCommon {
    val retrofitService: OrderService
        get() = Client.getClient(C.url_1c).create(OrderService::class.java)
}