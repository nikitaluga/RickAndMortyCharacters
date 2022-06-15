package com.rickandmortycharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.rickandmortycharacters.databinding.ActivityMainBinding
import com.rickandmortycharacters.util.ConnectionType
import com.rickandmortycharacters.util.NetworkMonitorUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkStatusTracking()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    private fun networkStatusTracking() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Timber.i( "Wifi Connection")
                                isVisibleView(true)
                            }
                            ConnectionType.Cellular -> {
                                Timber.i("Cellular Connection")
                                isVisibleView(true)
                            }
                            else -> isVisibleView(false)
                        }
                    }
                    false -> {
                        isVisibleView(false)
                        Timber.i("No Connection")
                    }
                }
            }
        }
    }

    private fun isVisibleView(isVisible: Boolean) {
        with(binding) {
            containerNavGraph.isVisible = isVisible
            noInternet.isVisible = !isVisible
        }
    }
}