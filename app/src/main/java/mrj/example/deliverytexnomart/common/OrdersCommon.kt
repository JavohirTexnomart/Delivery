package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.ConstantsFile
import mrj.example.deliverytexnomart.service.OrdersService

object OrdersCommon {
    val retrofitService: OrdersService
        get() = Client.getClient(ConstantsFile.url_1c).create(OrdersService::class.java)
}