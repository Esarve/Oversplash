package com.sourav.oversplash.activity.viewholders

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.databinding.ViewholderLoadingBinding

class ProgressHolder(private val binding: ViewholderLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    var parent: ConstraintLayout = binding.parent
}