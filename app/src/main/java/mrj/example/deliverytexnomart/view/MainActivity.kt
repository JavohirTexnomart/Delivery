package mrj.example.deliverytexnomart.view

import android.content.Intent
import android.os.Bundle
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.databinding.MainActivityBinding

class MainActivity : BaseActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, ShiftActivity::class.java))
        }
        setActionBar(binding.includeToolbar.myToolbar)
    }
}