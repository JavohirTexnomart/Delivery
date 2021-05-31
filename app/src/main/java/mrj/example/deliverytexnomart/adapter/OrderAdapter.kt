package mrj.example.deliverytexnomart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.databinding.OrderItemActivityBinding
import mrj.example.deliverytexnomart.holder.OrderHolder
import mrj.example.deliverytexnomart.model.Order


/**
 * Created by JavohirAI
 */

class OrderAdapter(var order_list: List<Order>) : RecyclerView.Adapter<OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val itemViewBinding =
            OrderItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val order = order_list.get(position)
        holder.bind(order)
    }

    override fun getItemCount(): Int = order_list.size
}