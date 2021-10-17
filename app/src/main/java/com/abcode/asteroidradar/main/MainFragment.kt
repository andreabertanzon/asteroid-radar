package com.abcode.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcode.asteroidradar.R
import com.abcode.asteroidradar.databinding.FragmentMainBinding
import com.abcode.asteroidradar.repository.FilterEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setObservables()

        return binding.root
    }

    private fun setObservables(){
        val asteroidAdapter = AsteroidAdapter()
        binding.asteroidRecycler.apply {
            adapter = asteroidAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        viewModel.asteroids.observe(viewLifecycleOwner) { asteroidList->
            asteroidAdapter.submitList(asteroidList)
        }
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
