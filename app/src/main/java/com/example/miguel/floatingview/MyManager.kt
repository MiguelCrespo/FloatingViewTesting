package com.example.miguel.floatingview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import javax.security.auth.callback.Callback


/**
 * Created by miguelcrespo on 10/29/18.
 */

class MyManager(v: View, context: Context, onClose: () -> Unit) {
    private var recyclerView: RecyclerView
    private var closeButton: Button

    init {
        Log.d(TAG, "MyManager Init")
        recyclerView = v.findViewById(R.id.recyclerView)
        closeButton = v.findViewById(R.id.close)

        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = MyAdapter()

        closeButton.setOnClickListener {
            Log.d(TAG, "WE have to close this shit")
            onClose()
        }
    }

    companion object {
        val TAG = "MyManager"
    }

    inner class MyAdapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val cellForRow = inflater.inflate(R.layout.random_layout, parent, false)

            return ViewHolder(cellForRow)
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        }

    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }
}