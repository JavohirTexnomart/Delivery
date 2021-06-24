package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.view.MenuItem
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.ConfirmRefuseResponseLayoutBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.OrderOperation

class ResponseActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: ConfirmRefuseResponseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfirmRefuseResponseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)

        if (intent != null) {
            val currentItem = (intent.extras?.get(C.keyResponseOrder)) as OrderOperation
            when (currentItem) {
                OrderOperation.TRANSFER -> {
                    binding.ivResponse.setImageResource(R.drawable.ic_vector_done_transfer)
                    binding.txtResponse.setText(R.string.text_transfer_order_response)
                }
                OrderOperation.CONFIRM -> {
                    binding.ivResponse.setImageResource(R.drawable.ic_vector_done_transfer)
                    binding.txtResponse.setText(R.string.text_confirm_order_response)
                }
                OrderOperation.REFUSE -> {
                    binding.ivResponse.setImageResource(R.drawable.ic_vector)
                    binding.txtResponse.setText(R.string.text_refuse_order_response)
                }
            }
        }
        binding.btnCloseActivity.setOnClickListener {
            C.order_closed = true
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                C.order_closed = true
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}