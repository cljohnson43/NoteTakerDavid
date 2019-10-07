package com.example.notetaker.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaker.R
import com.example.notetaker.models.Note

class NotesAdapter(val notes: MutableList<Note>, val fontRGB: String, val delegator: ItemSelector)
    : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    override fun onBindViewHolder(holder: NotesViewHolder, pos: Int) {
        holder.tvNote.text = notes.get(pos).title
        holder.tvNote.setTextColor(Color.parseColor(fontRGB))
        holder.cvNote.setOnClickListener{
            delegator.itemSelected(pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount() = notes.size

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNote: TextView = itemView.findViewById(R.id.tv_note_title)
        val cvNote: CardView = itemView.findViewById(R.id.cv_note)
    }

    interface ItemSelector {
        fun itemSelected(pos: Int)
    }
}