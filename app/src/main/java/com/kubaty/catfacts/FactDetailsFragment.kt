package com.kubaty.catfacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_fact_details.*

class FactDetailsFragment : Fragment() {
    private lateinit var viewModel: FactDetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_details_update_date.text = viewModel.catFact.updatedAt
        tv_details_text.text = viewModel.catFact.text
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val updatedAt = arguments?.getString("updatedAt") ?: return
        val factText = arguments?.getString("factText") ?: return
        viewModel = ViewModelProvider(this).get(FactDetailsViewModel::class.java)
        viewModel.setFact(updatedAt, factText)
    }
}
