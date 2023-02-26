package com.androidAssignment5.ui.mainActivity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidAssignment5.R
import com.androidAssignment5.databinding.RecycleviewAddContactItemBinding
import com.androidAssignment5.model.Contact
import com.androidAssignment5.util.DiffUtil


class AddContactRecyclerViewAdapter(private val contactClickListener: AddContactClickListener) :
    ListAdapter<Contact, AddContactRecyclerViewAdapter.Holder>(DiffUtil) {

    inner class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RecycleviewAddContactItemBinding.bind(item)
        fun bind(contact: Contact) {
            with(binding) {
                with(contact) {
                    tvContactName.text = name
                }
                ivAddContactPlus.setOnClickListener {
                    contactClickListener.onAddClick(contact.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_add_contact_item, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(currentList[position])
    }
}