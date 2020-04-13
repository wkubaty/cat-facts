package com.kubaty.catfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.R
import com.kubaty.catfacts.di.viewmodel.ViewModelFactory
import com.kubaty.catfacts.model.CatFact
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_fact_details.*
import javax.inject.Inject

class FactDetailsFragment : DaggerFragment() {
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fact = arguments?.getParcelable<CatFact>("fact")
            ?: throw IllegalStateException("Fact not serialized in details fragment.")
        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        viewModel.setFact(fact)
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
        showDetails()
    }

    private fun showDetails() {
        tv_details_update_date.text = viewModel.catFact.updatedAt.toString()
        tv_details_text.text = viewModel.catFact.text
        iv_details_cat_icon.setImageResource(viewModel.catFact.drawableId)
    }

    private fun configureToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(toolbar_fact_details)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar_fact_details.setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }
}
