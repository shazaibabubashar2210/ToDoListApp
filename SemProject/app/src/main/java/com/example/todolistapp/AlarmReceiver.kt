package com.example.todolistapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Vibrator
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Trigger a notification or sound when the alarm goes off
        Toast.makeText(context, "Task Time! Don't forget: ${intent.getStringExtra("task_name")}", Toast.LENGTH_LONG).show()

        // Optional: Play sound
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val r = RingtoneManager.getRingtone(context, notification)
        r.play()

        // Optional: Vibrate
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
    }
}
