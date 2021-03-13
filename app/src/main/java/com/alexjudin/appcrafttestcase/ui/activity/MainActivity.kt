package com.alexjudin.appcrafttestcase.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alexjudin.appcrafttestcase.AlbumApp
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.presentation.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity()
   {

    @Inject lateinit var viewModel: AlbumViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Screen)
        setContentView(R.layout.activity_main)

        AlbumApp.component.injectMainActivity(this)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }


}