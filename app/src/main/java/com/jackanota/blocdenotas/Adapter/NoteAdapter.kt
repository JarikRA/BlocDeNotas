package com.jackanota.blocdenotas.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jackanota.blocdenotas.Note
import com.jackanota.blocdenotas.databinding.ItemNoteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jackanota.blocdenotas.EditNoteActivity
import com.jackanota.blocdenotas.R

class NoteAdapter(private val context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    var notes: List<Note> = emptyList()

    init {
        loadNotesFromSharedPreferences()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNotesFromSharedPreferences() {
        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        notes = if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Note>>() {}.type)
        } else {
            emptyList()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note) { selectedNote ->
            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("noteItem", selectedNote)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        fun bind(note: Note, onClickListener: (Note) -> Unit) {
            tvTitle.text = note.title
            itemView.setOnClickListener {
                onClickListener(note)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
