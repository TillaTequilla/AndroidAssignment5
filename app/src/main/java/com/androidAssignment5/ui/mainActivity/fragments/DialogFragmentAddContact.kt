package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidAssignment5.architecture.BaseDialogFragment
import com.androidAssignment5.databinding.FragmentAddContactBinding
import com.androidAssignment5.extension.setSizePercent
import com.androidAssignment5.ui.mainActivity.MainActivityViewModel
import com.androidAssignment5.ui.mainActivity.adapters.AddContactClickListener
import com.androidAssignment5.ui.mainActivity.adapters.AddContactRecyclerViewAdapter


class DialogFragmentAddContact :
    BaseDialogFragment<FragmentAddContactBinding>(FragmentAddContactBinding::inflate) {

    private val allContactViewModel: DialogFragmentAddContactViewModel by activityViewModels()
    private val contactViewModel: ContactsViewModel by activityViewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val adapter: AddContactRecyclerViewAdapter by lazy {
        AddContactRecyclerViewAdapter(contactClickListener = object : AddContactClickListener {
            override fun onAddClick(id: String) {
                contactViewModel.addContact(
                    activityViewModel.getId(),
                    activityViewModel.getToken(),
                    id
                )
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSizePercent(90, 90)
        binding.recyclerViewAddContact.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewAddContact.adapter = adapter

        allContactViewModel.usersList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.pbContactsLoading.visibility = View.GONE
            binding.recyclerViewAddContact.visibility = View.VISIBLE
        }

        allContactViewModel.setData(activityViewModel.getToken())

        binding.ivContactBack.setOnClickListener {
            dismiss()
        }

        binding.ivContactsSearch.setOnClickListener {
            binding.tilSearchUser.visibility = View.VISIBLE
        }
        binding.etSearchUser.doAfterTextChanged { text ->
            if (text!!.isNotEmpty()) {
                val list = allContactViewModel.usersList.value?.filter {
                    it?.name?.contains(text.toString(), true) ?: false

                }
                adapter.submitList(list)
            } else {
                adapter.submitList(allContactViewModel.usersList.value)
            }
        }

    }


}