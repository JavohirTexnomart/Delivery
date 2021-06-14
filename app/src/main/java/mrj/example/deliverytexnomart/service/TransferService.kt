package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.PostDataOrder
import mrj.example.deliverytexnomart.model.ResponseResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TransferService {
    @POST("javoxir_ut_texnomart/hs/deliverymobapp/transferorder")
    fun getResponse(@Body data: PostDataOrder): Call<ResponseResult>
}