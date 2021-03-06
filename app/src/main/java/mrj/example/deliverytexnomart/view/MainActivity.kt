package mrj.example.deliverytexnomart.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.common.AdminUserCommon
import mrj.example.deliverytexnomart.common.UserCommon
import mrj.example.deliverytexnomart.databinding.MainActivityBinding
import mrj.example.deliverytexnomart.model.AdminUserResponse
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var userAdapter: UserResponse
    lateinit var adminUserAdapter: AdminUserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(toolbar = binding.includeToolbar.myToolbar)

        binding.etxtText.setText(C.getUserName(this))
        binding.imgOpenShowdialog.setOnClickListener {
            showDialogConfirmAdmin()
        }

        binding.btnLogin.setOnClickListener {
            val login = binding.etxtText.text.toString()
            val password = binding.etxtPwd.text.toString()

            if (checkFields()) {
                return@setOnClickListener
            }
            enbaleLogin(false)

            userAdapter = UserResponse()
            UserCommon.retrofitService.getUser(login, password)
                .enqueue(object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        toast("On failure ${t.message}")
                        enbaleLogin(true)
                    }

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        processResponseBody(response)
                    }
                })
        }
    }

    private fun processResponseBody(response: Response<UserResponse>) {
        if (response.body() != null) {
            userAdapter = (response.body() as UserResponse)
            checkResultOfBody(userAdapter.message_code.toInt(), true)
        } else {
            enbaleLogin(true)
            catchExceptionShowDialog(R.integer.error_can_not_connect, {})
        }
    }

    private fun checkResultOfBody(messageCode: Int, isUserAccount: Boolean) {
        val myCallBack = {
            if (isUserAccount) {
                val user = userAdapter.result
                C.current_user = user
                if (user.status == "open") {
                    C.setUserName(this, user.login)
                    startActivity(Intent(this, RouteSheetActivity::class.java))
                    finish()
                } else {
                    C.setUserName(this, user.login)
                    startActivity(Intent(this, ShiftActivity::class.java))
                    finish()
                }
            } else {
                C.cars.addAll(adminUserAdapter.result)
                openForSelectCar()
            }
        }
        catchExceptionShowDialog(messageCode, myCallBack)
        if (messageCode != 200) {
            enbaleLogin(true)
        }
    }

    private fun checkFieldIsEmpty(text: String, editText: EditText): Boolean {
        if (text.isEmpty()) {
            editText.error = resources.getString(R.string.error_field_empty)
        }
        return text.isEmpty()
    }

    private fun openForSelectCar() {
        if (C.cars.size > 0) {
            startActivity(Intent(this, CarSelectActivity::class.java))
        }
    }

    private fun enbaleLogin(enable: Boolean) {
        binding.btnLogin.isEnabled = enable
    }

    private fun showDialogConfirmAdmin() {
        val customView = layoutInflater.inflate(R.layout.login_layout, null)
        val etxtLogin = customView.findViewById<EditText>(R.id.etxt_text)
        val etxtPwd = customView.findViewById<EditText>(R.id.etxt_pwd)
        AlertDialog.Builder(this)
            .setView(customView)
            .setTitle(resources.getString(R.string.text_login_as_admin))
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                checkAdminUserProfile(etxtLogin.text.toString(), etxtPwd.text.toString())
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create().show()
    }

    private fun checkAdminUserProfile(login: String, password: String) {
        AdminUserCommon.retrofitService.getCars(login, password)
            .enqueue(object : Callback<AdminUserResponse> {
                override fun onFailure(call: Call<AdminUserResponse>, t: Throwable) {
                    toast("On failure ${t.message}")
                    enbaleLogin(true)
                }

                override fun onResponse(
                    call: Call<AdminUserResponse>,
                    response: Response<AdminUserResponse>
                ) {
                    if (response.body() != null) {
                        adminUserAdapter = (response.body() as AdminUserResponse)
                        checkResultOfBody(adminUserAdapter.message_code.toInt(), false)
                    }else {
                        enbaleLogin(true)
                        catchExceptionShowDialog(R.integer.error_can_not_connect, {})
                    }
                }
            })
    }

    private fun checkFields(): Boolean =
        checkFieldIsEmpty(binding.etxtText.text.toString(), binding.etxtText) || checkFieldIsEmpty(
            binding.etxtPwd.text.toString(),
            binding.etxtPwd
        )

    override fun onResume() {
        super.onResume()
        val carName = C.getSelectedCar(this).name
        if (carName != "10000111") {
            binding.txtCar.text = carName
        }
    }
}