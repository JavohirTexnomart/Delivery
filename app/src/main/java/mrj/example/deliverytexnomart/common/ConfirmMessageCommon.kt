package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.ConfirmMessageService

object ConfirmMessageCommon {
    val retrofitService: ConfirmMessageService
        get() = Client.getClient(C.url_1c).create(ConfirmMessageService::class.java)
}