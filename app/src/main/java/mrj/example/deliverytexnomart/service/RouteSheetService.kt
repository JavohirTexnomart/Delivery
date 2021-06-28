package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.OrdersResponse
import mrj.example.deliverytexnomart.model.RouteSheetsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteSheetService {
    @GET("RoznicaUT/hs/deliverymobapp/getRouteSheets")
    fun getRouteSheets(
        @Query("code_client") code_client: String?, @Query("code_car") code_car: String?
    ): Call<RouteSheetsResponse>
}