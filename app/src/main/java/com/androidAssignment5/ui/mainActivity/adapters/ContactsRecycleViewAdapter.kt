package com.androidAssignment5.ui.mainActivity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidAssignment5.R
import com.androidAssignment5.databinding.RecycleviewContactItemBinding
import com.androidAssignment5.extension.addCircularImage
import com.androidAssignment5.model.Contact
import com.androidAssignment5.util.DiffUtil

class ContactsRecycleViewAdapter(private val contactClickListener: ContactsClickListener) :
    ListAdapter<Contact, ContactsRecycleViewAdapter.Holder>(DiffUtil) {

    lateinit var selectionTracker: SelectionTracker<Contact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_contact_item, parent, false)
        return Holder(itemView)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(currentList[position], selectionTracker)
    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RecycleviewContactItemBinding.bind(item)
        fun bind(contact: Contact, selectionTracker: SelectionTracker<Contact>) = with(binding) {
            with(contact) {
                tvContactName.text = name
                tvContactCareer.text = career
                if (image == "null") {
                    ivContactPhoto.setImageResource(R.drawable.icon_default_photo)
                } else {
                    ivContactPhoto.addCircularImage(image)
                }
                IvRemoveContact.setOnClickListener {
                    contactClickListener.onDeleteClick(contact)
                }
                itemView.setOnClickListener {
                    contactClickListener.onContactClick(this)
                }
            }
            bindSelectedState(itemView, selectionTracker.isSelected(contact))
            if (selectionTracker.isSelected(contact)) {
                ivContactBorder.setImageResource(R.drawable.selected_user_border)
            } else {
                ivContactBorder.setImageResource(R.drawable.user_border)
            }


        }

        private fun bindSelectedState(view: View, selected: Boolean) {
            view.isActivated = selected
        }

        fun getItemDetails(): ItemDetails<Contact> =
            object : ItemDetails<Contact>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Contact? =
                    (bindingAdapter as ContactsRecycleViewAdapter).currentList[bindingAdapterPosition]
            }

    }


}
