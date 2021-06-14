package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
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
                        toast("On failure ${t.message}")
                    }
                    override fun onResponse(
                        call: Call<ConfirmMessage>,
                        response: Response<ConfirmMessage>
                    ) {
                        if (response.body() != null) {
                            adapter = (response.body() as ConfirmMessage)
                            val messageCode = adapter.message_code.toInt()
                            val callBack = {
                                C.order_closed = true
                                finish()
                            }
                            catchExceptionShowDialog(messageCode, callBack)
                            if (messageCode != 200) {
                                sendLetter = false
                                enableConfirmButton(!sendLetter)
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