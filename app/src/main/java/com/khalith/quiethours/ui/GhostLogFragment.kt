package com.khalith.quiethours.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khalith.quiethours.databinding.FragmentGhostLogBinding

class GhostLogFragment : Fragment() {

    private var _binding: FragmentGhostLogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GhostViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGhostLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GhostLogAdapter()
        binding.rvGhostLogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGhostLogs.adapter = adapter

        viewModel.allLogs.observe(viewLifecycleOwner) { logs ->
            adapter.submitList(logs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
