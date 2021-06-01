package mrj.example.deliverytexnomart.view

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.adapter.GoodAdapter
import mrj.example.deliverytexnomart.databinding.OrderActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Good
import mrj.example.deliverytexnomart.model.Order


/**
 * Created by JavohirAI
 */

class OrderActivity : BaseActivity(
    titleId = R.string.title_order,
    homeDislpayEnabled = true,
    displayLogoToolbar = false
) {

    private lateinit var cur_order: Order
    private lateinit var goods: MutableList<Good>
    private lateinit var binding: OrderActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goods = mutableListOf()
        if (intent.extras != null) {
            goods.add(Good("Планшет Realme", 1, 1500000.0, 1500000.0))
            goods.add(Good("Зарядник от Iphone", 2, 300000.0, 600000.0))
            goods.add(Good("Монитор х24", 1, 1500000.0, 1500000.0))
            goods.add(Good("Доставка на 7 этах ", 1, 30000.0, 30000.0))
            cur_order = intent.getParcelableExtra(C.ORDER_KEY)!!
            bind()
        }
        setActionBar(binding.includeToolbar.myToolbar)
    }

    private fun bind() {
        binding.apply {
            includeItem.apply {
                txtNumber.text = cur_order.number
                txtDate.text = cur_order.date
                txtContactPerson.text = cur_order.contactPerson
                txtPhone.text = cur_order.phoneNumber
                txtAddress.text = cur_order.address
                imgOpenOrder.isVisible = false
            }
            rvGoods.layoutManager = LinearLayoutManager(this@OrderActivity)
            rvGoods.adapter = GoodAdapter(goods)
        }
    }
}