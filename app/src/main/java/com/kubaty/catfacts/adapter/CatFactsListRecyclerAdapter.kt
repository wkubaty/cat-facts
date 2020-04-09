package com.kubaty.catfacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubaty.catfacts.R
import com.kubaty.catfacts.model.CatFact
import kotlinx.android.synthetic.main.cat_list_item.view.*

class CatFactsListRecyclerAdapter(
    private var catFacts: List<CatFact>,
    private val onFactClickListener: OnFactClickListener
) : RecyclerView.Adapter<CatFactsListRecyclerAdapter.FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        return FactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cat_list_item,
                parent,
                false
            )
            , onFactClickListener
        )
    }

    override fun getItemCount(): Int = catFacts.size

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(catFacts[position])
    }

    fun getFactAt(factIndex: Int): CatFact {
        return catFacts[factIndex]
    }

    fun updateData(catFacts: List<CatFact>) {
        this.catFacts = catFacts
    }

    inner class FactViewHolder(itemView: View, onFactClickListener: OnFactClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val factId = itemView.tv_fact_id

        init {
            itemView.setOnClickListener {
                onFactClickListener.onFactClick(adapterPosition)
            }
        }

        fun bind(fact: CatFact) {
            factId.text = fact._id
        }
    }

    interface OnFactClickListener {
        fun onFactClick(factIndex: Int)
    }
}
