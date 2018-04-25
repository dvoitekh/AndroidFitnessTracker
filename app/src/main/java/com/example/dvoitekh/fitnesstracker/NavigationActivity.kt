package com.example.dvoitekh.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.dvoitekh.fitnesstracker.fragments.EditFragment
import com.example.dvoitekh.fitnesstracker.fragments.StatsFragment
import com.example.dvoitekh.fitnesstracker.fragments.TodayFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_today -> {
                val fragment = TodayFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stats -> {
                val fragment = StatsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_edit -> {
                val fragment = EditFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val serviceIntent = Intent(this, BackgroundIntentService::class.java)
        startService(serviceIntent)

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        val fragment = TodayFragment.Companion.newInstance()
//        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
//        supportFragmentManager
//                .beginTransaction()
//                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
//                .addToBackStack(fragment.javaClass.getSimpleName())
//                .commit()
    }
}
