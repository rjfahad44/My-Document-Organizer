package com.ft.my_document_organizer.ui.activities.camera

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityCameraBinding
import com.ft.my_document_organizer.fragment.crop_fragment.CropFragment
import com.ft.my_document_organizer.ui.activities.add_document.AddDocumentActivity
import com.ft.my_document_organizer.ui.viewmodel.DocViewModel
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import com.ft.my_document_organizer.utils.Loader
import com.ft.my_document_organizer.utils.listeners.CropFragmentClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CameraActivity : BaseActivity<ActivityCameraBinding>(), CropFragmentClickListener {

    val viewModel: DocViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    var loader: Loader? = null

    private lateinit var cameraManager: CameraManager
    private var changeCamera = false
    private var flashMode = false

    override fun setBinding(): ActivityCameraBinding = ActivityCameraBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        loader = Loader(this)
        startCamera()
        controlCameraSelectCamera()
        controlFlashCamera()
        takePhoto()
        close()
        binding.rlDone.setOnClickListener {
            cameraManager.stop()
            startActivity(Intent(this@CameraActivity, AddDocumentActivity::class.java))
        }
    }

    override fun retakeBtnClick(any: Any) {
        binding.container.isVisible = true
    }

    override fun keepBtnClick(any: Any) {
        binding.container.isVisible = true
    }

    private fun startCamera() {
        cameraManager = CameraManager(
            owner = this,
            context = applicationContext,
            viewPreview = binding.pvViewCamera,
            onSuccess = ::onSuccess,
            onError = ::onError
        )
        cameraManager.startCamera(onFrontCamera = false)
    }

    private fun takePhoto() {
        binding.ivCameraCapture.setOnClickListener {
            loader?.showLoader()
            cameraManager.takePhoto()
        }
    }

    private fun controlCameraSelectCamera() {
        binding.ivChangeCamera.setOnClickListener {
            cameraManager.startCamera(onFrontCamera = changeCamera())
        }
    }

    private fun controlFlashCamera() {
        binding.ivFlashCamera.setOnClickListener {
            cameraManager.enableFlash(flashMode())
        }
    }

    private fun changeCamera(): Boolean {
        changeCamera = !changeCamera
        if (changeCamera) binding.ivFlashCamera.setImageResource(R.drawable.ic_flash_off)
        return changeCamera
    }

    private fun flashMode(): Boolean {
        flashMode = !flashMode
        if (flashMode) binding.ivFlashCamera.setImageResource(R.drawable.ic_flash_on)
        else binding.ivFlashCamera.setImageResource(R.drawable.ic_flash_off)
        return flashMode
    }

    private fun onSuccess(result: String?) {
        if (!result.isNullOrEmpty()){
            loader?.hideLoader()
            binding.ivPhotoPreview.setImageURI(result.toUri())
            binding.container.visibility = ViewGroup.VISIBLE
            val bundle = Bundle()
            val cropFragment = CropFragment(this)
            cropFragment.arguments = bundle
            bundle.putString("result", result)
            Timber.d("CameraActivity", "Result : $result")
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, cropFragment, "finish").commit()
        }
        //finish()
    }

    private fun onError(result: String?) {
        loader?.hideLoader()
        if (!result.isNullOrEmpty()) {
            Toast.makeText(this@CameraActivity, "$result", Toast.LENGTH_SHORT).show()
        }
        //finish()
    }

    private fun close() {
        binding.tvCancel.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.stop()
    }

    override fun onBackPressed() {
        finish()
    }
}