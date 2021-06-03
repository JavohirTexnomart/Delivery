package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.service.AdminUserService

object AdminUserCommon {
    val retrofitService: AdminUserService
        get() = Client.getClient(C.url_1c).create(AdminUserService::class.java)
}