package mrj.example.deliverytexnomart.service

import mrj.example.deliverytexnomart.model.PostDataShiftChange
import mrj.example.deliverytexnomart.model.ResponseResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ShiftChangeService {
    @POST("RoznicaUT/hs/deliverymobapp/changeshiftstatus")
    fun getResponse(@Body data: PostDataShiftChange): Call<ResponseResult>
}