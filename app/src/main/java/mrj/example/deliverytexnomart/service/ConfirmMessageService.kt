package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.ConfirmMessage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfirmMessageService {
    @GET("javoxir_ut_texnomart/hs/deliverymobapp/confirmletter")
    fun getResponse(
        @Query("date") date: String?, @Query("number") number: String?,
        @Query("dateRouteSheet") dateRouteSheet: String?, @Query("numberRouteSheet") numberRouteSheet: String?,
    @Query("numberletter") numberletter: String?
    ): Call<ConfirmMessage>
}