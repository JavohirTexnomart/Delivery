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
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.GoodAdapter
import mrj.example.deliverytexnomart.common.GoodCommon
import mrj.example.deliverytexnomart.common.RefuseTransferOrderCommon
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
                ivPerson.isVisible = false
                chbGoodsReceived.isVisible = false
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
                btnTransferOrder.setOnClickListener {

                }
            }
            rvGoods.layoutManager = LinearLayoutManager(this@OrderActivity)
            rvGoods.adapter = GoodAdapter(goods)
            enableButtons()
        }
    }

    private fun enableButtons(enable: Boolean = true) {
        binding.rvGoods.adapter!!.notifyDataSetChanged()
        binding.apply {
            btnConfirmOrder.isEnabled = goods.size > 0 && enable
            btnRefuseOrder.isEnabled = goods.size > 0 && enable
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

        val goodsOfOrder = mutableListOf(resources.getString(R.string.text_all))
        goods.forEach {
            goodsOfOrder.add(it.name)
        }

        val checkedGoodsBoolean = BooleanArray(goodsOfOrder.size)
        AlertDialog.Builder(this)
            .setMultiChoiceItems(
                goodsOfOrder.toTypedArray(), checkedGoodsBoolean
            ) { _, which, isChecked ->
                checkedGoodsBoolean[which] = isChecked
            }
            .setCancelable(false)
            .setTitle(resources.getString(R.string.text_are_you_sure_you_want_to_cancel_your_order))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                enableButtons(false)
                var all = false
                val checkedgoods = mutableListOf<String>()
                for (i in checkedGoodsBoolean.indices) {
                    val checked = checkedGoodsBoolean[i]
                    if (checked) {
                        if (goodsOfOrder[i] == resources.getString(R.string.text_all)) {
                            all = true
                            break
                        } else {
                            checkedgoods.add(goodsOfOrder[i])
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
        val postData = PostDataOrder(
            number = order.number,
            date = order.date,
            numberRouteSheet = order.numberRouteSheet,
            dateRouteSheet = order.dateRouteSheet,
            all = all,
            goods = goods
        )

        RefuseTransferOrderCommon.retrofitService.getResponse(postData)
            .enqueue(object : Callback<ResponseResult> {
                override fun onResponse(
                    call: Call<ResponseResult>,
                    response: Response<ResponseResult>
                ) {
                    val refuseAdapter: ResponseResult
                    if (response.body() != null) {
                        refuseAdapter = (response.body() as ResponseResult)
                        val messageCode = refuseAdapter.message_code.toInt()
                        val myCallback = {
                            finish()
                            C.order_closed = true
                        }
                        catchExceptionShowDialog(messageCode, myCallback)
                        if (messageCode != 200) {
                            enableButtons()
                        }
                    } else {
                        enableButtons()
                    }
                }

                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                    toast("On failure ${t.message}")
                }
            })
    }

//    private fun transferOrders() {
//        val postData = PostDataOrder(
//            number = order.number,
//            date = order.date,
//            numberRouteSheet = order.numberRouteSheet,
//            dateRouteSheet = order.dateRouteSheet,
//            all = true,
//            goods = arrayOf()
//        )
//
//        RefuseTransferOrderCommon.retrofitTransferService.getResponse(postData)
//            .enqueue(object : Callback<ResponseResult> {
//                override fun onResponse(
//                    call: Call<ResponseResult>,
//                    response: Response<ResponseResult>
//                ) {
//                    val refuseAdapter: ResponseResult
//                    if (response.body() != null) {
//                        refuseAdapter = (response.body() as ResponseResult)
//                        val messageCode = refuseAdapter.message_code.toInt()
//                        val myCallback = {
//                            showOrders()
//                        }
//                        catchExceptionShowDialog(messageCode, myCallback)
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
//                    toast("On failure ${t.message}")
//                }
//            })
//    }

}