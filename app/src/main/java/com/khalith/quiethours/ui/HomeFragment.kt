package com.khalith.quiethours.ui

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.TelecomManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.khalith.quiethours.R
import com.khalith.quiethours.databinding.FragmentHomeBinding
import com.khalith.quiethours.util.BusyModeManager

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var manager: BusyModeManager

    private val dialerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        updateDialerStatus()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        manager = BusyModeManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchAppEnabled.isChecked = manager.appEnabled
        binding.switchAppEnabled.setOnCheckedChangeListener { _, isChecked ->
            manager.appEnabled = isChecked
        }

        updateDialerStatus()
        binding.btnSetDialer.setOnClickListener {
            requestDefaultDialerRole()
        }

        // Mode Radio buttons
        when (manager.currentMode) {
            BusyModeManager.Mode.BUSY -> binding.rbBusy.isChecked = true
            BusyModeManager.Mode.WORK -> binding.rbWork.isChecked = true
            BusyModeManager.Mode.SLEEP -> binding.rbSleep.isChecked = true
            BusyModeManager.Mode.CUSTOM -> binding.rbCustom.isChecked = true
            else -> {}
        }

        binding.rgModes.setOnCheckedChangeListener { _, checkedId ->
            manager.currentMode = when (checkedId) {
                R.id.rb_busy -> BusyModeManager.Mode.BUSY
                R.id.rb_work -> BusyModeManager.Mode.WORK
                R.id.rb_sleep -> BusyModeManager.Mode.SLEEP
                R.id.rb_custom -> BusyModeManager.Mode.CUSTOM
                else -> BusyModeManager.Mode.OFF
            }
        }
    }

    private fun updateDialerStatus() {
        val telecomManager = requireContext().getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val isDefault = telecomManager.defaultDialerPackage == requireContext().packageName
        binding.tvDialerStatus.text = "Dialer Status: ${if (isDefault) "MATCHED" else "NOT SET"}"
        binding.btnSetDialer.isEnabled = !isDefault
    }

    private fun requestDefaultDialerRole() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_9) {
            val roleManager = requireContext().getSystemService(Context.ROLE_SERVICE) as RoleManager
            if (roleManager.isRoleAvailable(RoleManager.ROLE_DIALER)) {
                if (!roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                    val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                    dialerLauncher.launch(intent)
                }
            }
        } else {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, requireContext().packageName)
            dialerLauncher.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
