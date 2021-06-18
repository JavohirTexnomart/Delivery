package mrj.example.deliverytexnomart.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.adapter.RouteSheetAdapter
import mrj.example.deliverytexnomart.common.RouteSheetCommon
import mrj.example.deliverytexnomart.databinding.RoutesheetActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.RouteSheet
import mrj.example.deliverytexnomart.model.RouteSheetsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteSheetActivity : BaseActivity() {

    lateinit var binding: RoutesheetActivityBinding
    lateinit var routeSheetList: MutableList<RouteSheet>
    lateinit var adapter: RouteSheetsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RoutesheetActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        routeSheetList = mutableListOf()
        routeSheetList.clear()

        binding.apply {
            setActionBar(includeToolbar.myToolbar)
            rvRouteSheets.layoutManager = LinearLayoutManager(this@RouteSheetActivity)
        }
        bind()
        showRouteSheets()

    }

    private fun bind() {
        binding.apply {
            rvRouteSheets.adapter = RouteSheetAdapter(routeSheetList)
            rvRouteSheets.adapter!!.notifyDataSetChanged()
        }
    }

    private fun showRouteSheets(){
        RouteSheetCommon.retrofitService.getRouteSheets(
            C.current_user.code_client,
            C.getNameSelectedCar(this)
        )
            .enqueue(object : Callback<RouteSheetsResponse> {
                override fun onFailure(call: Call<RouteSheetsResponse>, t: Throwable) {
                    toast("On failure ${t.message}")
                }

                override fun onResponse(
                    call: Call<RouteSheetsResponse>,
                    response: Response<RouteSheetsResponse>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as RouteSheetsResponse)
                        val messageCode = adapter.message_code.toInt()
                        val callback = {
                            routeSheetList.addAll(adapter.result)
                            bind()
                        }
                        catchExceptionShowDialog(messageCode, callback)
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        if (C.order_closed) {
            showRouteSheets()
            C.routesheet_closed = !C.routesheet_closed
        }
    }
}