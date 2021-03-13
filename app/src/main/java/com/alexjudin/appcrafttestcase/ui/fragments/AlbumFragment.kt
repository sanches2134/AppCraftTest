package com.alexjudin.appcrafttestcase.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexjudin.appcrafttestcase.ui.activity.MainActivity
import com.alexjudin.appcrafttestcase.ui.adapter.AlbumAdapter
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.data.network.Resource
import com.alexjudin.appcrafttestcase.presentation.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.fragment_album.*
import kotlinx.android.synthetic.main.item_error.*


class AlbumFragment : Fragment(R.layout.fragment_album) {

    lateinit var viewModel: AlbumViewModel
    private lateinit var albumAdapter: AlbumAdapter

    private var loading = false
    private var error = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        bindRV()


        albumAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("album", it)
            }
            findNavController().navigate(

                R.id.action_AlbumFragment_to_photosFragment,
                bundle
            )
        }

        viewModel.albums.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    resource.data?.let { request ->
                        albumAdapter.differ.submitList(request.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    resource.message?.let {

                        showErrorMessage(it)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
        btnRetry.setOnClickListener {
            viewModel.getAlbum()
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        loading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        loading = true
    }

    private fun hideErrorMessage() {
        itemErrorMessage.visibility = View.INVISIBLE
        error = false
    }

    private fun showErrorMessage(message: String) {
        itemErrorMessage.visibility = View.VISIBLE
        tvErrorMessage.text = message
        this.error = true
    }


    private fun bindRV() {
        albumAdapter = AlbumAdapter()
        photos_RV.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}