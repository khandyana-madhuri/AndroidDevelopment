package com.example.androiddevelopment.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.Note

class ListAdapter : RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {

    private var note = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle = itemView.findViewById<TextView>(R.id.noteTitle)
        val noteDesc = itemView.findViewById<TextView>(R.id.noteDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun getItemCount(): Int {
        return note.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = note[position]
        holder.noteTitle.text = currentNote.title
        holder.noteDesc.text = currentNote.content
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(note: List<Note>) {
        this.note = note
        notifyDataSetChanged()
    }
}