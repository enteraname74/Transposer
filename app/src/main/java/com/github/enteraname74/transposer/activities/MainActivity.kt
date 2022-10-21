package com.github.enteraname74.transposer.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.adapters.VpAdapter
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Transposition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            readAllTranspositions()
            // Une fois nos transpositions récupérées, on initialise la liste des favoris :
            for (transposition in AppData.allTranspositions){
                if (transposition.isFavourite){
                    AppData.favouritesList.add(transposition)
                }
            }}

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
                1 -> {resources.getString(R.string.transpositions)}
                2 -> {resources.getString(R.string.favourites)}
                else -> { throw Resources.NotFoundException("Position not found")}
            }
        }.attach()

        val createTransposition = findViewById<FloatingActionButton>(R.id.add_transposition_button)
        createTransposition.setOnClickListener { createTransposition() }
    }

    override fun onResume() {
        super.onResume()
        if (!checkPermission(android.Manifest.permission.READ_CONTACTS)) {
            requestPermission(android.Manifest.permission.READ_CONTACTS)
        }
        if (!checkPermission(android.Manifest.permission.SEND_SMS)) {
            requestPermission(android.Manifest.permission.SEND_SMS)
        }
    }

    private fun toCloudActivity(){
        val intent = Intent(this, CloudActivity::class.java)
        startActivity(intent)
    }

    private fun createTransposition(){
        val intent = Intent(this, TranspositionActivity::class.java)
        startActivity(intent)
    }

    private fun openNavigationMenu(drawerLayout : DrawerLayout){
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun readAllTranspositions(){
        val path = applicationContext.filesDir
        var content = ArrayList<Transposition>()
        try {
            val ois = ObjectInputStream(FileInputStream(File(path, AppData.allTranspositionFile)))
            content = ois.readObject() as ArrayList<Transposition>
            ois.close()
        } catch (error : IOException){
            Log.d("Error read",error.toString())
        }
        AppData.allTranspositions = content
    }

    private fun checkPermission(permission : String) : Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission : String) {
        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            ActivityCompat.requestPermissions(this, arrayOf(permission), 69)
        }
    }
}