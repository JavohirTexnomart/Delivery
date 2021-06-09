package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.OrderAdapter
import mrj.example.deliverytexnomart.common.OrdersCommon
import mrj.example.deliverytexnomart.databinding.OrdersActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Order
import mrj.example.deliverytexnomart.model.OrdersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JavohirAI
 */

class OrdersActivity : BaseActivity(menuResId = R.menu.orders_menu) {

    lateinit var adapter: OrdersResponse
    lateinit var orders: MutableList<Order>
    private lateinit var binding: OrdersActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = OrdersResponse()
        orders = mutableListOf()

        showOrders()
        bindingFull()

    }

    private fun showOrders() {
        orders.clear()
        OrdersCommon.retrofitService.getOrders(
            C.current_user.code_client,
            C.getNameSelectedCar(this)
        )
            .enqueue(object : Callback<OrdersResponse> {
                override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                    toast("On failure ${t.message}")
                }

                override fun onResponse(
                    call: Call<OrdersResponse>,
                    response: Response<OrdersResponse>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as OrdersResponse)
                        val messageCode = adapter.message_code.toInt()
                        val callback = {
                            orders.addAll(adapter.result)
                            bindingFull()
                        }
                        catchExceptionShowDialog(messageCode, callback)
                    }
                }
            })
        binding.apply {
            setActionBar(includeToolbar.myToolbar)
            rvOrders.layoutManager = LinearLayoutManager(this@OrdersActivity)
        }
    }

    private fun bindingFull() {
        binding.apply {
            rvOrders.adapter = OrderAdapter(order_list = orders)
            rvOrders.adapter!!.notifyDataSetChanged()
            constraintOrderStatus.findViewById<TextView>(R.id.txt_orders).text =
                resources.getString(R.string.text_orders_size, orders.size.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        if (C.order_closed) {
            showOrders()
            C.order_closed = !C.order_closed
        }
    }
}