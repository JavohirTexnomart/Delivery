package mrj.example.deliverytexnomart.view

import android.os.Bundle
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.VerificationActivityBinding

class VerificationActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: VerificationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VerificationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)

        binding.etxtVerificationCode.setHint(R.string.text_verification_code)
    }

}