package com.github.enteraname74.transposer.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.adapters.VpAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.cloud_button).setOnClickListener { toCloudActivity() }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val openMenu = findViewById<ImageView>(R.id.menu_image)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        openMenu.setOnClickListener { openNavigationMenu(drawerLayout) }

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager.adapter = VpAdapter(this)

        TabLayoutMediator(tabLayout, viewPager){tab, index ->
            tab.text = when(index){
                0 -> {resources.getString(R.string.scales)}
                1 -> {resources.getString(R.string.favourites)}
                else -> { throw Resources.NotFoundException("Position not found")}
            }
        }.attach()
    }

    private fun toCloudActivity(){
        val intent = Intent(this, CloudActivity::class.java)
        startActivity(intent)
    }

    private fun openNavigationMenu(drawerLayout : DrawerLayout){
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}