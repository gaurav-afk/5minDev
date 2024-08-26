package com.towerofapp.a5mindev

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.towerofapp.a5mindev.R
import com.towerofapp.a5mindev.databinding.ItemSubtopicBinding


class SubTopicAdapter(
    context: Context,
    private val items: List<String>,
    private val onItemClick: (String) -> Unit
) : ArrayAdapter<String>(context, R.layout.item_subtopic, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val binding: ItemSubtopicBinding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            ItemSubtopicBinding.inflate(inflater, parent, false)
        } else {
            ItemSubtopicBinding.bind(convertView)
        }

        binding.btnSubtopic.text = item

        binding.btnSubtopic.setOnClickListener {
            item?.let { onItemClick(it) }
        }

        return binding.root
    }
}