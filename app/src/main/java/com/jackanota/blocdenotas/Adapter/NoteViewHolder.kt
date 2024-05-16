package com.jackanota.blocdenotas.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jackanota.blocdenotas.Note
import com.jackanota.blocdenotas.databinding.ItemNoteBinding

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding: ItemNoteBinding = ItemNoteBinding.bind(itemView)

    fun bind(note: Note, onClickListener: (Note) -> Unit) {
        binding.tvTitle.text = note.title
        itemView.setOnClickListener {
            onClickListener(note)
        }
    }
}
