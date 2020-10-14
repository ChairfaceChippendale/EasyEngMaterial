package com.ujujzk.ee.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ujujzk.ee.catalog.databinding.ItemDictionaryBinding
import com.ujujzk.ee.presentation.model.VDictionary
import java.lang.IllegalStateException


class DicAdapter(): ListAdapter<VDictionary, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DicViewHolder(ItemDictionaryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is DicViewHolder -> { holder.bind(getItem(position)) }
            else -> throw IllegalStateException("Unknown ViewHolder: ${holder.javaClass.name}")
        }
    }

    inner class DicViewHolder(private val binding: ItemDictionaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionary: VDictionary) {
            binding.name.text = dictionary.dictionaryName
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VDictionary>() {
            override fun areItemsTheSame(
                oldItem: VDictionary,
                newItem: VDictionary
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VDictionary,
                newItem: VDictionary
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}