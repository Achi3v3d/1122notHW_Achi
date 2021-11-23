package com.example.first_k_app

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception

class MyFirebaseService: FirebaseMessagingService() {
    private val notManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(baseContext)
    }

    // constructing our fire base service
    // extend the firebase messaging service
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(TAG,"new token....$p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        try {

            //let = if nulll do nothing if not null do
            remoteMessage.notification?.let {
                showNotification(it.title, it.body)
            }?: //if notification is null we show message from data
            showNotification(remoteMessage.data["title"], remoteMessage.data["message"])
            //display notification with message received
            //showNotification(remoteMessage.notification.title, remoteMessage.notification.body)
        } catch (e : Exception){
            Log.e(TAG, e.stackTraceToString())
        }
    }
//logic for  notification
    // attributes are nullables
    private fun showNotification(title: String?, body: String?) {
    NotificationCompat.Builder(baseContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(body)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
        .apply {
            // HERE we not for the new notification
            //apply block allows ups to handle this action with "this as the notification value
            notManager.notify(NOT_ID, this)
        }
    }

    companion object{
        const val TAG = "FirebaseService"
        const val NOT_ID = 423
    }
}