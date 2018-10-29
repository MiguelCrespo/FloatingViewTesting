package com.example.miguel.floatingview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create default notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "asdas"
            val channelName = "asdas"
            val defaultChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(defaultChannel)
        }

        button1.setOnClickListener {
            startService(Intent(this, ChatHeadService::class.java))
        }

        button2.setOnClickListener {
            stopService(Intent(this, ChatHeadService::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askPermission() {
        val packageName = this.packageName

        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package: ${packageName}"))
        startActivity(intent)
    }
}
