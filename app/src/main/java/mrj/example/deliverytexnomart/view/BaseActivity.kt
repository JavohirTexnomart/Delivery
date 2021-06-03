package mrj.example.deliverytexnomart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.Order

/**
 * Created by JavohirAI
 */

open class BaseActivity(
    val homeDislpayEnabled: Boolean = false,
    val titleId: Int = R.string.empty_title,
    val menuResId: Int = 110,
    val displayLogoToolbar: Boolean = true,
    val myCallBack: () -> Unit = {}
) :
    AppCompatActivity() {

    var errorid = 1001
    var connection_id = 1002

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuResId == 110) {
            return super.onCreateOptionsMenu(menu)
        }
        menuInflater.inflate(menuResId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun toast(text: String,toast_length:Int=Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, toast_length).show()
    }

    fun showCustomDialog(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setNegativeButton(android.R.string.ok, null)
            .create().show()
    }

    @SuppressLint("StringFormatInvalid")
    fun setActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(homeDislpayEnabled)
            it.setDisplayUseLogoEnabled(displayLogoToolbar)
        }
        title = resources.getString(titleId)
        if (intent.extras != null) {
            val cur_order =
                intent.getParcelableExtra(C.ORDER_KEY) ?: Order()
            if (!cur_order.number.equals("1") && !cur_order.address.equals("1"))
                title = resources.getString(titleId, cur_order.number)
        }
    }
}