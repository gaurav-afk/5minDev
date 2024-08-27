package com.example.a5mindev

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.a5mindev.data.Shorts


class ShortsAdapter(context: Context, shortsList: List<Shorts>) : ArrayAdapter<Shorts>(context, 0, shortsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_shorts, parent, false)

        val shorts = getItem(position)

        val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        titleTextView.text = shorts?.title ?: "No Title"

        return view
    }
}


