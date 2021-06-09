package mrj.example.deliverytexnomart.view

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.GoodAdapter
import mrj.example.deliverytexnomart.common.GoodCommon
import mrj.example.deliverytexnomart.common.RefuseOrderCommon
import mrj.example.deliverytexnomart.databinding.OrderActivityBinding
import mrj.example.deliverytexnomart.model.*
import retrofit2.*
import java.util.*


/**
 * Created by JavohirAI
 */

@Suppress("DEPRECATION")
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
                    toast("On failure ${t.message}")
                }

                override fun onResponse(
                    call: Call<GoodsResponse>,
                    response: Response<GoodsResponse>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as GoodsResponse)
                        val messageCode = adapter.message_code.toInt()
                        val myCallBack = {
                            goods.addAll(adapter.result)
                            enableButtons()
                        }
                        catchExceptionShowDialog(messageCode, myCallBack)
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

        val goods_of_order = mutableListOf(resources.getString(R.string.text_all))
        goods.forEach {
            goods_of_order.add(it.name)
        }

        val checkedGoodsBoolean = BooleanArray(goods_of_order.size)
        AlertDialog.Builder(this)
            .setMultiChoiceItems(
                goods_of_order.toTypedArray(), checkedGoodsBoolean
            ) { _, which, isChecked ->
                checkedGoodsBoolean[which] = isChecked
            }
            .setCancelable(false)
            .setTitle(resources.getString(R.string.text_are_you_sure_you_want_to_cancel_your_order))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                var all = false
                val checkedgoods = mutableListOf<String>()
                for (i in checkedGoodsBoolean.indices) {
                    val checked = checkedGoodsBoolean[i]
                    if (checked) {
                        if (goods_of_order[i] == resources.getString(R.string.text_all)) {
                            all = true
                            break
                        } else {
                            checkedgoods.add(goods_of_order[i])
                        }
                    }
                }
                refuseOrder(all, checkedgoods.toTypedArray())
            }
            .setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun refuseOrder(all: Boolean, goods: Array<String>) {
        val post_data = PostDataRefuseOrder(
            number = order.number,
            date = order.date,
            numberRouteSheet = order.numberRouteSheet,
            dateRouteSheet = order.dateRouteSheet,
            all = all,
            goods = goods
        )

        RefuseOrderCommon.retrofitService.getResponse(post_data)
            .enqueue(object : Callback<RefuseOrder> {
                override fun onResponse(call: Call<RefuseOrder>, response: Response<RefuseOrder>) {
                    val refuseAdapter: RefuseOrder
                    if (response.body() != null) {
                        refuseAdapter = (response.body() as RefuseOrder)
                        val myCallback = {
                            enableButtons()
                        }
                        catchExceptionShowDialog(refuseAdapter.message_code.toInt(), myCallback)
                    }
                }

                override fun onFailure(call: Call<RefuseOrder>, t: Throwable) {
                    toast("On failure ${t.message}")
                }
            })
    }

}