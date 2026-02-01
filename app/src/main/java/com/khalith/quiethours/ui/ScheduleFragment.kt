package com.khalith.quiethours.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.khalith.quiethours.databinding.FragmentScheduleBinding
import com.khalith.quiethours.util.BusyModeManager

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var manager: BusyModeManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        manager = BusyModeManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tpStart.hour = manager.scheduleStartHour
        binding.tpStart.minute = manager.scheduleStartMinute
        binding.tpEnd.hour = manager.scheduleEndHour
        binding.tpEnd.minute = manager.scheduleEndMinute

        val days = manager.scheduleDays
        binding.cbSun.isChecked = days.contains("1")
        binding.cbMon.isChecked = days.contains("2")
        binding.cbTue.isChecked = days.contains("3")
        binding.cbWed.isChecked = days.contains("4")
        binding.cbThu.isChecked = days.contains("5")
        binding.cbFri.isChecked = days.contains("6")
        binding.cbSat.isChecked = days.contains("7")

        binding.btnSaveSchedule.setOnClickListener {
            manager.scheduleStartHour = binding.tpStart.hour
            manager.scheduleStartMinute = binding.tpStart.minute
            manager.scheduleEndHour = binding.tpEnd.hour
            manager.scheduleEndMinute = binding.tpEnd.minute

            val newDays = mutableSetOf<String>()
            if (binding.cbSun.isChecked) newDays.add("1")
            if (binding.cbMon.isChecked) newDays.add("2")
            if (binding.cbTue.isChecked) newDays.add("3")
            if (binding.cbWed.isChecked) newDays.add("4")
            if (binding.cbThu.isChecked) newDays.add("5")
            if (binding.cbFri.isChecked) newDays.add("6")
            if (binding.cbSat.isChecked) newDays.add("7")
            manager.scheduleDays = newDays

            Toast.makeText(requireContext(), "Schedule Saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
