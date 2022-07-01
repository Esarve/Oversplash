package com.sourav.oversplash.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.activity.adapter.BasicAdapter
import com.sourav.oversplash.databinding.ActivityMainBinding
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.viewmodels.ImageViewModel

class MainActivity : BaseActivity(), AdapterOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: BasicAdapter;
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModels()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModels() {
        imageViewModel.getImageList()
        imageViewModel.photoList.observe(this) {
            adapter = BasicAdapter(it.toMutableList(), this)
            recyclerView.adapter = adapter
        }
    }

    private fun initView() {
        recyclerView = binding.rvMain
        binding.fab.setOnClickListener{
            refreshData()
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    fun refreshData(){
        imageViewModel.getImageList()
    }

    override fun onClick(url: String) {
        startActivity(
            Intent(this, PhotoViewActivity::class.java)
                .apply {
                    putExtra(Constants.IntentKey_PHOTO_URL, url)
                }
        )
    }

}