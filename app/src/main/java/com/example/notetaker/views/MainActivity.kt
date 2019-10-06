package com.example.notetaker.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetaker.R
import com.example.notetaker.adapters.NotesAdapter
import com.example.notetaker.models.EXTRA_NOTE_KEY
import com.example.notetaker.models.Note
import kotlinx.android.synthetic.main.activity_main.*

const val STRING_KEY = "note"
const val REQUEST_NOTE_CODE = 43

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
            R.id.btn_create_note -> {
                Intent(this, CreateNoteActivity::class.java).apply {
                    setAction(getString(R.string.ACTION_CREATE_NOTE))
                    startActivityForResult(this, REQUEST_NOTE_CODE)
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_NOTE_CODE) {
            Log.d("NOTE_ADDED", "Title: ${data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.title}\nNote: ${data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.note}")
        }

    }
}

fun getTitleKey(i: Int) = "${STRING_KEY}_TITLE_${i.toString()}"
fun getNoteKey(i: Int) = "${STRING_KEY}_NOTE_${i.toString()}"
