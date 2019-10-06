package com.example.notetaker.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notetaker.R
import com.example.notetaker.models.EXTRA_NOTE_KEY
import com.example.notetaker.models.Note
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {

    lateinit var actionRequested: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        actionRequested = intent.action ?: "NO_ACTION_REQUESTED"

        if (actionRequested == getString(R.string.ACTION_EDIT_NOTE)) {
            intent.getParcelableExtra<Note>(EXTRA_NOTE_KEY)?.let {
                et_title.setText(it.title, TextView.BufferType.EDITABLE)
                et_note.setText(it.note, TextView.BufferType.EDITABLE)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save_note -> {
                val title = et_title.text.toString()
                val note = et_note.text.toString()
                if (title == "") {
                    Toast.makeText(this, "Must enter a title.", Toast.LENGTH_SHORT).show()
                } else {
                    if (intent.action == actionRequested) {
                        Intent(actionRequested).apply {
                            putExtra(EXTRA_NOTE_KEY, Note(note = note, title = title))
                            setResult(Activity.RESULT_OK, this)
                        }
                        finish()
                    }
                }
            }
        }
    }

}