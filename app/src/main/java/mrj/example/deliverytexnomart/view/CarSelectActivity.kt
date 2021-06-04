package mrj.example.deliverytexnomart.view

import android.os.Bundle
import android.widget.ArrayAdapter
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.databinding.CarSelectActivityBinding
import mrj.example.deliverytexnomart.model.C

class CarSelectActivity : BaseActivity(homeDislpayEnabled = true) {

    lateinit var binding: CarSelectActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarSelectActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(toolbar = binding.includeToolbar.myToolbar)

        val cars_name = mutableListOf<String>()
        C.cars.forEach {
            it.name?.let { it1 -> cars_name.add(it1) }
        }
        binding.apply {
            rvListCars.adapter = ArrayAdapter(
                this@CarSelectActivity, android.R.layout.simple_list_item_single_choice,
                cars_name
            )
            btnContinue.setOnClickListener {
                val cur_car = C.cars.find {
                    it.name!! == rvListCars.getItemAtPosition(rvListCars.checkedItemPosition)
                        .toString()
                }
                if (cur_car != null) {
                    C.setSelectedCar(this@CarSelectActivity, cur_car)
                    finish()
                }
            }
        }

    }
}