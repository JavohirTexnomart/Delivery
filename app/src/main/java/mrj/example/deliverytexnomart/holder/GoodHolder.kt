package mrj.example.deliverytexnomart.holder

import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.GoodItemActivityBinding
import mrj.example.deliverytexnomart.model.Good


/**
 * Created by JavohirAI
 */

class GoodHolder(var goodBinding: GoodItemActivityBinding) :
    RecyclerView.ViewHolder(goodBinding.root) {

    fun bind(good: Good) {
        goodBinding.apply {
            txtAmount.text = good.amount.toString()
            txtName.text = good.name
            txtPrice.text = good.price.toString()
            txtSum.text = good.sum.toString()
        }
    }

}