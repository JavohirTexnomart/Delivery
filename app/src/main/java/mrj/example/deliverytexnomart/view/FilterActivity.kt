package mrj.example.deliverytexnomart.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import mrj.example.deliverytexnomart.databinding.FilterActivityBinding
import mrj.example.deliverytexnomart.model.C

class FilterActivity : BaseActivity(homeDislpayEnabled = true) {

    private lateinit var binding: FilterActivityBinding
    lateinit var filterList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(binding.includeToolbar.myToolbar)

        if (intent != null) {
            filterList = intent.getStringArrayExtra(C.keyFilterArray)!!.toList()
            binding.lvFilters.adapter =
                ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_single_choice,
                    filterList
                )
        }

        binding.lvFilters.setOnItemClickListener { parent, view, position, id ->
            val currentFilter = filterList.get(id.toInt())
            val data = Intent()
            data.putExtra(C.keySelectedSortFilterOrders, currentFilter)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}