package com.barretoareias.hangman.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.barretoareias.hangman.R

class LettersAdapter(private var mContext : Context, var resources : Int, var  word: List<Char>) :
    ArrayAdapter<Char>(mContext, resources, word) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(resources, null)
        val txtLetter = view.findViewById<TextView>(R.id.txtLetter)
        txtLetter.text = word[position].toString()
        return view
    }
}