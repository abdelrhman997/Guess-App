package com.example.guessapp.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.guessapp.R
import com.example.guessapp.databinding.TitleFragmentBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

        binding.button.setOnClickListener{
           findNavController().navigate(TitleFragmentDirections.actionTitleToGame())
        }
        return binding.root
    }
}
