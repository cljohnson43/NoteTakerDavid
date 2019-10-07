package com.example.notetaker.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetaker.R
import com.example.notetaker.adapters.NotesAdapter
import com.example.notetaker.models.EXTRA_NOTE_KEY
import com.example.notetaker.models.Note
import kotlinx.android.synthetic.main.activity_note_list.*

const val STRING_KEY = "note"
const val LIST_SIZE_KEY = "number_of_notes"
const val REQUEST_NOTE_CODE = 43
const val REQUEST_NOTE_EDIT = 44

class NoteListActivity : AppCompatActivity(), NotesAdapter.ItemSelector {

    lateinit var sharedPreference: SharedPreferences
    val notes: MutableList<Note> = mutableListOf()
    var editingNoteIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        sharedPreference = getSharedPreferences(
            applicationContext.packageName, Context.MODE_PRIVATE
        )
        retrieveNotes()

        val fontColor = sharedPreference.getString(COLOR_KEY, DEFAULT_COLOR) ?: DEFAULT_COLOR

        rv_notes_list.adapter = NotesAdapter(notes, fontColor, this)
        val linearLayoutM = LinearLayoutManager(this)
        rv_notes_list.layoutManager = linearLayoutM

        val decorator = DividerItemDecoration(rv_notes_list.context, linearLayoutM.orientation)
        rv_notes_list.addItemDecoration(decorator)
    }

    override fun onStop() {
        super.onStop()
        sharedPreference.edit()?.let {
            it.putInt(LIST_SIZE_KEY, notes.size)
            for (i in notes.indices) {
                val note = notes.get(i)
                it.putString(getNoteKey(i), note.note)
                it.putString(getTitleKey(i), note.title)
            }
            it.apply()
        }
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
        val numNotes: Int = sharedPreference.getInt(LIST_SIZE_KEY, 0)

        for (i in 0..numNotes) {
            retrievedTitle = sharedPreference.getString(getTitleKey(i), "") ?: ""
            retrievedNoteEntry = sharedPreference.getString(getNoteKey(i), "") ?: ""
            if (retrievedTitle != "") {
                notes.add(Note(retrievedTitle, retrievedNoteEntry))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_NOTE_CODE && RESULT_OK == resultCode) {
            val newTitle = data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.title
            val newNote = data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.note
            if (newTitle != null && newNote != null) {
                notes.add(Note(title = newTitle, note = newNote))
                rv_notes_list.adapter?.notifyItemInserted(notes.lastIndex)
            }
        }

        if (requestCode == REQUEST_NOTE_EDIT && RESULT_OK == resultCode) {
            val newTitle = data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.title
            val newNote = data?.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.note

            if (newTitle != null && newNote != null) {
                notes.get(editingNoteIndex).apply {
                    title = newTitle
                    note = newNote
                }
                rv_notes_list.adapter?.notifyItemChanged(editingNoteIndex)
            }

            editingNoteIndex = -1
        }
    }

    override fun itemSelected(pos: Int) {
        Intent(this, CreateNoteActivity::class.java).apply {
            setAction(getString(R.string.ACTION_EDIT_NOTE))
            putExtra(EXTRA_NOTE_KEY, notes.get(pos))
            startActivityForResult(this, REQUEST_NOTE_EDIT)
            editingNoteIndex = pos
        }
    }
}

fun getTitleKey(i: Int) = "${STRING_KEY}_TITLE_${i.toString()}"
fun getNoteKey(i: Int) = "${STRING_KEY}_NOTE_${i.toString()}"
