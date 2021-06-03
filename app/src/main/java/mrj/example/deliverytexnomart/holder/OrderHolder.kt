package mrj.example.deliverytexnomart.holder

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.OrderItemActivityBinding
import mrj.example.deliverytexnomart.model.ConstantsFile
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

            imgOpenOrder.apply {
                setOnClickListener {
                    val context = this.context
                    val intent = Intent(context, OrderActivity::class.java)
                    intent.putExtra(ConstantsFile.ORDER_KEY, order)
                    context.startActivity(intent)
                }
            }

            imgCallClient.setOnClickListener {
                val context = itemViewbinding.root.context
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${order.phoneNumber}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            ConstantsFile.REQUEST_PHONE_CALL
                        );
                    } else {
                        context.startActivity(intent);
                    }
                } else {
                    context.startActivity(intent);
                }
            }
        }
    }

}