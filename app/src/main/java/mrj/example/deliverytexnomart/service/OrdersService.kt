package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.GoodsResponse
import mrj.example.deliverytexnomart.model.OrdersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersService {
    @GET("javoxir_ut_texnomart/hs/deliverymobapp/getorders")
    fun getOrders(
        @Query("code_client") code_client: String?, @Query("code_car") code_car: String?,
        @Query("numberRouteSheet") numberRouteSheet: String?, @Query("dateRouteSheet") dateRouteSheet: String?
    ): Call<OrdersResponse>
}