package com.sourav.oversplash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.activity.adapter.BasicAdapter
import com.sourav.oversplash.data.TopicData
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.FragmentFeedBinding
import com.sourav.oversplash.utils.DataWrapper
import com.sourav.oversplash.viewmodels.ImageViewModel

class FeedFragment : Fragment(), FeedAdapterOnClickListener<Photo> {
    // TODO: Rename and change types of parameters
    private val args: FeedFragmentArgs by navArgs()
    private lateinit var binding: FragmentFeedBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: BasicAdapter;
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        var topic: TopicData? = null
        try {
            topic = args.topic
        }catch (e: Exception){
            e.printStackTrace()
        }

        initView(topic)
        initViewModels(topic)

        return binding.root
    }

    private fun initViewModels(topic: TopicData?) {
        when (topic) {
            null -> imageViewModel.getImageList()
            else -> imageViewModel.getImageListByTopic(topic.topicID)
        }
        imageViewModel.photoList.observe(viewLifecycleOwner) {
            when(it.status){
                DataWrapper.Status.SUCCESS -> {
                    val photoList = it.data!!.toMutableList()
                    adapter.setData(photoList)
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

    private fun initView(topic: TopicData?) {
        when (topic) {
            null -> binding.topic.visibility = View.GONE
            else -> {
                binding.topic.visibility = View.VISIBLE
                binding.topic.text = topic.topicName
            }
        }
        recyclerView = binding.rvMain
        binding.fab.setOnClickListener{
            refreshData()
        }

        recyclerView.apply {
            setHasFixedSize(false)

            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            itemAnimator = null
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                        loadMoreData(topic)
                    }
                }
            })
        }
        adapter = BasicAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun loadMoreData(topic: TopicData?) {
        when (topic) {
            null -> imageViewModel.getNextPage()
            else -> imageViewModel.getNextTopicImagePage(topic.topicID)
        }
    }

    private fun refreshData(){

    }

    override fun onClick(photo: Photo) {
        val action = FeedFragmentDirections.actionFeedFragmentToPhotoViewActivity(photo)
        Navigation.findNavController(view!!).navigate(action)
    }
}