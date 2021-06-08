package mrj.example.deliverytexnomart.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.GoodAdapter
import mrj.example.deliverytexnomart.common.GoodCommon
import mrj.example.deliverytexnomart.databinding.OrderActivityBinding
import mrj.example.deliverytexnomart.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created by JavohirAI
 */

class OrderActivity : BaseActivity(
    titleId = R.string.title_order,
    homeDislpayEnabled = true,
    displayLogoToolbar = false
) {
    lateinit var adapter: GoodsResponse
    private lateinit var order: Order
    private lateinit var goods: MutableList<Good>
    private lateinit var binding: OrderActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goods = mutableListOf()
        if (intent.extras != null) {
            order = intent.getParcelableExtra(C.ORDER_KEY)!!
            bind()
        }
        GoodCommon.retrofitService.getGoods(order.date, order.number)
            .enqueue(object : Callback<GoodsResponse> {
                override fun onFailure(call: Call<GoodsResponse>, t: Throwable) {
                    Toast.makeText(
                        this@OrderActivity,
                        "On failure ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<GoodsResponse>,
                    response: Response<GoodsResponse>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as GoodsResponse)
                        when (adapter.message_code.toInt()) {
                            resources.getInteger(R.integer.success) -> {
                                goods.addAll(adapter.result)
                                enableButtons()
                            }
                            resources.getInteger(R.integer.error_date_number_of_order_not_fill) -> showCustomDialog(
                                resources.getString(R.string.error_user_not_found)
                            )
                            resources.getInteger(R.integer.error_date_format_invalid) -> showCustomDialog(
                                resources.getString(R.string.error_user_not_found)
                            )
                        }

                    }
                }
            })
        setActionBar(binding.includeToolbar.myToolbar)
    }

    private fun bind() {
        binding.apply {
            includeItem.apply {
                txtNumber.text = order.number
                txtDate.text = order.date
                txtContactPerson.text = order.contactPerson
                txtPhone.text = order.phoneNumber
                txtAddress.text = order.address
                imgOpenOrder.isVisible = false
                imgCallClient.setOnClickListener {
                    openCallDial(binding.root.context, order.phoneNumber)
                }
                btnConfirmOrder.setOnClickListener {
                    val intent = Intent(this@OrderActivity, VerificationActivity::class.java)
                    intent.putExtra(C.ORDER_KEY_FOR_CONFIRM, order)
                    startActivity(intent)
                }
                btnRefuseOrder.setOnClickListener {
                    showDialogRefuseOrder()
                }
            }
            rvGoods.layoutManager = LinearLayoutManager(this@OrderActivity)
            rvGoods.adapter = GoodAdapter(goods)
            enableButtons()
        }
    }

    private fun enableButtons() {
        binding.rvGoods.adapter!!.notifyDataSetChanged()
        binding.apply {
            btnConfirmOrder.isEnabled = goods.size > 0
            btnRefuseOrder.isEnabled = goods.size > 0
            txtTotal.text = goods.sumByDouble {
                it.sum
            }.toString()
        }

    }

    companion object {
        fun openCallDial(context: Context, phoneNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${phoneNumber}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        C.REQUEST_PHONE_CALL
                    )
                } else {
                    context.startActivity(intent)
                }
            } else {
                context.startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (C.order_closed) {
            finish()
        }
    }

    private fun showDialogRefuseOrder() {

        val colors = mutableListOf(resources.getString(R.string.text_all))
        goods.forEach {
            colors.add(it.name)
        }

        val checkedColors = BooleanArray(colors.size)
        AlertDialog.Builder(this)
            .setMultiChoiceItems(
                colors.toTypedArray(), checkedColors
            ) { dialog, which, isChecked ->
                checkedColors[which] = isChecked
            }
            .setCancelable(false)
            .setTitle(resources.getString(R.string.text_are_you_sure_you_want_to_cancel_your_order))
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                for (i in checkedColors.indices) {
                    val checked = checkedColors[i]
                    if (checked) {
                        toast(colors[i])
                    }
                }
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.cancel()
            }
            .create()
            .show()

    }
}