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
import com.kubaty.catfacts.R
import com.kubaty.catfacts.adapter.CatFactsListRecyclerAdapter
import com.kubaty.catfacts.di.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_list.*
import javax.inject.Inject

class FactListFragment : DaggerFragment(), CatFactsListRecyclerAdapter.OnFactClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    private lateinit var navController: NavController
    private lateinit var recyclerViewAdapter: CatFactsListRecyclerAdapter
    private lateinit var viewModel: FactListViewModel

    @Inject
    lateinit var providerFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, providerFactory).get(FactListViewModel::class.java)
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

        viewModel.getFactsLiveData().observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.updateData(it)
            rv_facts.adapter?.notifyDataSetChanged()
        })

        swipe_layout.setOnRefreshListener(this)
    }

    override fun onFactClick(factIndex: Int) {
        val fact = recyclerViewAdapter.getFactAt(factIndex)
        val bundle = bundleOf("updatedAt" to fact.updatedAt, "factText" to fact.text)
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
                viewModel.getNewFacts(amount = CATS_AMOUNT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        viewModel.getNewFacts(amount = CATS_AMOUNT)
        swipe_layout.isRefreshing = false
    }

    companion object {
        const val CATS_AMOUNT = 30
    }
}
