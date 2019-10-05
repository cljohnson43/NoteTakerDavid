package com.example.notetaker.views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetaker.R
import com.example.notetaker.adapters.NotesAdapter
import com.example.notetaker.models.Note
import kotlinx.android.synthetic.main.activity_main.*

const val STRING_KEY = "note"
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences
    val notes: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences(
            applicationContext.packageName, Context.MODE_PRIVATE
        )
        retrieveNotes()

        rv_notes_list.adapter = NotesAdapter(notes)
        rv_notes_list.layoutManager = LinearLayoutManager(this)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save_list -> saveNotes()
        }
    }

    fun saveNotes() {
        val preferenceEditor = sharedPreference.edit()
        for(i in notes.indices) {
            preferenceEditor.putString(getTitleKey(i), notes.get(i).title)
            preferenceEditor.putString(getNoteKey(i), notes.get(i).note)
        }
        preferenceEditor.apply()

        Toast.makeText(this, "Notes saved!", Toast.LENGTH_SHORT).show()
    }

    fun retrieveNotes() {
        var retrievedTitle: String
        var retrievedNoteEntry: String

        for (i in 0..6) {
            retrievedTitle = sharedPreference.getString(getTitleKey(i), "") ?: ""
            retrievedNoteEntry = sharedPreference.getString(getNoteKey(i), "") ?: ""
            notes.add(Note(retrievedTitle, retrievedNoteEntry))
        }

    }
}

fun getTitleKey(i: Int) = "${STRING_KEY}_TITLE_${i.toString()}"
fun getNoteKey(i: Int) = "${STRING_KEY}_NOTE_${i.toString()}"
