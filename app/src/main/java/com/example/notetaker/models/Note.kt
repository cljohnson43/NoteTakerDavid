package com.example.notetaker.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val EXTRA_NOTE_KEY = "note_key"

@Parcelize
data class Note(var title: String, var note: String) : Parcelable {
}