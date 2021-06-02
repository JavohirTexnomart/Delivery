package mrj.example.deliverytexnomart.view

import android.content.Intent
import android.os.Bundle
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.databinding.ShiftActivityBinding

class ShiftActivity : BaseActivity() {

    lateinit var binding: ShiftActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShiftActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOpenShift.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }
        setActionBar(binding.includeToolbar.myToolbar)
    }

}