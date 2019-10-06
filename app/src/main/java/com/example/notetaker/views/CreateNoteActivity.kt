package com.example.notetaker.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notetaker.R
import com.example.notetaker.models.EXTRA_NOTE_KEY
import com.example.notetaker.models.Note
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save_note -> {
                val title = et_title.text.toString()
                val note = et_note.text.toString()
                if (title == null || title == "") {
                    Toast.makeText(this, "Must enter a title.", Toast.LENGTH_SHORT).show()
                } else {
                    val action = getString(R.string.ACTION_CREATE_NOTE)
                    if (intent.action == action) {
                        Intent(action).apply {
                            putExtra(EXTRA_NOTE_KEY, Note(note = et_note.text.toString(), title = et_title.text.toString()))
                            setResult(Activity.RESULT_OK, this)
                        }
                        finish()
                    }
                }
            }
        }
    }

}