package mrj.example.deliverytexnomart.view

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.common.ShiftChangeCommon
import mrj.example.deliverytexnomart.databinding.ShiftActivityBinding
import mrj.example.deliverytexnomart.model.C
import mrj.example.deliverytexnomart.model.PostDataShiftChange
import mrj.example.deliverytexnomart.model.ResponseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShiftActivity : BaseActivity() {

    private val DEFAULTANIMATIONDURATION = 2500L
    private lateinit var binding: ShiftActivityBinding
    private lateinit var mRocket: View
    private var mScreenHeight: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShiftActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOpenShift.setOnClickListener {
            openOrderList()
        }
        setActionBar(binding.includeToolbar.myToolbar)
    }

    private fun clickListenerToOpenShift() {
        enableLoginBtn()
        mRocket = findViewById(R.id.rocket)
        val valueAnimator = ValueAnimator.ofFloat(0f, -mScreenHeight)
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            mRocket.translationY = value
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = DEFAULTANIMATIONDURATION
        valueAnimator.start()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this, RouteSheetActivity::class.java))
            finish()
        }, DEFAULTANIMATIONDURATION)
    }

    private fun openOrderList() {
        val myCallback = {
            clickListenerToOpenShift()
        }
        val body = PostDataShiftChange(C.current_user.login, C.current_user.password, "open")
        ShiftChangeCommon.retrofitService.getResponse(body)
            .enqueue(object : Callback<ResponseResult> {
                override fun onResponse(
                    call: Call<ResponseResult>,
                    response: Response<ResponseResult>
                ) {
                    if (response.body() != null) {
                        val currentAdapter = (response.body() as ResponseResult)
                        val messageCode = currentAdapter.message_code.toInt()
                        catchExceptionShowDialog(messageCode, myCallback)
                    }
                }

                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                    toast("On failure ${t.message}")
                    enableLoginBtn(true)
                }

            })
    }

    override fun onResume() {
        super.onResume()
        val displayMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(displayMetrics)
        }
        mScreenHeight = displayMetrics.heightPixels.toFloat()
    }

    private fun enableLoginBtn(enable: Boolean = false) {
        binding.btnOpenShift.isEnabled = enable
    }
}