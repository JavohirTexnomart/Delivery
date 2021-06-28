package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("RoznicaUT/hs/deliverymobapp/auth")
    fun getUser(@Query("login") login: String?, @Query("password") password: String?): Call<UserResponse>
}