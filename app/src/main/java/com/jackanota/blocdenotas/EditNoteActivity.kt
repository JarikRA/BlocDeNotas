package com.jackanota.blocdenotas

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jackanota.blocdenotas.databinding.ActivityEditNoteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EditNoteActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var mlNote: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var note: Note
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etTitle = binding.etTitle
        mlNote = binding.mlNote
        btnSave = binding.btnSave
        btnDelete = binding.btnDelete

        sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)

        note = intent.getParcelableExtra("noteItem")!!

        etTitle.setText(note.title)
        mlNote.setText(note.text)

        btnSave.setOnClickListener {
            saveNote()
        }

        btnDelete.setOnClickListener {
            deleteNote()
        }
    }

    private fun saveNote() {
        val title = etTitle.text.toString()
        val text = mlNote.text.toString()
        val updatedNote = Note(title, text)

        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<MutableList<Note>>() {}.type
        val notesList: MutableList<Note> = gson.fromJson(json, type) ?: mutableListOf()

        // Reemplaza la nota existente con la actualizada
        val noteIndex = notesList.indexOfFirst { it == note }
        if (noteIndex != -1) {
            notesList[noteIndex] = updatedNote
        } else {
            notesList.add(updatedNote)
        }

        val updatedNotesJson = gson.toJson(notesList)
        sharedPreferences.edit().putString("notes", updatedNotesJson).apply()
        finish()
    }

    private fun deleteNote() {
        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<MutableList<Note>>() {}.type
        val notesList: MutableList<Note> = gson.fromJson(json, type) ?: mutableListOf()

        // Elimina la nota
        notesList.remove(note)

        val updatedNotesJson = gson.toJson(notesList)
        sharedPreferences.edit().putString("notes", updatedNotesJson).apply()
        finish()
    }
}
