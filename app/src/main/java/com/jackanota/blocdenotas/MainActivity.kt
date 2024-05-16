package com.jackanota.blocdenotas

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackanota.blocdenotas.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jackanota.blocdenotas.Adapter.NoteAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)

        noteAdapter = NoteAdapter(this)
        binding.recyclerNote.adapter = noteAdapter
        binding.recyclerNote.layoutManager = LinearLayoutManager(this)

        loadNotes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNotes() {
        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<List<Note>>() {}.type
        val notesList: List<Note>? = gson.fromJson(json, type)
        if (notesList != null) {
            noteAdapter.notes = notesList
            noteAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "No hay notas disponibles.", Toast.LENGTH_SHORT).show()
        }
    }

    fun addNote(view: View) {
        val intent = Intent(this, NewNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }
}

