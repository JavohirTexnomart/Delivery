package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.OrdersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersService {
    @GET("javoxir_ut_texnomart/hs/deliverymobapp/getorders")
    fun getOrders(
        @Query("code_client") code_client: String?
    ): Call<OrdersResponse>
}