package com.barretoareias.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.barretoareias.hangman.databinding.ActivityGameBinding
import com.barretoareias.hangman.databinding.NameFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.function.Consumer

class NameFragment(onNameDefined: Consumer<String>) : BottomSheetDialogFragment() {

    companion object { const val TAG = "NameFragment" }
    private lateinit var binding: NameFragmentBinding
    private val onNameDefined : Consumer<String>

    init {
        this.onNameDefined = onNameDefined
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NameFragmentBinding.inflate(layoutInflater)
        binding.btnNameConfirm.setOnClickListener{ onNameConfirmed() }

        return binding.root
    }

    private fun onNameConfirmed() {
        if(binding.txtName.text.isNullOrEmpty()) {
            Toast.makeText(this.context, R.string.name_label, Toast.LENGTH_SHORT).show()
            return
        }
        onNameDefined.accept(binding.txtName.text.toString())
        dismiss()
    }

}