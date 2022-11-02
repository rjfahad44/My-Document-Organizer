package com.ft.my_document_organizer.ui.activities.camera

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.ft.my_document_organizer.utils.Constants.Companion.FILENAME_FORMAT
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(
    private val owner: AppCompatActivity,
    private val context: Context,
    private val viewPreview: PreviewView,
    private val onSuccess: (result: String) -> Unit,
    private val onError: (error: String) -> Unit,
) {

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null

    fun stop() {
        cameraExecutor.shutdown()
    }

    fun enableFlash(onFlashMode: Boolean) {
        camera?.cameraControl?.enableTorch(onFlashMode)
    }

    private fun controlWhichCameraToDisplay(frontCamera: Boolean?): Int {
        lensFacing = when (frontCamera) {
            true -> CameraSelector.LENS_FACING_FRONT
            else -> CameraSelector.LENS_FACING_BACK
        }
        return lensFacing
    }

    fun startCamera(onFrontCamera: Boolean?) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            controlWhichCameraToDisplay(frontCamera = onFrontCamera)
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindCameraUseCases() {
        cameraProvider?.let { cameraProvider ->
            val cameraSelector = getCameraSelector()
            val previewView = getPreviewUseCase()
            imageCapture = getImageCapture()
            cameraProvider.unbindAll()
            try {
                camera = cameraProvider.bindToLifecycle(
                    owner,
                    cameraSelector,
                    previewView,
                    imageCapture
                )

                camera?.cameraControl?.setLinearZoom(0.0f)

                previewView.setSurfaceProvider(viewPreview.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed $exc")
            }
        } ?: throw IllegalStateException("Camera initialization failed.")
    }

    private fun getCameraSelector(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
    }

    private fun getImageCapture(): ImageCapture {
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
    }

    private fun getPreviewUseCase(): Preview {
        return Preview.Builder().setTargetRotation(viewPreview.display.rotation).build()
    }

    fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            owner.cacheDir,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(owner), object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onSuccess(Uri.fromFile(photoFile).toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception.toString())
                }
            }
        )
    }

}
