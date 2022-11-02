package com.ft.my_document_organizer.fragment.profile_fragment

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ft.my_document_organizer.DocApplication
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseFragment
import com.ft.my_document_organizer.databinding.FragmentProfileBinding
import com.ft.my_document_organizer.state.NetworkState
import com.ft.my_document_organizer.ui.activities.MainActivity
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import com.ft.my_document_organizer.utils.AppUtils.errorDialog
import com.ft.my_document_organizer.utils.AppUtils.successDialog
import com.ft.my_document_organizer.utils.Constants.Companion.COMMON_NOT_FOUND
import com.ft.my_document_organizer.utils.Constants.Companion.COMMON_UNPROCESSABLE_ENTITY
import com.ft.my_document_organizer.utils.Constants.Companion.RESET_PASSWORD_SUCCESS
import com.ft.my_document_organizer.utils.EspressoIdlingResource
import com.ft.my_document_organizer.utils.Loader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    lateinit var userViewModel: UserViewModel
    private lateinit var resetPassword: Dialog
    var loader: Loader? = null

    override fun setBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).userViewModel
        initialize()
    }

    private fun initialize(){
        loader = Loader(requireActivity())
        binding.toolbar.ivActionBarBack.isVisible = false
        binding.toolbar.tvActionBarTitle.text = getString(R.string.profile)

        setupChangePasswordObservers()

        binding.rvResetPassword.setOnClickListener {
            resetPasswordDialog()
        }
    }

    private fun resetPasswordDialog() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_reset_new_password)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        resetPassword = dialog
        dialog.setCancelable(false)

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.tvResetPassword).setOnClickListener {
            val oldPassword = dialog.findViewById<TextInputEditText>(R.id.tietOldPassword).text.toString()
            val newPassword = dialog.findViewById<TextInputEditText>(R.id.tietNewPassword).text.toString()
            val reNewPassword = dialog.findViewById<TextInputEditText>(R.id.tietRetypeNewPassword).text.toString()

            if (oldPassword.isNotEmpty() || newPassword.isNotEmpty() || reNewPassword.isNotEmpty()) {
                if (newPassword == reNewPassword){
                    val resetPasswordMap = HashMap<String, String>()
                    resetPasswordMap["old_password"] = oldPassword
                    resetPasswordMap["new_password"] = newPassword
                    resetPasswordMap["confirm_password"] = reNewPassword
                    val token = "Bearer ${DocApplication.sp.getString("access_token", null)}"

                    userViewModel.changePassword(resetPasswordMap, token)
                }else{
                    Toast.makeText(context, "New Password not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    private fun setupChangePasswordObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.changePasswordResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader!!.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()
                            when (newResponse.http_code) {
                                RESET_PASSWORD_SUCCESS -> {
                                    //Successful Response
                                    resetPassword.apply {
                                        dismiss()
                                    }
                                    successDialog(newResponse.data?.message.toString(), requireActivity())
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(newResponse.data?.message.toString(), requireActivity())
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    val oldPassword = resetPassword.findViewById<TextInputLayout>(R.id.tilOldPassword)
                                    val newPassword = resetPassword.findViewById<TextInputLayout>(R.id.tilNewPassword)
                                    val reNewPassword = resetPassword.findViewById<TextInputLayout>(R.id.tilRetypeNewPassword)

                                    if (newResponse.data?.old_password !=null){
                                        oldPassword.helperText = newResponse.data.old_password
                                        //email.boxStrokeColor = ContextCompat.getColor(this@MainActivity,R.color.red)
                                    }
                                    if (newResponse.data?.new_password !=null){
                                        newPassword.helperText = newResponse.data.new_password
                                        //password.boxStrokeColor = ContextCompat.getColor(this@MainActivity,R.color.red)
                                    }
                                    if (newResponse.data?.confirm_password !=null){
                                        reNewPassword.helperText = newResponse.data.confirm_password
                                        //password.boxStrokeColor = ContextCompat.getColor(this@MainActivity,R.color.red)
                                    }
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader!!.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader!!.hideLoader()
                        response.message?.let {
                            errorDialog(it, requireActivity())
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(context, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

}