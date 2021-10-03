package com.bogsnebes.tinkoff

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyService : Service() {
    init {
        Log.d(TAG, "Service is running...")
        run()
    }

    @SuppressLint("Recycle")
    private fun run(): Int {
        Thread {
            val intent = Intent(SecondActivity.BROADCAST_ACTION)

            val contentResolver = contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentResolver.query(uri, null, null, null, null)
            Thread.sleep(1000)
            cursor?.count?.let {
                if (it > 0)
                    cursor.moveToNext()
                intent.putExtra(
                    SecondActivity.TEXT_FROM_SERVICE,
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                )
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                stopSelf()
            }
            intent.putExtra(SecondActivity.TEXT_FROM_SERVICE, "Hello from Service!")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            stopSelf()
        }.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    companion object {
        const val TAG = "MyService"
    }
}