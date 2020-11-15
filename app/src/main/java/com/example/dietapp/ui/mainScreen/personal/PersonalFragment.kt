package com.example.dietapp.ui.mainScreen.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.R

class PersonalFragment : Fragment() {

    private lateinit var personalViewModel: PersonalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        personalViewModel =
            ViewModelProvider(this).get(PersonalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_personal, container, false)
        val textView: TextView = root.findViewById(R.id.tvText)
        personalViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}