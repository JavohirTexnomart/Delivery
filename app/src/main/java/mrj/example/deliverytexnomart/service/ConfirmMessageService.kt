package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.ConfirmMessage
import mrj.example.deliverytexnomart.model.PostDataConfirmOrder
import mrj.example.deliverytexnomart.model.PostDataOrder
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ConfirmMessageService {
    @POST("javoxir_ut_texnomart/hs/deliverymobapp/confirmletter")
    fun getResponse(
        @Body data: PostDataConfirmOrder
    ): Call<ConfirmMessage>
}