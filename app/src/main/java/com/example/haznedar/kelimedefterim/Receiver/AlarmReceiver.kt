package com.example.haznedar.kelimedefterim.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.karn.notify.Notify
import android.text.format.DateFormat
import com.example.haznedar.kelimedefterim.Service.AlarmService
import com.example.haznedar.kelimedefterim.Util.Constants
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXTACT_ALARM_TIME, 0L)

        when (intent.action){
            Constants.ACTION_SET_EXACT_ALARM ->{
                buildNotification(context, "Alıştırma Zamanı", convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_ALARM ->{
                setRepetitiveAlarm(AlarmService(context))
                buildNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))

            }

        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {

        Notify
            .with(context)
            .content {
                this.title = title
                this.text = "Eklediğiniz Kelimelerle Alıştırma Yapın!"
            }
            .show()
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
            Timber.d("Set alarm for next week same time - ${convertDate(this.timeInMillis)}")
        }
        alarmService.setRepetitiveAlarm(cal.timeInMillis)
    }

    private fun convertDate(timeInMillis : Long):String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}