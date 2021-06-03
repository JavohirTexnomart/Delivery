package mrj.example.deliverytexnomart.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.common.AdminUserCommon
import mrj.example.deliverytexnomart.common.UserCommon
import mrj.example.deliverytexnomart.databinding.MainActivityBinding
import mrj.example.deliverytexnomart.model.AdminUserResponse
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.User
import mrj.example.deliverytexnomart.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding
    lateinit var userAdapter: UserResponse
    lateinit var adminUserAdapter: AdminUserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(toolbar = binding.includeToolbar.myToolbar)

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
                        Toast.makeText(
                            this@MainActivity,
                            "On failure ${t.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
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

        }
    }

    private fun checkResultOfBody(messageCode: Int, isUserAccount: Boolean) {
        when (messageCode) {
            resources.getInteger(R.integer.success) -> {
                if (isUserAccount) {
                    val user = userAdapter.result
                    openShiftOrOrdersAndRegisterUserInC(user)
                } else {
                    val cars = adminUserAdapter.result
                    openForSelectCar()
                }
            }
            resources.getInteger(R.integer.error_user_not_found) -> {
                showCustomDialog(
                    resources.getString(R.string.error_user_not_found)
                )
                enbaleLogin(true)
            }
            resources.getInteger(R.integer.error_field_incorrect) -> {
                showCustomDialog(
                    resources.getString(R.string.error_user_not_found)
                )
                enbaleLogin(true)
            }
        }
    }

    private fun checkFieldIsEmpty(text: String, editText: EditText): Boolean {
        if (text.isEmpty()) {
            editText.error = resources.getString(R.string.error_field_empty)
        }
        return text.isEmpty()
    }

    fun openShiftOrOrdersAndRegisterUserInC(user: User) {
        C.current_user = user
        startActivity(Intent(this, ShiftActivity::class.java))
        finish()
    }

    fun openForSelectCar() {
        toast("get cars")
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
            ) { dialog, which ->
                checkAdminUserProfile(etxtLogin.text.toString(), etxtPwd.text.toString())
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create().show()
    }

    private fun checkAdminUserProfile(login: String, password: String) {
        AdminUserCommon.retrofitService.getCars(login, password)
            .enqueue(object : Callback<AdminUserResponse> {
                override fun onFailure(call: Call<AdminUserResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "On failure ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    enbaleLogin(true)
                }

                override fun onResponse(
                    call: Call<AdminUserResponse>,
                    response: Response<AdminUserResponse>
                ) {
                    if (response.body() != null) {
                        adminUserAdapter = (response.body() as AdminUserResponse)
                        checkResultOfBody(adminUserAdapter.message_code.toInt(), false)
                    }
                }
            })
    }

    private fun checkFields(): Boolean =
        checkFieldIsEmpty(binding.etxtText.text.toString(), binding.etxtText) || checkFieldIsEmpty(
            binding.etxtPwd.text.toString(),
            binding.etxtPwd
        )

}