package com.kubaty.catfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubaty.catfacts.R
import com.kubaty.catfacts.adapter.CatFactsListRecyclerAdapter
import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.model.CatFact
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactListFragment : DaggerFragment(), CatFactsListRecyclerAdapter.OnFactClickListener {
    private lateinit var navController: NavController
    private lateinit var recyclerViewAdapter: CatFactsListRecyclerAdapter

    @Inject
    lateinit var factsController: FactsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        GlobalScope.launch { //todo move to VM
            val factsResponse = factsController.getFacts("cat", 30)
            withContext(Dispatchers.Main) {
                initRecyclerView(factsResponse.body()!!)
            }
        }
    }

    override fun onFactClick(factIndex: Int) {
        val fact = recyclerViewAdapter.getFactAt(factIndex)
        val bundle = bundleOf("updatedAt" to fact.updatedAt, "factText" to fact.text)
        navController.navigate(R.id.action_factListFragment_to_factDetails, bundle)
    }

    private fun initRecyclerView(facts: List<CatFact>) {
        rv_facts.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = CatFactsListRecyclerAdapter(facts, this)
        rv_facts.adapter = recyclerViewAdapter
    }
}
