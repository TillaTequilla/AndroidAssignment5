package com.androidAssignment5.ui.mainActivity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidAssignment5.architecture.BaseDialogFragment
import com.androidAssignment5.databinding.AddContactBinding
import com.androidAssignment5.extension.setSizePercent
import com.androidAssignment5.ui.mainActivity.adapters.AddContactClickListener
import com.androidAssignment5.ui.mainActivity.adapters.AddContactRecyclerViewAdapter
import com.androidAssignment5.util.Constance


class DialogFragmentAddContact : BaseDialogFragment<AddContactBinding>(AddContactBinding::inflate) {

    private val contactViewModel: DialogFragmentAddContactViewModel by activityViewModels()
    private val adapter: AddContactRecyclerViewAdapter by lazy {
       AddContactRecyclerViewAdapter(contactClickListener = object : AddContactClickListener{
           override fun onAddClick(id: String) {
               contactViewModel.addContact(requireActivity().intent.getStringExtra(Constance.INTENT_ID).toString(),"Bearer "+ requireActivity().intent.getStringExtra(Constance.INTENT_ACCESS_TOKEN).toString(),id)
           }
       })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSizePercent(90, 90)
        binding.recyclerViewAddContact.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewAddContact.adapter = adapter
        contactViewModel.usersList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val token =
            requireActivity().intent.getStringExtra(Constance.INTENT_ACCESS_TOKEN).toString()
        contactViewModel.setData("Bearer " + token)

        binding.ivContactBack.setOnClickListener {
            println(contactViewModel.usersList.value)
        }

    }


}