package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.VerificationActivityBinding
import mrj.example.deliverytexnomart.model.C

class VerificationActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: VerificationActivityBinding
    var sendLetter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VerificationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)

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
                sendLetter = true
                enableConfirmButton(!sendLetter)
                Handler(Looper.getMainLooper()).postDelayed({
                    sendLetter = false
                    enableConfirmButton(!sendLetter)
                    C.order_closed = true
                    finish()
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