package com.shaun.androidtesting.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shaun.androidtesting.R
import com.shaun.androidtesting.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}