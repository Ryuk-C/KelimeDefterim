package com.example.haznedar.kelimedefterim.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.Service.AlarmService
import kotlinx.android.synthetic.main.hesabim_layout.*
import java.util.*

class HesabimFragment : Fragment()

{

    lateinit var  alarmService : AlarmService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflat2 = inflater.inflate(R.layout.hesabim_layout, container, false)
        return inflat2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmService = AlarmService(requireContext())
        btnBildirim.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarm(timeInMillis)}
        }
        btnCikisYap()

    }


    fun btnCikisYap() {

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)


        btnCikisYap.setOnClickListener {

            val intent = Intent(this.context, GirisActivity::class.java)
            startActivity(intent)
            val editor = sp?.edit()
            editor?.remove("kullaniciAdi")
            editor?.remove("kullaniciSifre")
            editor?.commit()

        }
    }

    private fun setAlarm(callback : (Long) -> Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND,0)
            this.set(Calendar.MILLISECOND,0)

            DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)

                    TimePickerDialog(
                        requireContext(),
                        0,
                        { _, hour, min ->

                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, min)
                            callback(this.timeInMillis)

                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()


                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH),
            ).show()

        }
    }
}