package com.example.haznedar.kelimedefterim.Service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.haznedar.kelimedefterim.Receiver.AlarmReceiver
import com.example.haznedar.kelimedefterim.Util.Constants
import com.example.haznedar.kelimedefterim.Util.RandomIntUtil

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?


    fun setExactAlarm(timeInMillis: Long) {

        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {

                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXTRA_EXTACT_ALARM_TIME, timeInMillis)
                }

            )
        )

    }

    fun setRepetitiveAlarm(timeInMillis: Long) {

        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {

                    action = Constants.ACTION_SET_REPETITIVE_ALARM
                    putExtra(Constants.EXTRA_EXTACT_ALARM_TIME, timeInMillis)
                }

            )
        )

    }

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            getRandomRequestCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {

        alarmManager?.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )

            } else {

                alarmManager.setExact(

                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }

        }

    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)


    private fun getRandomRequestCode() = RandomIntUtil.getRandomInt()
}