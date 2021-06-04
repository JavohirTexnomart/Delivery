package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.VerificationActivityBinding

class VerificationActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: VerificationActivityBinding
    var sendLetter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VerificationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)

        binding.etxtVerificationCode.setHint(R.string.text_verification_code)

        binding.btnConfirmVerification.setOnClickListener {
            sendLetter = true
            enableConfirmButton()
            val handler = Handler()
            handler.postDelayed({
                sendLetter = false
                enableConfirmButton()
            }, 2000)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (sendLetter) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enableConfirmButton() {
        binding.btnConfirmVerification.isEnabled = !sendLetter
        binding.txtSendAgain.isEnabled = !sendLetter
    }

}