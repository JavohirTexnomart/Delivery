package mrj.example.deliverytexnomart.view

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.addPauseListener
import mrj.example.deliverytexnomart.BaseActivity
import mrj.example.deliverytexnomart.R
import mrj.example.deliverytexnomart.databinding.ShiftActivityBinding

class ShiftActivity : BaseActivity() {

    private val DEFAULT_ANIMATION_DURATION = 2500L
    lateinit var binding: ShiftActivityBinding
    lateinit var mRocket: View
    var mScreenHeight: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShiftActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOpenShift.setOnClickListener {
            enbaleLogin(false)
            mRocket = findViewById(R.id.rocket)
            val valueAnimator = ValueAnimator.ofFloat(0f, -mScreenHeight)
            valueAnimator.addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                mRocket.translationY = value
            }
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = DEFAULT_ANIMATION_DURATION
            valueAnimator.start()

            val handler = Handler()
            handler.postDelayed({
                openOrderList()
            }, DEFAULT_ANIMATION_DURATION)

        }
        setActionBar(binding.includeToolbar.myToolbar)
    }

    private fun openOrderList() {
        startActivity(Intent(this, OrdersActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        mScreenHeight = displaymetrics.heightPixels.toFloat()
    }

    private fun enbaleLogin(enable: Boolean) {
        binding.btnOpenShift.isEnabled = enable
    }
}