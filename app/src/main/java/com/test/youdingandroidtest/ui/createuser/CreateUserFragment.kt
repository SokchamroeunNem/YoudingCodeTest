package com.test.youdingandroidtest.ui.createuser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.test.youdingandroidtest.R
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.databinding.FragmentCreateUserBinding
import com.test.youdingandroidtest.ui.MainActivity.Companion.usersListData
import com.test.youdingandroidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateUserFragment : Fragment() {

    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userEntity: UserEntity

    private val viewModel: MainViewModel by viewModels()

    private var userId = 0

    private lateinit var fullnameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullnameLayout = view.findViewById(R.id.fullname_layout)
        emailLayout = view.findViewById(R.id.email_layout)
        passwordLayout = view.findViewById(R.id.password_layout)

        checkOnBackPressedCallback()

        // Add a callback to the onBackPressedDispatcher
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Set up validation listeners for each field
        setupValidationListeners()

        // Set up the Sign In button click listener
        binding.signInButton.setOnClickListener {
            validateForm()
        }

    }

    private fun checkOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    private fun setupValidationListeners() {
        binding.fullname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim().isEmpty()) {
                    fullnameLayout.error = "Full name cannot be empty."
                } else {
                    fullnameLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!isValidEmail(s.toString())) {
                    emailLayout.error = "Please enter a valid email address."
                } else {
                    emailLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim().isEmpty()) {
                    passwordLayout.error = "Password cannot be empty."
                } else {
                    passwordLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateForm() {

        val fullName = binding.fullname.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()


        if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            // Proceed with login logic
            checkIfEmailExists(email, usersListData)
        } else {
            //Full name
            if (fullName.isEmpty()) {
                fullnameLayout.error = "Full name cannot be empty."
            } else {
                fullnameLayout.isErrorEnabled = false
            }
            //Email
            if (!isValidEmail(email)) {
                emailLayout.error = "Please enter a valid email address."
            } else {
                emailLayout.isErrorEnabled = false
            }
            //Password
            if (password.isEmpty()) {
                passwordLayout.error = "Password cannot be empty."
            } else {
                passwordLayout.isErrorEnabled = false
            }
            Toast.makeText(
                requireContext(), "Please correct the errors in the form.", Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun checkIfEmailExists(email: String, usersListData: List<UserEntity>) {
        // Check if the email exists in the list
        val emailExists = usersListData.any { it.email == email }

        // Show a toast message based on whether the email exists
        if (emailExists) {
            Toast.makeText(requireContext(), "Email already exists.", Toast.LENGTH_SHORT).show()
        } else {
            // Email does not exist, proceed with your logic here
            createUser()
            goToHomeFragment()
            Toast.makeText(requireContext(), "Create user successful!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun goToHomeFragment() {
        val action = CreateUserFragmentDirections.actionCreateUserFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun createUser() {
        val fullName = binding.fullname.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        userEntity.userId = userId
        userEntity.fullName = fullName
        userEntity.email = email
        userEntity.passwrd = password
        userEntity.createdAt = System.currentTimeMillis()

        viewModel.saveUser(userEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}