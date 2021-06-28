package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.PostDataOrder
import mrj.example.deliverytexnomart.model.ResponseResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefuseOrderService {
    @POST("RoznicaUT/hs/deliverymobapp/refuseorder")
    fun getResponse(@Body data: PostDataOrder): Call<ResponseResult>
}