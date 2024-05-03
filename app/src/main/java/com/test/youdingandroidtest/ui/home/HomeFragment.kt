package com.test.youdingandroidtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.databinding.FragmentHomeBinding
import com.test.youdingandroidtest.ui.home.adapter.UsersAdapter
import com.test.youdingandroidtest.utils.DataStatus
import com.test.youdingandroidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var notesAdapter: UsersAdapter

    @Inject
    lateinit var entity: UserEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize the adapter
        notesAdapter = UsersAdapter()

        binding.btnAddNote.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateUserFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        observe()

    }

    private fun observe() {
        viewModel.getAllUsers()
        viewModel.usersList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is DataStatus.Success -> {
                    if (response.data?.isEmpty() == true) {
                        binding.listBody.visibility = View.GONE
                        binding.emptyBody.isVisible = true
                        binding.loading.isVisible = false
                    } else {
                        binding.listBody.visibility = View.VISIBLE
                        binding.emptyBody.isVisible = false

                        //bind the data to the ui
                        binding.loading.isVisible = false
                        notesAdapter.differ.submitList(response.data)
                        binding.rvUsers.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = notesAdapter
                        }
                    }
                }

                is DataStatus.Error -> {
                    Toast.makeText(
                        requireContext(), response.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is DataStatus.Loading -> {
                    binding.loading.isVisible = true
                    binding.emptyBody.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}