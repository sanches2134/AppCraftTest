package com.alexjudin.appcrafttestcase.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexjudin.appcrafttestcase.ui.activity.MainActivity
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.data.network.Resource
import com.alexjudin.appcrafttestcase.presentation.viewmodel.AlbumViewModel
import com.alexjudin.appcrafttestcase.ui.adapter.PhotosAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.itemErrorMessage
import kotlinx.android.synthetic.main.fragment_photos.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_photos.photos_RV
import kotlinx.android.synthetic.main.item_error.*


class PhotosFragment : Fragment(R.layout.fragment_photos) {

    lateinit var viewModel: AlbumViewModel
    private lateinit var photosAdapter: PhotosAdapter
    private val args: PhotosFragmentArgs by navArgs()

    var loading = false
    private var error = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        val album = args.album

        bindRV()

        album.id?.let { viewModel.getPhotos(it) }

        viewModel.photos.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { request ->
                        hideErrorMessage()
                        photosAdapter.differ.submitList(request.toList())

                    }
                }
                is Resource.Error -> {
                    resource.message?.let {
                        showErrorMessage(it)
                    }
                }

            }
        }

        btnRetry.setOnClickListener {
            album.id?.let { it1 -> viewModel.getPhotos(it1) }
        }

        fab.setOnClickListener {
            viewModel.saveAlbum(album)
            Snackbar.make(view, "Новость сохранена!", Snackbar.LENGTH_SHORT).show()
        }

        back.setOnClickListener {
            findNavController().navigate(
                R.id.action_photosFragment_to_AlbumFragment
            )
        }
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
        photosAdapter = context?.let { PhotosAdapter(it) }!!
        photos_RV.apply {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}