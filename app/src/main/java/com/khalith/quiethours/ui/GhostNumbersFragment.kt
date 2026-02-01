package com.khalith.quiethours.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khalith.quiethours.data.GhostNumber
import com.khalith.quiethours.databinding.FragmentGhostNumbersBinding

class GhostNumbersFragment : Fragment() {

    private var _binding: FragmentGhostNumbersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GhostViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGhostNumbersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GhostNumberAdapter { ghostNumber ->
            viewModel.deleteGhostNumber(ghostNumber)
        }
        binding.rvGhostNumbers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGhostNumbers.adapter = adapter

        viewModel.allGhostNumbers.observe(viewLifecycleOwner) { numbers ->
            adapter.submitList(numbers)
        }

        binding.btnAddNumber.setOnClickListener {
            showAddNumberDialog()
        }
    }

    private fun showAddNumberDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Ghost Number")

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }

        val nameInput = EditText(requireContext()).apply { hint = "Name" }
        val numberInput = EditText(requireContext()).apply { hint = "Number" }

        layout.addView(nameInput)
        layout.addView(numberInput)
        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val name = nameInput.text.toString()
            val number = numberInput.text.toString()
            if (number.isNotEmpty()) {
                viewModel.insertGhostNumber(GhostNumber(name = name, number = number))
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
