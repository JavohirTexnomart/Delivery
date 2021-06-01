package mrj.example.deliverytexnomart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
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

    open var dialog_error_message = ""
    lateinit var txt_error: TextView
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

    override fun onCreateDialog(id: Int): Dialog {
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null)
        txt_error = view.findViewById(android.R.id.text1)
        if (id == errorid) {
            return AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.title_dialog_error))
                .setView(view)
                .setNegativeButton(android.R.string.ok, null)
                .create()
        }
        if (id.equals(connection_id)) {
            return AlertDialog.Builder(this)
                .setView(view)
                .create()
        }
        return super.onCreateDialog(id)
    }

    override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        txt_error.text = dialog_error_message
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
                intent.getParcelableExtra(C.ORDER_KEY) ?: Order("1", "1", "1", "1", "1", "1")
            if (!cur_order.number.equals("1") && !cur_order.address.equals("1"))
                title = resources.getString(titleId, cur_order.number)
        }
    }
}