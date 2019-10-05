package com.example.notetaker.views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaker.R
import com.example.notetaker.adapters.NotesAdapter
import kotlinx.android.synthetic.main.activity_main.*

const val STRING_KEY = "note"
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences
    lateinit var preferenceEditor: SharedPreferences.Editor
    val notes: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences(
            applicationContext.packageName, Context.MODE_PRIVATE
        )
        preferenceEditor = sharedPreference.edit()

        for(i in 0..6) {
            val str = sharedPreference.getString(getStringKey(i), "note ${i.toString()}") ?: ""
            notes.add(str)
        }

        rv_notes_list.adapter = NotesAdapter(notes)
        rv_notes_list.layoutManager = LinearLayoutManager(this)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save_list -> saveNotes()
        }
    }

    fun saveNotes() {
        for(i in notes.indices) {
            preferenceEditor.putString(getStringKey(i), notes.get(i))
        }
        preferenceEditor.commit()

        Toast.makeText(this, "Notes saved!", Toast.LENGTH_SHORT).show()
    }
}

fun getStringKey(i: Int) = "${STRING_KEY}_$i.toString()}"
