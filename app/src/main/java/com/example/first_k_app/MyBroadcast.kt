package com.example.first_k_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class MyBroadcast(
    val myFunc: ((String)-> Unit)
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("ANY_TAG", "ANYS STRING")
        myFunc.invoke(intent.getStringExtra(MY_KEY) ?: "")
    }
    companion object{
        const val MY_KEY = "KEY2"
        const val MY_ACTION = "MY_ACTION"
        val IntentFilter = IntentFilter(MY_ACTION)
    }
}