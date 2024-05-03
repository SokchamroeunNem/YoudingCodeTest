package com.test.youdingandroidtest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.test.youdingandroidtest.R
import com.test.youdingandroidtest.databinding.FragmentLoginBinding
import com.test.youdingandroidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailLayout = view.findViewById(R.id.email_layout)
        passwordLayout = view.findViewById(R.id.password_layout)

        binding.containerSingup.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSingUpFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        // Set up the Sign In button click listener
        binding.loginButton.setOnClickListener {
            validateForm()
        }

    }

    private fun loginResult() {
        lifecycleScope.launch {
            viewModel.loginResult.collect { isLoggedIn ->
                if (isLoggedIn == true) {
                    // Navigate to the next screen or perform other actions
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else if (isLoggedIn == false) {
                    // Show an error message
                    Toast.makeText(
                        requireContext(), "Invalid email or password", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateForm() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Proceed with login logic
            //checkIfEmailExists(email, MainActivity.usersListData)
            loginUser(email, password)
            loginResult()
        } else {
            Toast.makeText(
                requireContext(),
                "Please input all field email and password can't empty.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun loginUser(email: String, password: String) {
        viewModel.loginUser(email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}