package com.shaun.androidtesting.presentation.note_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaun.androidtesting.databinding.NoteItemBinding
import com.shaun.androidtesting.domain.model.NoteItem

class NoteListAdapter(
    private val noteList: List<NoteItem>,
    val listener:OnClick
) : RecyclerView.Adapter<NoteListAdapter.NoteItemHolder>() {

    interface OnClick{
        fun onDelete(uid:Long)
    }
    class NoteItemHolder( val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteItem: NoteItem) {

            binding.title.text = noteItem.title
            binding.shortNote.text = noteItem.body
            binding.date.text = "12/22/11"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemHolder {
        val itemBinding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteItemHolder, position: Int) {
        val noteItem = noteList[position]
        with(holder){
            binding.delete.setOnClickListener {
                listener.onDelete(noteItem.id)
            }
        }
        holder.bind(noteItem)
    }

    override fun getItemCount(): Int = noteList.size
}