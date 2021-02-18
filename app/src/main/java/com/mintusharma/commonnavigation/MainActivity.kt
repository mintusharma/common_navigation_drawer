package com.mintusharma.commonnavigation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mintusharma.commonnavigation.databinding.ActivityMainBinding
import com.mintusharma.commonnavigation.fragments.FirstFragment
import com.mintusharma.commonnavigation.fragments.SecondFragment
import com.mintusharma.commonnavigation.fragments.ThirdFragment

class MainActivity : AppCompatActivity() {

    private var mDrawer:DrawerLayout?=null
    private var toolbar:Toolbar?=null
    private var navDrawer:NavigationView?=null
    private lateinit var binding: ActivityMainBinding

    private var drawerToggle:ActionBarDrawerToggle?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        toolbar=findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // finding the drawer view
        mDrawer=findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawerToggle=setupDrawerToggle()
        drawerToggle!!.isDrawerIndicatorEnabled=true
        drawerToggle!!.syncState()

        //tie drawer layout event to the ActionBarToggle
        mDrawer!!.addDrawerListener(drawerToggle!!)
        navDrawer=findViewById(R.id.nvView)
        //setuo draer view
        setupDrawerContent(navDrawer)
    }

    private fun setupDrawerContent(navDrawer: NavigationView?) {
        navDrawer!!.setNavigationItemSelectedListener { menuItem->
            selectDrawerMenuItem(menuItem)
            true
        }
    }

    private fun selectDrawerMenuItem(menuItem: MenuItem) {
        var fragment:Fragment?=null
        val fragmentClass:Class<*>
        when(menuItem.itemId){
            R.id.nav_first_fragment->fragment=FirstFragment()
            R.id.nav_second_fragment->fragment=SecondFragment()
            R.id.nav_third_fragment->fragment=ThirdFragment()
            else -> FirstFragment::class.java
        }

        val fragmenManager = supportFragmentManager
        fragmenManager.beginTransaction().replace(R.id.flContent,fragment!!).commit()

        menuItem.isChecked=true
        title=menuItem.title
        mDrawer!!.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  if (drawerToggle!!.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        drawerToggle!!.onConfigurationChanged(newConfig)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle? {
        return  ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close)
    }
}