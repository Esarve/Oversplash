package com.sourav.oversplash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.R
import com.sourav.oversplash.activity.adapter.TopicAdapter
import com.sourav.oversplash.data.TopicData
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.databinding.FragmentTopicBinding
import com.sourav.oversplash.utils.DataWrapper
import com.sourav.oversplash.utils.Utils
import com.sourav.oversplash.viewmodels.TopicViewModel

class TopicFragment : Fragment(), FeedAdapterOnClickListener<Topic> {

    private lateinit var binding: FragmentTopicBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: TopicAdapter;
    private val topicViewModel: TopicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicBinding.inflate(inflater,container,false)
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
                    Toast.makeText(requireContext(), getString(R.string.msg_something_went_wrong_http).plus(" ${it.errorCode}"), Toast.LENGTH_SHORT).show()
                }
                DataWrapper.Status.LOADING ->{}
                DataWrapper.Status.FAILURE -> {
                    if (!Utils.isNetworkConnected(Oversplash.instance)){
                        Toast.makeText(Oversplash.instance, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(requireContext(), getString(R.string.msg_something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        recyclerView = binding.rvTopic

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

    override fun onClick(topic: Topic) {
        val action = TopicFragmentDirections.actionDestTopicToDestFeed(TopicData(topic.title,topic.id))
        Navigation.findNavController(view!!).navigate(action)
    }

}