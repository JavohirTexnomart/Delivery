package mrj.example.deliverytexnomart.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.databinding.GoodItemActivityBinding
import mrj.example.deliverytexnomart.holder.GoodHolder
import mrj.example.deliverytexnomart.model.Good
import mrj.example.deliverytexnomart.view.OrderActivity


/**
 * Created by JavohirAI
 */

class GoodAdapter(val order_list: List<Good>) : RecyclerView.Adapter<GoodHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodHolder {
        val goodBinding =
            GoodItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodHolder(goodBinding = goodBinding)
    }

    override fun onBindViewHolder(holder: GoodHolder, position: Int) {
        holder.bind(order_list.get(position))
    }

    override fun getItemCount(): Int = order_list.size
}