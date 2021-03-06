package com.flamingo.dietapp.ui.mainScreen.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flamingo.dietapp.BuildConfig
import com.flamingo.dietapp.Preferences
import com.flamingo.dietapp.R
import com.flamingo.dietapp.repository.TestRepository
import kotlinx.android.synthetic.main.fragment_personal.*

class PersonalFragment : Fragment() {

    private lateinit var personalViewModel: PersonalViewModel

    val preferences by lazy { Preferences(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        personalViewModel =
            ViewModelProvider(this).get(PersonalViewModel::class.java)
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvVersion.text = "version ${BuildConfig.VERSION_NAME}"
        swchUseTestData.isChecked = preferences.useTestRepository
        swchUseTestData.setOnCheckedChangeListener { buttonView, isChecked ->
            preferences.useTestRepository = isChecked
            TestRepository.regenerate()
        }
    }
}