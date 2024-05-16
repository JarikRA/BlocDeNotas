package com.jackanota.blocdenotas

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jackanota.blocdenotas.databinding.ActivityEditNoteBinding

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        gson = Gson()

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.mlNote.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(title, content)
                saveNote(note)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Por favor, ingrese un título y contenido.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnDelete.visibility = View.GONE
        binding.btnDelete.isEnabled = false
    }

    private fun saveNote(note: Note) {
        val editor = sharedPreferences.edit()

        val currentNotesJson = sharedPreferences.getString("notes", "[]")
        val type = object : TypeToken<List<Note>>() {}.type
        val currentNotes: MutableList<Note> = gson.fromJson(currentNotesJson, type) ?: mutableListOf()

        currentNotes.add(note)

        val updatedNotesJson = gson.toJson(currentNotes)
        editor.putString("notes", updatedNotesJson)
        editor.apply()
    }
}
