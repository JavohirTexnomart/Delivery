package mrj.example.deliverytexnomart.common

import mrj.example.deliverytexnomart.model.Client
import mrj.example.deliverytexnomart.model.ConstantsFile
import mrj.example.deliverytexnomart.service.UserService

object UserCommon {
    val retrofitService: UserService
        get() = Client.getClient(ConstantsFile.url_1c).create(UserService::class.java)
}