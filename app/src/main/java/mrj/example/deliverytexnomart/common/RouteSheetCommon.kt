package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.RouteSheetService

object RouteSheetCommon {
    val retrofitService: RouteSheetService
        get() = Client.getClient(C.url1c).create(RouteSheetService::class.java)
}