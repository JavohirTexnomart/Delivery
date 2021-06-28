package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.GoodsResponse
import mrj.example.deliverytexnomart.model.OrdersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderService {
    @GET("RoznicaUT/hs/deliverymobapp/orderdetails")
    fun getGoods(
        @Query("date") date: String?, @Query("number") number: String?
    ): Call<GoodsResponse>
}