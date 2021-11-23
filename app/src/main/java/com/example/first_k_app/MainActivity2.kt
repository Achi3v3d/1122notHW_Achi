package com.example.first_k_app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.first_k_app.databinding.ActivityMain2Binding
import com.example.first_k_app.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding


    private val broadcastManager: LocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(baseContext)
    }
    private val notManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(baseContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private val myBroadcast = MyBroadcast{
    //Log.d("MainActivity2", it)
        // notificationBuilder
        val notificationBuilder = createNotification(it).build()
        notManager.notify(5,notificationBuilder)
    }
    override fun onStart() {
        super.onStart()
        broadcastManager.registerReceiver(myBroadcast,MyBroadcast.IntentFilter)
    }

    override fun onResume() {
        super.onResume()




        createNotificationChannel()

        val ss:String=intent.getStringExtra("DATA").toString()
        binding.textView.text = intent.getStringExtra("DATA").toString()

        binding.button2.setOnClickListener(){
            val myIntent = Intent(baseContext,MyBroadcast::class.java).apply {
            putExtra("KEY2",intent.getStringExtra("DATA"))
                action = MyBroadcast.MY_ACTION


                // we are creating our file to save data in shared preferences
                //this data is stored locally in android device memory
                val sharedPreferences = baseContext.getSharedPreferences("MY_SHARED_PREFS", MODE_PRIVATE)
                // editor is needed to write data in shared preferences
                // in order to write to the preference you need to use . apply{}
                // put oporator everything needs a key
                val editor = sharedPreferences.edit().apply{
                    putString("EMAIL_KEY", binding.textView.text.toString())
                    putString("udokwu.maduabuchi@gmail.com", "TESTing")
                    /*clear the whole file
                    sharedPreferences.edit().clear().apply()
                    //clear a specific value
                    sharedPreferences.edit().remove("EMAIL_KEY").apply()*/
                    val allKeys = sharedPreferences.all.keys.toString()
                    Log.d("key_value", allKeys)
                }.apply()

               val mString =  sharedPreferences.getString("EMAIL_KEY", null)
            }
                 broadcastManager.sendBroadcast(myIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        broadcastManager.unregisterReceiver(myBroadcast)
    }
    fun createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOT_NAME
            val descriptionText = NOT_DESC
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


    }


    private fun createNotification(text :String): NotificationCompat.Builder =
            NotificationCompat.Builder(baseContext, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("This is a content title $text")
                .setContentText("this is the content text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    companion object{
        const val CHANNEL_ID = "CHANNEL_ID"
        const val NOT_NAME = "SAMPLE_NOT"
        const val NOT_DESC = "SAMPLE_DESC"
    }


}
