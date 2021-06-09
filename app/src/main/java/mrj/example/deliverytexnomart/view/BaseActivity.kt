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
    val displayLogoToolbar: Boolean = true
) :
    AppCompatActivity() {

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

    fun toast(text: String, toast_length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, toast_length).show()
    }

    fun catchExceptionShowDialog(resourceId: Int, callBack: () -> Unit) {
        when (resourceId) {
            resources.getInteger(R.integer.success) -> {
                callBack()
            }
            resources.getInteger(R.integer.error_field_incorrect) -> {
                showCustomDialog(
                    resources.getString(R.string.error_user_not_found)
                )
            }
            resources.getInteger(R.integer.error_user_not_found) -> {
                showCustomDialog(
                    resources.getString(R.string.error_user_not_found)
                )
            }
            resources.getInteger(R.integer.error_client_not_found) -> {
                showCustomDialog(
                    resources.getString(R.string.error_client_not_found)
                )
            }
            resources.getInteger(R.integer.error_date_number_of_order_not_fill) -> showCustomDialog(
                resources.getString(R.string.error_user_not_found)
            )
            resources.getInteger(R.integer.error_date_format_invalid) -> showCustomDialog(
                resources.getString(R.string.error_user_not_found)
            )
        }
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
            val curOrder =
                intent.getParcelableExtra(C.ORDER_KEY) ?: Order()
            if (!curOrder.number.equals("1") && !curOrder.address.equals("1"))
                title = resources.getString(titleId, curOrder.number)
        }
    }
}