package com.example.notetaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaker.R

class NotesAdapter(val notes: MutableList<String>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onBindViewHolder(holder: NotesViewHolder, pos: Int) {
        holder.etNote.setText(notes.get(pos), TextView.BufferType.EDITABLE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount() = notes.size

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNote: EditText = itemView.findViewById(R.id.et_note)
    }
}