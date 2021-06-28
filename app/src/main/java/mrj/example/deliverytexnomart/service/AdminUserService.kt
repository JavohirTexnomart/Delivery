package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.AdminUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AdminUserService {
    @GET("RoznicaUT/hs/deliverymobapp/getcars")
    fun getCars(
        @Query("login") login: String?, @Query("password") password: String?
    ): Call<AdminUserResponse>
}