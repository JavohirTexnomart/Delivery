package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.RefuseOrderService
import mrj.example.deliverytexnomart.service.TransferService

object RefuseTransferOrderCommon {
    val retrofitService: RefuseOrderService
        get() = Client.getClient(C.url1c).create(RefuseOrderService::class.java)

    val retrofitTransferService: TransferService
        get() = Client.getClient(C.url1c).create(TransferService::class.java)

}