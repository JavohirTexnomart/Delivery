package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.OrderAdapter
import mrj.example.deliverytexnomart.databinding.OrdersActivityBinding
import mrj.example.deliverytexnomart.model.Order

/**
 * Created by JavohirAI
 */

class OrdersActivity : BaseActivity(homeDislpayEnabled = true, menuResId = R.menu.orders_menu) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = OrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orders = mutableListOf<Order>()
        val order = Order(
            number = "TM-044-3235",
            date = "22.04.2021",
            address = "г. Ташкент, Яккасарайскийрайон, ул. Шота Руставели, дом 45",
            contactPerson = "Кадыров Азамат Журабек угли",
            phoneNumber = "+998 90 1156969",
            phoneNumberInFormat = "998901156969"
        )
        for (i in 0..6) {
            orders.add(order)
        }
        binding.apply {
            rvOrders.layoutManager = LinearLayoutManager(this@OrdersActivity)
            rvOrders.adapter = OrderAdapter(order_list = orders)
            setActionBar(includeToolbar.myToolbar)
            constraintOrderStatus.findViewById<TextView>(R.id.txt_orders).text =
                resources.getString(R.string.text_orders_size, orders.size.toString())
        }

    }

}