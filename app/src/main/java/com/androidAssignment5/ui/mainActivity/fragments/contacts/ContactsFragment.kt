package com.androidAssignment5.ui.mainActivity.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.androidAssignment5.ui.mainActivity.adapters.ContactsRecyclerViewAdapter
import com.androidAssignment5.architecture.BaseFragment
import com.androidAssignment5.model.Contact
import com.androidAssignment5.R
import com.androidAssignment5.util.Constance
import com.androidAssignment5.util.SwipeToDeleteCallback
import com.androidAssignment5.databinding.FragmentContactsBinding
import com.androidAssignment5.ui.mainActivity.MainActivityViewModel
import com.androidAssignment5.ui.mainActivity.adapters.ContactsClickListener
import com.androidAssignment5.ui.mainActivity.fragments.addContact.DialogFragmentAddContact
import com.androidAssignment5.util.ContactLookUp
import com.androidAssignment5.util.KeyProvider
import com.google.android.material.snackbar.Snackbar


class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    lateinit var tracker: SelectionTracker<Contact>
    private val contactViewModel: ContactsViewModel by activityViewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val adapter: ContactsRecyclerViewAdapter by lazy {
        ContactsRecyclerViewAdapter(contactClickListener = object : ContactsClickListener {
            override fun onDeleteClick(contact: Contact) {
                contactViewModel.deleteContact(
                    activityViewModel.getId(),
                    contact.id,
                    activityViewModel.getToken()
                )
                undoUserDeletion(binding.root, contact)
            }

            override fun onContactClick(contact: Contact) {
                val dialog = DialogFragmentShowContact()
                val args = Bundle()
                args.putParcelable(Constance.CONTACT_SERIALIZABLE, contact)
                dialog.arguments = args
                dialog.show(parentFragmentManager, "showContact")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setClickListeners()
        addTracker()
        setObservers()
        contactViewModel.getUsers(activityViewModel.getId(), activityViewModel.getToken())
    }

    private fun setClickListeners() {
        binding.ivMultiselectTrash.setOnClickListener {
            deleteSelectedContacts(tracker.selection)
        }
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.ivContactBack.setOnClickListener {
            viewPager?.currentItem = 0
        }

        binding.ivContactBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvAddContact.setOnClickListener {
            val dialog = DialogFragmentAddContact()
            dialog.show(parentFragmentManager, "addContact")
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val contact =
                    contactViewModel.contactList.value?.get(viewHolder.absoluteAdapterPosition)
                val id = contact?.id.toString()
                contactViewModel.deleteContact(
                    activityViewModel.getId(),
                    id,
                    activityViewModel.getToken()
                )
                undoUserDeletion(binding.root, contact)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }


    private fun addTracker() {
        tracker = SelectionTracker.Builder(
            "selection-1",
            binding.recyclerView,
            KeyProvider(adapter),
            ContactLookUp(binding.recyclerView),
            StorageStrategy.createParcelableStorage(Contact::class.java)
        ).build()
        adapter.selectionTracker = tracker
    }

    private fun setObservers() {
        tracker.addObserver(object : SelectionTracker.SelectionObserver<Contact>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                binding.ivMultiselectTrash.isVisible = tracker.hasSelection()
            }
        })
        contactViewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun deleteSelectedContacts(selection: Selection<Contact>) {
        val list = selection.toList()
        contactViewModel.deleteSelected(
            list, activityViewModel.getId(),
            activityViewModel.getToken()
        )
    }

    private fun undoUserDeletion(view: View, contact: Contact?) {
        val delMessage = Snackbar.make(
            view,
            getString(R.string.contacts_sbRemoved, contact!!.name),
            Snackbar.LENGTH_LONG
        )
        delMessage.setAction("Cancel") {
            contactViewModel.addContact(
                activityViewModel.getId(),
                activityViewModel.getToken(),
                contact.id
            )
        }.show()
    }

}