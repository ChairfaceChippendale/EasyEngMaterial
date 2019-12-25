package com.ujujzk.ee.ui.dictionary.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.databinding.ItemDictionaryBinding
import com.ujujzk.ee.ui.model.VDictionary
import com.ujujzk.ee.ui.tools.inflate
import kotlin.properties.Delegates

class DicAdapter: RecyclerView.Adapter<DicAdapter.DicViewHolder>() {

    internal var data: List<VDictionary> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DicViewHolder(parent.inflate<ItemDictionaryBinding>(R.layout.item_dictionary))

    override fun onBindViewHolder(holder: DicViewHolder, pos: Int) =
        holder.bind(data[pos])

    override fun getItemCount() =
        data.size

    inner class DicViewHolder(private val binding: ItemDictionaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionary: VDictionary) {
            binding.dictionary = dictionary
            binding.executePendingBindings()
        }
    }
}