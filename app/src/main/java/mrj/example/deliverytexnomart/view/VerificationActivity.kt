package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.common.ConfirmMessageCommon
import mrj.example.deliverytexnomart.databinding.VerificationActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.ConfirmMessage
import mrj.example.deliverytexnomart.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: VerificationActivityBinding
    var sendLetter = false
    lateinit var order: Order
    lateinit var adapter: ConfirmMessage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VerificationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)
        if (intent.extras != null) {
            order = intent.getParcelableExtra(C.ORDER_KEY_FOR_CONFIRM)!!
        }

        binding.apply {
            etxtVerificationCode.setHint(R.string.text_verification_code)

            etxtVerificationCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    enableConfirmButton(s.toString().length > 3)
                }
            })

            btnConfirmVerification.setOnClickListener {
                val confirmMessage = etxtVerificationCode.text.toString()
                sendLetter = true
                enableConfirmButton(!sendLetter)
                ConfirmMessageCommon.retrofitService.getResponse(
                    date = order.date,
                    number = order.number,
                    dateRouteSheet = order.dateRouteSheet,
                    numberRouteSheet = order.numberRouteSheet,
                    numberletter = confirmMessage
                ).enqueue(object : Callback<ConfirmMessage> {
                    override fun onFailure(call: Call<ConfirmMessage>, t: Throwable) {
                        Toast.makeText(
                            this@VerificationActivity,
                            "On failure ${t.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(
                        call: Call<ConfirmMessage>,
                        response: Response<ConfirmMessage>
                    ) {
                        if (response.body() != null) {
                            adapter = (response.body() as ConfirmMessage)
                            when (adapter.message_code.toInt()) {
                                resources.getInteger(R.integer.success) -> {
                                    C.order_closed = true
                                    finish()
                                }
                                resources.getInteger(R.integer.error_date_number_of_order_not_fill) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_date_number_of_order_not_fill)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                                resources.getInteger(R.integer.error_date_format_invalid) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_date_format_invalid)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                                resources.getInteger(R.integer.error_order_not_found) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_order_not_found)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                                resources.getInteger(R.integer.error_message_incorrect) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_message_incorrect)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                                resources.getInteger(R.integer.error_route_sheet_not_found) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_route_sheet_not_found)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                                resources.getInteger(R.integer.error_can_not_close_route_sheet) -> {
                                    showCustomDialog(
                                        resources.getString(R.string.error_can_not_close_route_sheet)
                                    )
                                    sendLetter = false
                                    enableConfirmButton(!sendLetter)
                                }
                            }
                        }
                    }
                })
                Handler(Looper.getMainLooper()).postDelayed({
                }, 2000)
            }
        }
        enableConfirmButton(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (sendLetter) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enableConfirmButton(enable: Boolean) {
        binding.btnConfirmVerification.isEnabled = enable
        binding.txtSendAgain.isEnabled = enable
    }

}