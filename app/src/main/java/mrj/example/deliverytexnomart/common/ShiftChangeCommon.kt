package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.ShiftChangeService

object ShiftChangeCommon {
    val retrofitService: ShiftChangeService
        get() = Client.getClient(C.url_1c).create(ShiftChangeService::class.java)
}