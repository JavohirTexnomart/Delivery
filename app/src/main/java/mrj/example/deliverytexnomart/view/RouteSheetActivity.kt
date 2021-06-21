package mrj.example.deliverytexnomart.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.RouteSheetAdapter
import mrj.example.deliverytexnomart.common.RouteSheetCommon
import mrj.example.deliverytexnomart.databinding.RoutesheetActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.RouteSheet
import mrj.example.deliverytexnomart.model.RouteSheetsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteSheetActivity : BaseActivity(menuResId = R.menu.route_sheet_menu) {

    lateinit var binding: RoutesheetActivityBinding
    lateinit var routeSheetList: MutableList<RouteSheet>
    lateinit var currentFilter: String
    lateinit var adapter: RouteSheetsResponse
    lateinit var filterList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RoutesheetActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        routeSheetList = mutableListOf()
        routeSheetList.clear()

        filterList = mutableListOf()
        filterList.add(resources.getString(R.string.text_filter_ascending))
        filterList.add(resources.getString(R.string.text_filter_descending))
        currentFilter = filterList.get(0)


        binding.apply {
            setActionBar(includeToolbar.myToolbar)
            rvRouteSheets.layoutManager = LinearLayoutManager(this@RouteSheetActivity)
        }
        bind()
        showRouteSheets()

    }

    private fun bind() {
        routeSheetList.sortBy {
            it.dateInNumber
        }
        if (currentFilter != filterList[0]) {
            routeSheetList.reverse()
        }

        binding.apply {
            rvRouteSheets.adapter = RouteSheetAdapter(routeSheetList)
            rvRouteSheets.adapter!!.notifyDataSetChanged()
        }
    }

    private fun showRouteSheets() {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myCallback = {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        when (item.itemId) {
            R.id.item_filter -> {
                openFilterActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFilterActivity() {
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra(C.keyFilterArray, filterList.toTypedArray())
        filterActivityLauncher.launch(intent)
    }

    var filterActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if (data != null) {
                currentFilter = data.getStringExtra(C.keySelectedSortFilterOrders).toString()
                bind()
            }
        }
    }

}