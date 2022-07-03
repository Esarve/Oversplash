package com.sourav.oversplash.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.activity.adapter.BasicAdapter
import com.sourav.oversplash.databinding.FragmentFeedBinding
import com.sourav.oversplash.viewmodels.ImageViewModel

class FeedFragment : Fragment(), AdapterOnClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentFeedBinding
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: BasicAdapter;
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)

        initView()
        initViewModels()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModels() {
        imageViewModel.getImageList()
        imageViewModel.photoList.observe(viewLifecycleOwner) {
            val photoList = it.toMutableList()
            adapter.setData(photoList)
        }
    }

    private fun initView() {
        recyclerView = binding.rvMain
        binding.fab.setOnClickListener{
            refreshData()
        }

        recyclerView.apply {
            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(Oversplash.instance).apply {
//                orientation = LinearLayoutManager.VERTICAL
//            }
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            itemAnimator = null
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                        loadMoreData()
                    }
                }
            })
        }
        adapter = BasicAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun loadMoreData() {
        imageViewModel.getNextPage()
    }

    private fun refreshData(){

    }

    override fun onClick(url: String) {
        val action = FeedFragmentDirections.actionFeedFragmentToPhotoViewActivity(url)
        Navigation.findNavController(view!!).navigate(action)
//        startActivity(
//            Intent(this, PhotoViewActivity::class.java)
//                .apply {
//                    putExtra(Constants.IntentKey_PHOTO_URL, url)
//                }
//        )
    }
}