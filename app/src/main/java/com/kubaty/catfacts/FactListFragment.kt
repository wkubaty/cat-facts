package com.kubaty.catfacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubaty.catfacts.adapter.CatFactsListRecyclerAdapter
import com.kubaty.catfacts.model.CatFact
import kotlinx.android.synthetic.main.fragment_fact_list.*

class FactListFragment : Fragment(), CatFactsListRecyclerAdapter.OnFactClickListener {
    private lateinit var navController: NavController
    private lateinit var recyclerViewAdapter: CatFactsListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecyclerView()
    }

    override fun onFactClick(factIndex: Int) {
        val fact = recyclerViewAdapter.getFactAt(factIndex)
        val bundle = bundleOf("updatedAt" to fact.updatedAt, "factText" to fact.text)
        navController.navigate(R.id.action_factListFragment_to_factDetails, bundle)
    }

    private fun initRecyclerView() {
        rv_facts.layoutManager = LinearLayoutManager(context)
        val tmpFacts = listOf(CatFact("1", "10.10.2020", "Test cat")) // todo
        recyclerViewAdapter = CatFactsListRecyclerAdapter(tmpFacts, this)
        rv_facts.adapter = recyclerViewAdapter
    }
}
