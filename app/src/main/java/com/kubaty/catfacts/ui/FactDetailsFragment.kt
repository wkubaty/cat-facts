package com.kubaty.catfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.R
import com.kubaty.catfacts.di.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_details.*
import java.util.*
import javax.inject.Inject

class FactDetailsFragment : DaggerFragment() {
    private lateinit var viewModel: FactDetailsViewModel

    @Inject
    lateinit var providerFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val updatedAt = arguments?.getSerializable("updatedAt") as Date? ?: return
        val factText = arguments?.getString("factText") ?: return
        val drawableId = arguments?.getInt("drawableId") ?: return
        viewModel = ViewModelProvider(this, providerFactory).get(FactDetailsViewModel::class.java)
        viewModel.setFact(updatedAt, factText, drawableId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        tv_details_update_date.text = viewModel.catFact.updatedAt.toString()
        tv_details_text.text = viewModel.catFact.text
        iv_details_cat_icon.setImageResource(viewModel.catFact.drawableId)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar_compare)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar_compare.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}
