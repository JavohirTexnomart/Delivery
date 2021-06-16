package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.service.UserService

object UserCommon {
    val retrofitService: UserService
        get() = Client.getClient(C.url1c).create(UserService::class.java)
}