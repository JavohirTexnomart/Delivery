package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.RefuseOrderService

object RefuseOrderCommon {
    val retrofitService: RefuseOrderService
        get() = Client.getClient(C.url_1c).create(RefuseOrderService::class.java)
}