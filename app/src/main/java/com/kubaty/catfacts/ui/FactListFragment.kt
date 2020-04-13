package com.kubaty.catfacts.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.kubaty.catfacts.R
import com.kubaty.catfacts.adapter.CatFactsListRecyclerAdapter
import com.kubaty.catfacts.di.viewmodel.ViewModelFactory
import com.kubaty.catfacts.ui.state.MainStateEvent
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_list.*
import javax.inject.Inject

class FactListFragment : DaggerFragment(), CatFactsListRecyclerAdapter.OnFactClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    private lateinit var navController: NavController
    private lateinit var recyclerViewAdapter: CatFactsListRecyclerAdapter
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_fact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar_fact_list)
        navController = Navigation.findNavController(view)
        initRecyclerView()

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.facts?.let {
                        viewModel.setViewStateFacts(it, requireContext())
                    }
                }
            }

            swipe_layout.isRefreshing = dataState.loading
            if (!dataState.loading) {
                handleInitialErrorInfo(viewModel.viewState.value?.facts.isNullOrEmpty())
            }
            dataState.message?.let { event ->
                event.getContentIfNotHandled()?.let {
                    showErrorSnackbar(it)
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { mainViewState ->
            mainViewState.facts?.let { facts ->
                recyclerViewAdapter.updateData(facts)
                rv_facts.adapter?.notifyDataSetChanged()
            }
        })
        swipe_layout.setOnRefreshListener(this)

    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(ll_fragment_fact_list, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onFactClick(factIndex: Int) {
        val fact = recyclerViewAdapter.getFactAt(factIndex)
        val bundle = bundleOf(
            "fact" to fact
        )
        navController.navigate(R.id.action_factListFragment_to_factDetails, bundle)
    }

    private fun initRecyclerView() {
        rv_facts.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = CatFactsListRecyclerAdapter(listOf(), this)
        rv_facts.adapter = recyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.facts_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                onRefresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        viewModel.setStateEvent(MainStateEvent.LoadFactsEvent())
    }

    private fun handleInitialErrorInfo(isListEmpty: Boolean) {
        ll_network_error_info.visibility = if (isListEmpty) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "FactListFragment"
    }
}
