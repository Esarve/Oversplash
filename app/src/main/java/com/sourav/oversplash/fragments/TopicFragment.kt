package com.sourav.oversplash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.activity.adapter.TopicAdapter
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.FragmentFeedBinding
import com.sourav.oversplash.utils.DataWrapper
import com.sourav.oversplash.viewmodels.TopicViewModel

class TopicFragment : Fragment(), FeedAdapterOnClickListener {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: TopicAdapter;
    private val topicViewModel: TopicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        initView()
        initData()
        return binding.root
    }

    private fun initData() {
        topicViewModel.getTopics()
        topicViewModel.topicLiveData.observe(viewLifecycleOwner) {
            when(it.status){
                DataWrapper.Status.SUCCESS -> {
                    adapter.setData(it.data!!)
                }
                DataWrapper.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Something Went Wrong with HTTP CODE ${it.errorCode}", Toast.LENGTH_SHORT).show()
                }
                DataWrapper.Status.LOADING ->{}
                DataWrapper.Status.FAILURE -> {
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        recyclerView = binding.rvMain

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(Oversplash.instance).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
        }
        adapter = TopicAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    override fun onClick(photo: Photo) {

    }

}