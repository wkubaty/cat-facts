package com.kubaty.catfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.R
import com.kubaty.catfacts.di.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_details.*
import javax.inject.Inject

class FactDetailsFragment : DaggerFragment() {
    private lateinit var viewModel: FactDetailsViewModel

    @Inject
    lateinit var providerFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val updatedAt = arguments?.getString("updatedAt") ?: return
        val factText = arguments?.getString("factText") ?: return
        viewModel = ViewModelProvider(this, providerFactory).get(FactDetailsViewModel::class.java)
        viewModel.setFact(updatedAt, factText)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_details_update_date.text = viewModel.catFact.updatedAt
        tv_details_text.text = viewModel.catFact.text
    }
}
