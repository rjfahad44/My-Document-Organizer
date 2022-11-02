package com.ft.my_document_organizer.fragment.crop_fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import com.ft.my_document_organizer.base.BaseFragment
import com.ft.my_document_organizer.databinding.FragmentCropBinding
import com.ft.my_document_organizer.utils.listeners.CropFragmentClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CropFragment(private val cropFragmentClickListener: CropFragmentClickListener) :
    BaseFragment<FragmentCropBinding>() {

    override fun setBinding(): FragmentCropBinding = FragmentCropBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val bundle = arguments
        val result = bundle?.getString("result")
        Log.d("CropFragment", "Result : ${result.toString()}")

        binding.ivPhotoView.setImageURI(result?.toUri())

        binding.rlRetake.setOnClickListener {
            cropFragmentClickListener.retakeBtnClick("close")
        }
        binding.rlKeep.setOnClickListener {
            cropFragmentClickListener.keepBtnClick("close")
        }
    }
}