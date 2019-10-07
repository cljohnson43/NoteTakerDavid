package com.example.notetaker.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaker.R
import com.example.notetaker.models.NoteColor

class MainAdapter(val colorList: List<NoteColor>, val delegator: ColorSelector)
    : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = colorList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val color = colorList.get(position)
        holder.tvColor.setText(color.label)
        holder.cvColor.setBackgroundColor(Color.parseColor(color.rgb))
        holder.cvColor.setOnClickListener {
            delegator.colorSelected(colorList.get(position).rgb)
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvColor: TextView = itemView.findViewById(R.id.tv_color)
        val cvColor: CardView = itemView.findViewById(R.id.cv_color_item)
    }

    interface ColorSelector {
        fun colorSelected(rgb: String)
    }
}
