package com.example.simplenotification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import mad.project.gogoapp.R
import mad.project.gogoapp.ViewJobListDActivity
import java.text.SimpleDateFormat
import java.util.*

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationContentTv: TextView
    private lateinit var confirmBtn: Button
    private lateinit var declineBtn: Button

    private companion object {
        private const val CHANNEL_ID = "channel01"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notificationContentTv = findViewById(R.id.notificationContentTv)

        confirmBtn = findViewById(R.id.confirmBtn)
        declineBtn = findViewById(R.id.declineBtn)

        confirmBtn.setOnClickListener {
            showNotificationconfirm()
        }

        declineBtn.setOnClickListener {
            showNotificationDecline()
        }

        onNewIntent(intent)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val bundle: Bundle? = intent!!.extras

        if (bundle != null) {
            val name = bundle.getString("KEY_NAME")
            val type = bundle.getString("KEY_TYPE")

            Toast.makeText(this, "$name $type", Toast.LENGTH_LONG).show()
            notificationContentTv.text = "$name \n$type"
        }
    }

    private fun showNotificationconfirm() {
        createNotificationChannel()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        val mainIntent = Intent(this, NotificationActivity::class.java)

        mainIntent.putExtra("KEY_NAME", "GOGO")
        mainIntent.putExtra("KEY_TYPE", "Notification Content")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent =
            PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val callIntent = Intent(this, NotificationActivity::class.java)
        callIntent.putExtra("KEY_NAME", "GOGO")
        callIntent.putExtra("KEY_TYPE", "Call Button Clicked")
        val callPendingIntent =
            PendingIntent.getActivity(this, 2, callIntent, PendingIntent.FLAG_IMMUTABLE)

        val messageIntent = Intent(this, NotificationActivity::class.java)
        messageIntent.putExtra("KEY_NAME", "GOGO")
        messageIntent.putExtra("KEY_TYPE", "Message Button Clicked")
        val messagePendingIntent =
            PendingIntent.getActivity(this, 3, messageIntent, PendingIntent.FLAG_IMMUTABLE)


        val notificationBuilder = NotificationCompat.Builder(this, "${NotificationActivity.CHANNEL_ID}")

        notificationBuilder.setSmallIcon(R.drawable.logo3d)

        notificationBuilder.setContentTitle("Confirm This Job Request")

        notificationBuilder.setContentText("Connect Your Service Provider.")

        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        notificationBuilder.setAutoCancel(true)

        notificationBuilder.setContentIntent(mainPendingIntent)

        notificationBuilder.addAction(R.drawable.baseline_call_24, "Call", callPendingIntent)

        notificationBuilder.addAction(
            R.drawable.baseline_message_24,
            "Message",
            messagePendingIntent
        )


        val notificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }







        notificationManagerCompat.notify(notificationId, notificationBuilder.build())


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MyNotification"
            val description = "My notification channel description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel(NotificationActivity.CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    private fun showNotificationDecline() {
        createNotificationChannel2()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        val mainIntent = Intent(this, ViewJobListDActivity::class.java)

        mainIntent.putExtra("KEY_NAME", "GOGO")
        mainIntent.putExtra("KEY_TYPE", "Notification Content")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent =
            PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val callIntent = Intent(this, NotificationActivity::class.java)
        callIntent.putExtra("KEY_NAME", "GOGO")
        callIntent.putExtra("KEY_TYPE", "Call Button Clicked")
        val callPendingIntent =
            PendingIntent.getActivity(this, 2, callIntent, PendingIntent.FLAG_IMMUTABLE)

        val messageIntent = Intent(this, NotificationActivity::class.java)
        messageIntent.putExtra("KEY_NAME", "GOGO")
        messageIntent.putExtra("KEY_TYPE", "Message Button Clicked")
        val messagePendingIntent =
            PendingIntent.getActivity(this, 3, messageIntent, PendingIntent.FLAG_IMMUTABLE)


        val notificationBuilder = NotificationCompat.Builder(this, "${NotificationActivity.CHANNEL_ID}")

        notificationBuilder.setSmallIcon(R.drawable.logo3d)

        notificationBuilder.setContentTitle("Decline This Job Request")

        notificationBuilder.setContentText("Sorry, Your service provider declined this request.")

        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        notificationBuilder.setAutoCancel(true)

        notificationBuilder.setContentIntent(mainPendingIntent)

        notificationBuilder.addAction(R.drawable.baseline_call_24, "Call", callPendingIntent)

        notificationBuilder.addAction(
            R.drawable.baseline_message_24,
            "Message",
            messagePendingIntent
        )


        val notificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        notificationManagerCompat.notify(notificationId, notificationBuilder.build())

    }
    private fun createNotificationChannel2(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name:CharSequence = "MyNotification"
            val description = "My notification channel description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(NotificationActivity.CHANNEL_ID,name,importance)
            notificationChannel.description=description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }
}