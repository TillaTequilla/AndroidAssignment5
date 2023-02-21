package com.androidAssignment5.ui.mainActivity.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseDialogFragment
import com.androidAssignment5.util.Constance.ADD_CONTACT_RESULT_KEY
import com.androidAssignment5.databinding.AddContactBinding
import com.androidAssignment5.extension.setSizePercent
import com.androidAssignment5.extension.toast
import com.androidAssignment5.model.Contact
import com.androidAssignment5.util.Constance


class DialogFragmentAddContact : BaseDialogFragment<AddContactBinding>(AddContactBinding::inflate) {

    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSizePercent(90, 90)
        setClickListeners()
    }

    private fun setClickListeners() {
        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    imageUri = result.data?.data
                    binding.ivAddContactPhoto.setImageURI(imageUri)
                }
            }
        binding.btnSaveContact.setOnClickListener {
            if (binding.etUsernameNew.text!!.isEmpty()) {
                context?.toast(getString(R.string.contacts_toast_noInformation))
            } else {
                val contact = createContact()
                addContactToActivity(contact)
                dismiss()
            }
            binding.ivAddContactChoosePhoto.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                launcher.launch(intent)
            }
        }
    }

    private fun addContactToActivity(contact: Contact) {
        val bundle = Bundle()
        bundle.putParcelable(Constance.CONTACT_SERIALIZABLE, contact)
        setFragmentResult(ADD_CONTACT_RESULT_KEY, bundle)
    }

    private fun createContact(): Contact {
        binding.run {
            return Contact(
                imageUri.toString(),
                etUsernameNew.text.toString(),
                etCareerNew.text.toString(),
                etEmailNew.text.toString(),
                etPhoneNew.text.toString(),
                etAddressNew.text.toString(),
                etDateOfBirthNew.text.toString()
            )
        }
    }
}