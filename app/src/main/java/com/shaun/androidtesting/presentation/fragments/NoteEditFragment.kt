package com.shaun.androidtesting.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.shaun.androidtesting.R
import com.shaun.androidtesting.databinding.FragmentNotesListBinding


class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesListBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}