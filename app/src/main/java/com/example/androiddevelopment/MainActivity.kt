package com.example.androiddevelopment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddevelopment.adapter.UserListAdapter
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var mainActivityBinding: ActivityMainBinding
    var adapter = UserListAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityBinding.viewModel = mainViewModel

        CoroutineScope(Dispatchers.Main).launch {
            val users = mainViewModel.fetchUser()
            mainActivityBinding.userListView.layoutManager = LinearLayoutManager(this@MainActivity)
            mainActivityBinding.userListView.adapter = adapter
            adapter.setUsers(users)

        }
    }
}

