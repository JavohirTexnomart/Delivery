package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.ConstantsFile
import mrj.example.deliverytexnomart.service.OrderService

object GoodCommon {
    val retrofitService: OrderService
        get() = Client.getClient(ConstantsFile.url_1c).create(OrderService::class.java)
}