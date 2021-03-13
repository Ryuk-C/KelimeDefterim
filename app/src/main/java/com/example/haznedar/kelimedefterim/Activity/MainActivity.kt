package com.example.haznedar.kelimedefterim.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.haznedar.kelimedefterim.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_Main.add(MeowBottomNavigation.Model(1, R.drawable.list))
        bnv_Main.add(MeowBottomNavigation.Model(2, R.drawable.akademi))
        bnv_Main.add(MeowBottomNavigation.Model(3, R.drawable.stream))
        bnv_Main.add(MeowBottomNavigation.Model(4, R.drawable.person))
        bnv_Main.show(1,true)
        replace(ListelerimFragment())

        bnv_Main.setOnClickMenuListener { model: MeowBottomNavigation.Model ->
            when (model.id){
                1 -> replace(ListelerimFragment())
                2 -> replace(AkademiFragment())
                3 -> replace(HesabimFragment())
                4 -> replace(HesabimFragment())
            }
            null
        }

    }

    private fun replace(fragment: Fragment) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentTutucu, fragment)
        transaction.commit()
    }



    /*
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

     */

}