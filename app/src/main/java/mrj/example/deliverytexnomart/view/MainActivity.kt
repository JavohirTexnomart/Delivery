package mrj.example.deliverytexnomart.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.common.UserCommon
import mrj.example.deliverytexnomart.databinding.MainActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.User
import mrj.example.deliverytexnomart.model.UserResonse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding
    lateinit var adapter: UserResonse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(toolbar = binding.includeToolbar.myToolbar)


        binding.btnLogin.setOnClickListener {
            enbaleLogin(false)
            val login = binding.etxtEmail.text.toString()
            val password = binding.etxtPwd.text.toString()

            if (checkFieldIsEmpty(login, binding.etxtEmail) || checkFieldIsEmpty(
                    password,
                    binding.etxtPwd
                )
            ) {
                return@setOnClickListener
            }

            adapter = UserResonse()
            UserCommon.retrofitService.getUser(login, password)
                .enqueue(object : Callback<UserResonse> {
                    override fun onFailure(call: Call<UserResonse>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "On failure ${t.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        enbaleLogin(true)
                    }

                    override fun onResponse(
                        call: Call<UserResonse>,
                        response: Response<UserResonse>
                    ) {
                        if (response.body() != null) {
                            adapter = (response.body() as UserResonse)
                            when (adapter.message_code.toInt()) {
                                resources.getInteger(R.integer.success) -> {
                                    val user = adapter.result
                                    openShiftOrOrdersAndRegisterUserInC(user)
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
                    }
                })
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

    private fun enbaleLogin(enable: Boolean) {
        binding.btnLogin.isEnabled = enable
    }

}