package com.sourav.oversplash.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.R
import com.sourav.oversplash.activity.adapter.BasicAdapter
import com.sourav.oversplash.databinding.ActivityMainBinding
import com.sourav.oversplash.viewmodels.ImageViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: BasicAdapter;
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.bottomNavigation.let {
            NavigationUI.setupWithNavController(it,navController)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }
}