package com.alexjudin.appcrafttestcase.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexjudin.appcrafttestcase.ui.activity.MainActivity
import com.alexjudin.appcrafttestcase.ui.adapter.AlbumAdapter
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.presentation.viewmodel.AlbumViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_albums.*


class SavedAlbumFragment : Fragment(R.layout.fragment_saved_albums) {


    lateinit var viewModel: AlbumViewModel
    lateinit var albumAdapter: AlbumAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        bindRV()

        albumAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("album", it)
                putSerializable("screenType",1)

            }
            findNavController().navigate(
                    R.id.action_savedAlbumsFragment_to_photosFragment,
                    bundle
            )
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = albumAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Альбом удален!", Snackbar.LENGTH_LONG).apply {
                    setAction("Вернуть") {
                        viewModel.saveAlbum(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            albumAdapter.differ.submitList(it)
        }

    }

    private fun bindRV() {
        albumAdapter = AlbumAdapter()
        rvSavedNews.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}