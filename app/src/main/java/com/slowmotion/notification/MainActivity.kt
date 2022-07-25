package com.slowmotion.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import com.slowmotion.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private val channel_id = "channel_1"

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //register channel ke dalam sistem
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channel_id, "countdown", "Notif when countdown end")

        binding.btnStart.setOnClickListener {
            countDownTimer.start()
        }

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                binding.timer.text = getString(R.string.being_watered, p0 / 1000)
            }

            override fun onFinish() {
                displayNotification()
            }
        }


    }


    private fun displayNotification(){
        val notificationId = 45
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this@MainActivity, channel_id)
            .setContentTitle("Thank you")
            .setContentText("come tomorrow")
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        //validasi notif akan dibuat jika version sdk 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) run {
            //set interupsi notifikasi
            val importance = NotificationManager.IMPORTANCE_HIGH
            //membuat channel
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            //mendaftarkan channel ke dalam sistem notification manager
            notificationManager?.createNotificationChannel(channel)
        }

    }


}