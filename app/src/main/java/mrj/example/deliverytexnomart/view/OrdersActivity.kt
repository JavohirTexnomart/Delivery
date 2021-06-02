package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.OrderAdapter
import mrj.example.deliverytexnomart.common.OrdersCommon
import mrj.example.deliverytexnomart.common.UserCommon
import mrj.example.deliverytexnomart.databinding.OrdersActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Order
import mrj.example.deliverytexnomart.model.OrdersResponse
import mrj.example.deliverytexnomart.model.UserResonse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JavohirAI
 */

class OrdersActivity : BaseActivity(menuResId = R.menu.orders_menu) {

    lateinit var adapter: OrdersResponse
    lateinit var orders: MutableList<Order>
    lateinit var binding: OrdersActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = OrdersResponse()
        orders = mutableListOf<Order>()

        OrdersCommon.retrofitService.getOrders(C.current_user.code_client)
            .enqueue(object : Callback<OrdersResponse> {
                override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                    Toast.makeText(
                        this@OrdersActivity,
                        "On failure ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<OrdersResponse>,
                    response: Response<OrdersResponse>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as OrdersResponse)
                        when (adapter.message_code.toInt()) {
                            resources.getInteger(R.integer.success) -> {
                                orders.addAll(adapter.result)
                                bindingFull()
                            }
                            resources.getInteger(R.integer.error_user_not_found) -> showCustomDialog(
                                resources.getString(R.string.error_user_not_found)
                            )
                            resources.getInteger(R.integer.error_field_incorrect) -> showCustomDialog(
                                resources.getString(R.string.error_user_not_found)
                            )
                        }

                    }
                }
            })
        binding.apply {
            setActionBar(includeToolbar.myToolbar)
            rvOrders.layoutManager = LinearLayoutManager(this@OrdersActivity)
        }
        bindingFull()

    }

    private fun OrdersActivity.bindingFull() {
        binding.apply {
            rvOrders.adapter = OrderAdapter(order_list = orders)
            rvOrders.adapter!!.notifyDataSetChanged()
            constraintOrderStatus.findViewById<TextView>(R.id.txt_orders).text =
                resources.getString(R.string.text_orders_size, orders.size.toString())
        }
    }

}