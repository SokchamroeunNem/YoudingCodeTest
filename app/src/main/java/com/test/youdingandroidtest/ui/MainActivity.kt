package com.test.youdingandroidtest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.databinding.ActivityMainBinding
import com.test.youdingandroidtest.utils.DataStatus
import com.test.youdingandroidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var entity: UserEntity

    private lateinit var binding: ActivityMainBinding

    companion object {
        var usersListData: List<UserEntity> = emptyList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
    }

    private fun observe() {
        viewModel.getAllUsers()
        viewModel.usersList.observe(this@MainActivity) { response ->
            when (response) {
                is DataStatus.Success -> {
                    usersListData = response.data?.toMutableList() ?: emptyList()
                }

                is DataStatus.Error -> {
                    Toast.makeText(
                        this, response.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is DataStatus.Loading -> {

                }
            }
        }
    }

}