package com.schaefer.mymovies.presentation.home

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.schaefer.mymovies.R
import com.schaefer.mymovies.presentation.adapters.home.HomeListAdapter
import com.schaefer.mymovies.presentation.adapters.home.OnItemClickListener
import com.schaefer.mymovies.presentation.details.DetailsActivity
import com.schaefer.mymovies.presentation.model.Show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

private const val BUNDLE_SHOW = "show"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), OnItemClickListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private val homeShowAdapter = HomeListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        homeViewModel.getShows()
        setupView()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //TODO FIX the search
        inflater.inflate(R.menu.menu_home, menu)
        val manager = activity?.getSystemService(AppCompatActivity.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.menu_action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.getShowsBySearch(newText?.trim())
                return false
            }
        })
    }

    private fun setupView() {
        rvHomeShows.apply {
            adapter = homeShowAdapter
        }

        srlHomeFragment.setOnRefreshListener { homeViewModel.getShows() }
    }

    private fun setupObservers() {
        homeViewModel.state.observe(viewLifecycleOwner, {
            srlHomeFragment.isRefreshing = it.isLoading
        })

        homeViewModel.listShow.observe(viewLifecycleOwner, {
            homeShowAdapter.shows = it
        })

        homeViewModel.action.observe(viewLifecycleOwner, {
            when (it) {
                is HomeAction.NavigateToDetails -> {
                    startActivity(Intent(context, DetailsActivity::class.java).putExtra(BUNDLE_SHOW, it.show))
                }
            }
        })
    }

    override fun onItemClick(show: Show) {
        homeViewModel.navigateToDetails(show)
    }
}