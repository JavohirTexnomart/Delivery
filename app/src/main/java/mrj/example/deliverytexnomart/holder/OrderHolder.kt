package mrj.example.deliverytexnomart.holder

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.OrderItemActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Order
import mrj.example.deliverytexnomart.view.OrderActivity


/**
 * Created by JavohirAI
 */

class OrderHolder(var itemViewbinding: OrderItemActivityBinding) :
    RecyclerView.ViewHolder(itemViewbinding.root) {

    fun bind(order: Order) {
        val resources = itemViewbinding.root.resources
        itemViewbinding.apply {
            txtNumber.text =
                resources.getString(R.string.text_number, order.number)
            txtDate.text =
                resources.getString(R.string.text_date, order.date)
            txtContactPerson.text = order.contactPerson
            txtPhone.text = order.phoneNumber
            txtAddress.text = order.address
            ivPerson.visibility = View.GONE
            imgOpenOrder.apply {
                setOnClickListener {
                    val context = this.context
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra(C.ORDER_KEY, order)
                    context.startActivity(intent)
                }
            }

            imgCallClient.setOnClickListener {
                OrderActivity.openCallDial(itemViewbinding.root.context, order.phoneNumber)
            }
        }
    }

}