package com.shaun.androidtesting.presentation.note_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaun.androidtesting.R
import com.shaun.androidtesting.common.showToast
import com.shaun.androidtesting.data.local.dto.toNotes
import com.shaun.androidtesting.databinding.FragmentNotesListBinding
import com.shaun.androidtesting.presentation.note_detail.NoteEditFragmentArgs
import com.shaun.androidtesting.presentation.note_list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.fragment_notes_list), NoteListAdapter.OnClick {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var  notesAdapter:NoteListAdapter
    private val viewModel by viewModels<NoteListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesListBinding.bind(view)

        binding.addNoteButton.setOnClickListener {
            val isEdit = false
            val noteId = 0L
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment(
                isEdit,
                noteId
            )
            findNavController().navigate(action)
        }


        setObservers()

    }

    private fun setObservers() {
        viewModel.allNotes?.observe(viewLifecycleOwner) { notes ->
            notes?.map { it.toNotes() }?.let { notelist ->
                notesAdapter = NoteListAdapter(noteList = notelist, this)
                binding.recyclerView.adapter = notesAdapter
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            }
        }


        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showToast(viewModel.errorMessage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDelete(uid: Long, position:Int) {
        notesAdapter.notifyItemRemoved(position)

        lifecycleScope.launch {
            delay(400)
            viewModel.deleteNote(uid)
        }
    }

    override fun onEdit(uid: Long) {

        val action=NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment(isEdit = true, noteId = uid)
        findNavController().navigate(action)
    }
}