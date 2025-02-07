package com.oneteam.presentation.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneteam.app.R
import com.oneteam.app.databinding.FragmentImageBinding
import com.oneteam.common.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_image) {

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var currentCategory: String
    private lateinit var binding: FragmentImageBinding
    private val viewModel: ImageViewModel by activityViewModels()
    private var category: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)

        onInitUi()
        onInitObservers(category)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
    }

    override fun onPause() {
        super.onPause()
        binding.searchView.setQuery("", false)
    }

    private fun onInitObservers(category: String) {
        viewModel.launchOnMain {
            viewModel.getImages(category).collectLatest { pagingData ->
                viewModel.search.collectLatest { searchQuery ->
                    val filteredData = pagingData.filter { image ->
                        image.alt.contains(searchQuery, ignoreCase = true)
                    }
                    imageAdapter.submitData(filteredData)
                }
            }
        }
        viewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (!isConnected) {
                Toast.makeText(
                    requireContext(),
                    getText(R.string.no_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        onPagerAdapterLoadState()
    }

    private fun onInitUi() {
        category = arguments?.getString(Constant.CATEGORY) ?: Constant.CATEGORY_NATURE

        when (category) {
            Constant.CATEGORY_NATURE -> binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.nature_drawable
                )
            )

            Constant.CATEGORY_CITY -> binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.city_drawable
                )
            )

            Constant.CATEGORY_ANIMAL -> binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.animal_drawable
                )
            )
        }

        imageAdapter = ImageAdapter()
        onInitRv()
        currentCategory = category
        binding.searchView.setQuery("", false)
        viewModel.emptySearch()
        viewModel.getImages(currentCategory)

        onSetUpSearch()
    }

    private fun onSetUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.onSearchQueryChanged(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.onSearchQueryChanged(it) }
                return true
            }
        })
    }

    private fun onInitRv() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = imageAdapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun onPagerAdapterLoadState() {
        imageAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> { }

                is LoadState.NotLoading -> {
                    if (imageAdapter.itemCount == 0) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_no_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    Toast.makeText(
                        requireContext(),
                        error.message ?: getString(R.string.error_common),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        fun newInstance(category: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString(Constant.CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}