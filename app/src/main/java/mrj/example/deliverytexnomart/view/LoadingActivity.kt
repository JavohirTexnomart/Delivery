package mrj.example.deliverytexnomart.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import mrj.example.deliverytexnomart.BuildConfig
import mrj.example.deliverytexnomart.databinding.LoadingActivityBinding

class LoadingActivity : BaseActivity() {

    lateinit var binding: LoadingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            txtVersion.text = BuildConfig.VERSION_NAME
        }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            for (item in 1..10) {
                Thread.sleep(200)
                setProgressValue(item * 10)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    private fun setProgressValue(progress: Int) {
        binding.pbLoading.progress = progress
    }
}