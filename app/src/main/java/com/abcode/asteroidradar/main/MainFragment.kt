package com.abcode.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcode.asteroidradar.R
import com.abcode.asteroidradar.databinding.FragmentMainBinding
import com.abcode.asteroidradar.repository.FilterEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setObservables()

        return binding.root
    }

    private fun setObservables() {
        val asteroidAdapter =
            AsteroidAdapter(AsteroidAdapter.AsteroidListener { it -> viewModel.onAsteroidClicked(it) })
        binding.asteroidRecycler.apply {
            adapter = asteroidAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        viewModel.asteroids.observe(viewLifecycleOwner) { asteroidList ->
            asteroidAdapter.submitList(asteroidList)
        }

        viewModel.loadStatus.observe(viewLifecycleOwner) {
            when (it) {
                is LoadStatus.Idle -> {
                    binding.asteroidRecycler.visibility = View.VISIBLE
                    binding.statusLoadingWheel.visibility = View.GONE
                }
                is LoadStatus.Error -> {
                    binding.asteroidRecycler.visibility = View.GONE
                    binding.statusLoadingWheel.visibility = View.GONE
                    Toast.makeText(context, "Error Fetching from API", Toast.LENGTH_SHORT).show()
                }
                is LoadStatus.Loading -> {
                    binding.asteroidRecycler.visibility = View.GONE
                    binding.statusLoadingWheel.visibility = View.VISIBLE
                }
            }
        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner) { asteroid ->
            if (asteroid != null) {
                val action = MainFragmentDirections.actionShowDetail(asteroid)
                findNavController().navigate(action)
                viewModel.navigationDone()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterAsteroids(
            when (item.itemId) {
                R.id.show_rent_menu -> {
                    FilterEnum.TODAY
                }
                R.id.show_all_menu -> {
                    FilterEnum.WEEK
                }
                else -> {
                    FilterEnum.ALL
                }
            }
        )
        return true
    }
}
