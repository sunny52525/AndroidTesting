package com.shaun.androidtesting.presentation.note_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shaun.androidtesting.R
import com.shaun.androidtesting.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NoteListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesListBinding.bind(view)

        binding.addNoteButton.setOnClickListener {
//            val isEdit = false
//            val noteId = 0L
//            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment(
//                isEdit,
//                noteId
//            )
//            findNavController().navigate(action)

            viewModel.addNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}