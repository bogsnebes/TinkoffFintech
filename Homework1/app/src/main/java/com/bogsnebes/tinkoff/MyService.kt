package com.bogsnebes.tinkoff

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyService : Service() {
    init {
        run()
    }

    private fun run(): Int {
        Thread {
            val intent = Intent(EXTRA_BROADCAST_ACTION)

            val contentResolver = contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentResolver.query(uri, null, null, null, null)
            Thread.sleep(1000)
            if (cursor?.count != null) {
                if (cursor.count > 0)
                    cursor.moveToNext()
                intent.putExtra(
                    EXTRA_TEXT_FROM_SERVICE,
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                )
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                stopSelf()
            } else {
                intent.putExtra(EXTRA_TEXT_FROM_SERVICE, "Hello from Service!")
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                stopSelf()
            }
        }.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    companion object {
        const val EXTRA_TEXT_FROM_SERVICE = "TEXT_FROM_SERVICE"
        const val EXTRA_BROADCAST_ACTION = "com.bogsnebes.tinkoff"

        fun createInstance(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}