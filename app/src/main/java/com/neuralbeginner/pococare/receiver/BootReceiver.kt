package com.neuralbeginner.pococare.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO: Handle boot completed
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // Start metrics collection service
            }
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                // Handle app updates
            }
        }
    }
}