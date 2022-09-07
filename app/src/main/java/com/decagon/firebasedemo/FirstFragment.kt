package com.decagon.firebasedemo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.decagon.firebasedemo.databinding.FragmentFirstBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth:FirebaseAuth
    val signIn: ActivityResultLauncher<Intent> = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(), this::onSignInResult
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseSetUp()
        auth = Firebase.auth
        binding.fragmentLoginLoginBtn.setOnClickListener {
            val provider = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


    }

    override fun onStart() {
        super.onStart()
        firebaseSetUp()
    }


    fun firebaseSetUp(){
            if (auth.currentUser == null){
                // user is not signed In the sign in user

                val signItent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setLogo(R.mipmap.ic_launcher)
                    .setAvailableProviders(listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    )).build()

                //startActivity(Intent(requireContext(), SecondFragment::class.java))

                signIn.launch(signItent)
        }
    }

    private fun onSignInResult(result:FirebaseAuthUIAuthenticationResult){
        if (result.resultCode == RESULT_OK){
            Log.d("LOGIN", "login successful")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }else{
            Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()

            val response = result.idpResponse
            if (response == null){
                Log.d("LOGIN", "login canceled")
            }else{
                Log.d("LOGIN", "login error ===${response.error}")
            }
        }
    }

    private fun getPhotoUrl():String{
        val user = auth.currentUser
        return user?.photoUrl.toString()
    }

    private fun getUserName() :String?{
        val user = auth.currentUser
        return if (user != null){
            user.displayName
        }else "ANONYMOUS"
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}