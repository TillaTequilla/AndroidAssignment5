package com.androidAssignment5.util

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.androidAssignment5.ui.mainActivity.adapters.ContactsRecyclerViewAdapter
import com.androidAssignment5.model.Contact

class ContactLookUp(private val recyclerView: RecyclerView) : ItemDetailsLookup<Contact>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Contact>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ContactsRecyclerViewAdapter.Holder).getItemDetails()
        }
        return null
    }

}