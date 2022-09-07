package com.decagon.firebasedemo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.firebasedemo.databinding.FragmentSecondBinding
import com.firebase.ui.auth.AuthUI

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            signOut()
        }
    }

    private fun signOut(){
        AuthUI.getInstance().signOut(requireContext())
        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        //startActivity(Intent(requireContext(), FirstFragment::class.java))
        return
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}