package com.example.haznedar.kelimedefterim.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haznedar.kelimedefterim.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttomBar()


    }

    fun buttomBar() {

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentTutucu, ListelerimFragment()).commit()

        buttomBar1.setOnNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.listelerim_bnb) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentTutucu, ListelerimFragment()).commit()
            }

            if (menuItem.itemId == R.id.akademi_bnb) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentTutucu, AkademiFragment()).commit()
            }

            if (menuItem.itemId == R.id.hesabim_bnb) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentTutucu, HesabimFragment()).commit()
            }
            true
        }
    }
}