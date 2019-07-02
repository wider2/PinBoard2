package com.example.pinboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReceiverTest : BroadcastReceiver() {

    override fun onReceive(ctx: Context, i: Intent) {
        isNetworkAvailable()
    }

    @Test
    fun isNetworkAvailable() {
        var isConnected = false
        val context = InstrumentationRegistry.getTargetContext()

        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (j in info.indices) {
                    if (info[j].state == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(TAG, "Now you are connected to Internet")
                            isConnected = true
                        }
                    }
                }
            }
        }
        Log.v(TAG, "You are not connected to Internet")

        assertTrue(isConnected)
    }

    companion object {
        private val TAG = "ReceiverTest"
    }

}
