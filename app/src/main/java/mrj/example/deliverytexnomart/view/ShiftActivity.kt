package mrj.example.deliverytexnomart.view

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.ShiftActivityBinding

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
            enbaleLogin()
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
                openOrderList()
            }, DEFAULTANIMATIONDURATION)

        }
        setActionBar(binding.includeToolbar.myToolbar)
    }

    private fun openOrderList() {
        startActivity(Intent(this, OrdersActivity::class.java))
        finish()
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

    private fun enbaleLogin(enable: Boolean = false) {
        binding.btnOpenShift.isEnabled = enable
    }
}