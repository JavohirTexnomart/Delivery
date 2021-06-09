package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.PostDataRefuseOrder
import mrj.example.deliverytexnomart.model.RefuseOrder
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefuseOrderService {
    @POST("javoxir_ut_texnomart/hs/deliverymobapp/refuseorder")
    fun getResponse(@Body data: PostDataRefuseOrder): Call<RefuseOrder>
}