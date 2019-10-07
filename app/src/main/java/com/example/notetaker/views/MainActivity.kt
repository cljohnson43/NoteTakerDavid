package com.example.notetaker.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetaker.R
import com.example.notetaker.adapters.MainAdapter
import com.example.notetaker.models.NoteColor
import kotlinx.android.synthetic.main.activity_main.*

const val DEFAULT_COLOR = "#00FF00"
const val COLOR_KEY = "notetaker.color"

class MainActivity : AppCompatActivity(), MainAdapter.ColorSelector {

    val sharedPreferences: SharedPreferences by lazy {
        this.getSharedPreferences(
            applicationContext.packageName,
            Context.MODE_PRIVATE
        )
    }

    val colorList: List<NoteColor> by lazy {
        listOf(
            NoteColor(getString(R.string.tv_blue), getString(R.string.blue_rgb)),
            NoteColor(getString(R.string.tv_yellow), getString(R.string.yellow_rgb)),
            NoteColor(getString(R.string.tv_red), getString(R.string.red_rgb))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val colorRetrieved = sharedPreferences.getString(COLOR_KEY, DEFAULT_COLOR) ?: DEFAULT_COLOR
        Toast.makeText(this, "NoteColor retrieved $colorRetrieved", Toast.LENGTH_SHORT).show()

        rv_color_list.adapter = MainAdapter(colorList = colorList, delegator = this)
        rv_color_list.layoutManager = LinearLayoutManager(this)
    }

    override fun colorSelected(rgb: String) {
        sharedPreferences.edit()?.also {
            it.putString(COLOR_KEY, rgb)
            it.apply()
        }

        Intent(this, NoteListActivity::class.java).also {
            startActivity(it)
        }
    }
}
