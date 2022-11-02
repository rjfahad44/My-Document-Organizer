package com.ft.my_document_organizer.ui.activities

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Html
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.ft.my_document_organizer.DocApplication.Companion.sp
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.ui.adapter.RvCountryAdapter
import com.ft.my_document_organizer.databinding.ActivityMainBinding
import com.ft.my_document_organizer.utils.listeners.RvCountryItemClickListener
import com.ft.my_document_organizer.remainder.*
import com.ft.my_document_organizer.utils.AppUtils.saveInfo
import com.ft.my_document_organizer.ui.viewmodel.DocViewModel
import com.ft.my_document_organizer.utils.Constants.Companion.LOGIN_INFO
import com.ft.my_document_organizer.worker.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.Executor
import kotlin.collections.ArrayList
import androidx.activity.viewModels
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ft.my_document_organizer.fragment.label_fragment.LabelFragment
import com.ft.my_document_organizer.fragment.profile_fragment.ProfileFragment
import com.ft.my_document_organizer.fragment.home_fragment.HomeFragment
import com.ft.my_document_organizer.state.NetworkState
import com.ft.my_document_organizer.ui.activities.add_document.AddDocumentTextOnlyActivity
import com.ft.my_document_organizer.ui.activities.camera.CameraActivity
import com.ft.my_document_organizer.ui.activities.settings.SettingsActivity
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import com.ft.my_document_organizer.utils.*
import com.ft.my_document_organizer.utils.AppUtils.errorDialog
import com.ft.my_document_organizer.utils.AppUtils.successDialog
import com.ft.my_document_organizer.utils.Constants.Companion.COMMON_NOT_FOUND
import com.ft.my_document_organizer.utils.Constants.Companion.COMMON_UNPROCESSABLE_ENTITY
import com.ft.my_document_organizer.utils.Constants.Companion.EMAIL_VERIFICATION_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.FORGOT_PASSWORD_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.GOOGLE_SIGNING_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.LOGIN_FAILED
import com.ft.my_document_organizer.utils.Constants.Companion.LOGIN_FORBIDDEN
import com.ft.my_document_organizer.utils.Constants.Companion.LOGIN_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.OTP_SEND_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.OTP_VERIFY_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.REGISTRATION_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.RESET_PASSWORD_SUCCESS
import com.ft.my_document_organizer.utils.Constants.Companion.WORKER_NAME
import timber.log.Timber
import kotlin.collections.HashMap

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), RvCountryItemClickListener {
    private val TAG = "MainActivity"

    val data = Data.Builder()
    var bottomNavigationMenuItem: MenuItem? = null

    private val workManager by lazy { WorkManager.getInstance(this) }

    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

    private var googleSignInClient: GoogleSignInClient = AppUtils.googleSigningBuilder()

    private val viewModel: DocViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()

    private lateinit var loginDialog: Dialog
    private lateinit var registrationDialog: Dialog
    private lateinit var forgotPasswordDialog: Dialog
    private lateinit var sendPasswordDialog: Dialog
    private lateinit var setPasswordDialog: Dialog
    private lateinit var newPasswordDialog: Dialog
    private lateinit var checkEmailDialog: Dialog
    private lateinit var verifyEmailDialog: Dialog

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var countryName: TextView
    private lateinit var tvCountrySearch: TextView
    private lateinit var constrainLayoutForCountryReg: ConstraintLayout
    private lateinit var constrainLayoutForCountrySearch: ConstraintLayout
    private lateinit var name: EditText
    private lateinit var email: EditText

    val countryCode = Constants.CountryCode
    var loader: Loader? = null

    override fun onResume() {
        super.onResume()
        if (sp.getBoolean("isLogin", false)) {
            Timber.d("Login")
        } else {
            Timber.d("Log Out")
        }
    }

    override fun setBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    init {
        sp = saveInfo(LOGIN_INFO)
    }

    private fun initialize() {
        loader = Loader(this@MainActivity)
        //startDownloadWorker()
        //getAllGoogleAccount()
        //getPrimaryEmail()

        if (!sp.getBoolean("isLogin", false)) {
            loginDialog()
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment(), "finish").commit()

        binding.floatingActionButton.setOnClickListener {
            floatingActionButton.animate().rotationBy(135f)
            addDialog(binding.floatingActionButton)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, HomeFragment(), "finish").commit()
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, ProfileFragment(), "finish").commit()
                }
                R.id.menu_label -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, LabelFragment(), "finish").commit()
                }
                R.id.menu_menu -> {
                    bottomNavigationMenuItem = bottomNavigationView.menu.findItem(R.id.menu_menu)
                    bottomNavigationMenuItem!!.setIcon(R.drawable.ic_close)
                    menuDialog()
                }
            }
            true
        }

        setupLoginObservers()
        setupGoogleLoginObservers()
        setupRegistrationObservers()
        setupEmailVerificationObservers()
        setupResendOtpObservers()
        setupForgotPasswordObservers()
        setupOtpVerifyObservers()
        setupResetPasswordObservers()
    }

    private fun addDialog(floatingActionButton: FloatingActionButton) {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_add_document_pressed)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val location = IntArray(2)
        floatingActionButton.getLocationOnScreen(location)
        val wmlp = dialog.window!!.attributes
        wmlp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        wmlp.x = location[1] + 0
        wmlp.y = location[0] - 100

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        dialog.findViewById<RelativeLayout>(R.id.rlOnlyText).setOnClickListener {
            startActivity(Intent(this@MainActivity, AddDocumentTextOnlyActivity::class.java))
        }

        dialog.findViewById<RelativeLayout>(R.id.rlCamera).setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }

        dialog.setOnDismissListener {
            floatingActionButton.animate().rotationBy(-135f)
        }

        dialog.show()
    }

    private fun menuDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_menu)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        dialog.setOnDismissListener {
            if (bottomNavigationMenuItem != null) {
                bottomNavigationMenuItem!!.setIcon(R.drawable.ic_baseline_menu_24)
            }
        }

        dialog.findViewById<RelativeLayout>(R.id.rlvSettings).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
        dialog.findViewById<RelativeLayout>(R.id.rlvLogout).setOnClickListener {
            loader?.showLoader()
            Handler(Looper.getMainLooper()).postDelayed({
                loader?.hideLoader()
                if (AppUtils.isGoogleSigning() || sp.getBoolean("isLogin", false)) {
                    dialog.dismiss()
                    loginDialog()
                    AppUtils.googleSigningBuilder().signOut()
                    sp.edit().putBoolean("isLogin", false).apply()
                } else {
                    Toast.makeText(this, "Signing please", Toast.LENGTH_SHORT).show()
                }
            }, 800)
        }
        dialog.show()
    }

    private fun loginDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_login)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loginDialog = dialog

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.findViewById<ImageView>(R.id.ivCloseIcon).visibility = ViewGroup.INVISIBLE

        dialog.findViewById<TextView>(R.id.tvLoginBtn).setOnClickListener {
            val email = dialog.findViewById<TextInputEditText>(R.id.tietEmail)
            val password = dialog.findViewById<TextInputEditText>(R.id.tietPassword)

            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val userLoginData = HashMap<String, String>()
                userLoginData["username"] = email.text.toString()
                userLoginData["password"] = password.text.toString()

                userViewModel.login(userLoginData)
            }
        }

        dialog.findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            forgotPasswordDialog()
            dialog.hide()
        }

        dialog.findViewById<ImageView>(R.id.ivBiometricVerification).setOnClickListener {
            biometricsAuthentication()
            //dialog.dismiss()
        }
        dialog.findViewById<RelativeLayout>(R.id.rlGoogleLogin).setOnClickListener {
            signInGoogle()
            //dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.tvRegistrationBtn).setOnClickListener {
            registrationDialog()
            dialog.hide()
        }

        dialog.show()
    }

    private fun setupLoginObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.loginResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()
                            when (newResponse.http_code) {
                                LOGIN_SUCCESS -> {
                                    //success
                                    loginDialog.dismiss()
                                    successDialog(newResponse.data?.message.toString(), this@MainActivity)
                                    sp.edit()
                                        .putBoolean("isLogin", true)
                                        .putString("email", newResponse.data?.email)
                                        .putString("access_token", newResponse.data?.access_token)
                                        .apply()
                                }
                                LOGIN_FORBIDDEN -> {
                                    //Error: Forbidden
                                    verificationDialog(
                                        newResponse.data?.user_info?.email?: newResponse.data?.email,
                                        newResponse.access_token
                                    )
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                LOGIN_FAILED -> {
                                    //Not found
                                    val email =
                                        loginDialog.findViewById<TextInputLayout>(R.id.tilEmail)
                                    val password =
                                        loginDialog.findViewById<TextInputLayout>(R.id.tilPassword)
                                    if (newResponse.data?.email != null) {
                                        email.helperText = newResponse.data.email
                                        //email.boxStrokeColor = ContextCompat.getColor(this@MainActivity,R.color.red)
                                    }
                                    if (newResponse.data?.password != null) {
                                        password.helperText = newResponse.data.password
                                        //password.boxStrokeColor = ContextCompat.getColor(this@MainActivity,R.color.red)
                                    }
                                }

                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Not found
                                    val email =
                                        loginDialog.findViewById<TextInputLayout>(R.id.tilEmail)
                                    val password =
                                        loginDialog.findViewById<TextInputLayout>(R.id.tilPassword)

                                    email.helperText = newResponse.data?.email ?: ""
                                    password.helperText = newResponse.data?.password ?: ""
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun registrationDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_registration)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        registrationDialog = dialog

        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)

        val countryList = arrayListOf("Bangladesh", "India", "Nepal", "Bhutan", "Pakistan")
        val countryAdapter = RvCountryAdapter(this)
        val rv = dialog.findViewById<RecyclerView>(R.id.rvCountryList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = countryAdapter
        countryAdapter.update(countryList)

        this.name = dialog.findViewById(R.id.etFullName)
        this.email = dialog.findViewById(R.id.etEmail)
        this.countryName = dialog.findViewById(R.id.tvCountryName)
        this.tvCountrySearch = dialog.findViewById(R.id.tvCountry)
        this.constrainLayoutForCountrySearch = dialog.findViewById(R.id.clCountrySearch)
        this.constrainLayoutForCountryReg = dialog.findViewById(R.id.clReg)

        dialog.findViewById<ImageView>(R.id.ivCloseCountryList).setOnClickListener {
            this.constrainLayoutForCountrySearch.isVisible = false
            this.constrainLayoutForCountryReg.isVisible = true
        }
        dialog.findViewById<TextView>(R.id.tvCountryName).setOnClickListener {
            this.constrainLayoutForCountryReg.isVisible = false
            this.constrainLayoutForCountrySearch.isVisible = true

            dialog.findViewById<EditText>(R.id.etSearchLabel)
                .doOnTextChanged { text, start, before, count ->
                    filter(text, countryList, countryAdapter)
                }
        }

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener {
            loginDialog.show()
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.tvContinueBtn).setOnClickListener {
            if (this.name.text.toString().isEmpty() || this.email.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                setPasswordDialog(this.name.text.toString(), this.email.text.toString())
            }
            dialog.hide()
        }

        dialog.findViewById<TextView>(R.id.tvLoginBtn).setOnClickListener {
            loginDialog.show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun filter(
        text: CharSequence?,
        countryList: ArrayList<String>,
        countryAdapter: RvCountryAdapter
    ) {
        val newList: ArrayList<String> = ArrayList()
        for (item in countryList) {
            if (item.lowercase().contains(text!!)) {
                newList.add(item)
            }
        }
        countryAdapter.update(newList)
    }


    private fun setPasswordDialog(name: String, email: String) {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_set_password)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setPasswordDialog = dialog
        dialog.setCancelable(false)

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener {
            registrationDialog.show()
            dialog.dismiss()
        }

        val password = dialog.findViewById<TextInputEditText>(R.id.tietPassword)
        val retypePassword = dialog.findViewById<TextInputEditText>(R.id.tietRetypePassword)

        dialog.findViewById<TextView>(R.id.tvContinueBtn).setOnClickListener {

            if (password.text.toString() == retypePassword.text.toString() && (password.text.toString()
                    .isNotEmpty() || retypePassword.text.toString().isNotEmpty())
            ) {

                val userRegistrationInfoMap = HashMap<String, String>()
                userRegistrationInfoMap["name"] = name
                userRegistrationInfoMap["email"] = email
                userRegistrationInfoMap["password"] = password.text.toString()
                userRegistrationInfoMap["confirm_password"] = retypePassword.text.toString()

                userViewModel.registration(userRegistrationInfoMap)
            } else {
                Toast.makeText(this@MainActivity, "Password is mismatch", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<TextView>(R.id.tvLoginHere).setOnClickListener {
            registrationDialog.dismiss()
            loginDialog.show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupRegistrationObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.registrationResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()

                            when (newResponse.http_code) {
                                REGISTRATION_SUCCESS -> {
                                    //success
                                    setPasswordDialog.dismiss()
                                    registrationDialog.dismiss()
                                    loginDialog.show()
                                    verificationDialog(
                                        newResponse.data?.userInfo?.email,
                                        newResponse.data?.accessToken
                                    )
                                    sp.edit()
                                        .putString("email", newResponse.data?.userInfo?.email)
                                        .putString("access_token", newResponse.data?.accessToken)
                                        .apply()
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun verificationDialog(email: String?, accessToken: String?) {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_verification)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        verifyEmailDialog = dialog

        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(false)

        val etOtp = dialog.findViewById<EditText>(R.id.etVerificationCode)

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener {
            dialog.dismiss()
        }
        val subTitle =
            "<font color=#8688A8>Enter the code we've send on your\nemail</font> <font color=#42435A>$email</font>"
        dialog.findViewById<TextView>(R.id.tvSubTitle).text = Html.fromHtml(subTitle)

        dialog.findViewById<TextView>(R.id.tvSubmitBtn).setOnClickListener {
            if (etOtp.text.toString().isNotEmpty()) {
                val map = HashMap<String, String>()
                map["code"] = etOtp.text.toString()
                val token = "Bearer $accessToken"

                userViewModel.emailVerification(map, token)
            } else {
                Toast.makeText(this@MainActivity, "Field not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<TextView>(R.id.tvReSendBtn).setOnClickListener {
            resendOTP(email)
        }
        dialog.show()
    }

    private fun setupEmailVerificationObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.emailVerificationResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()
                            when (newResponse.http_code) {
                                EMAIL_VERIFICATION_SUCCESS -> {
                                    //Successful Response
                                    verifyEmailDialog.dismiss()
                                    successDialog(newResponse.data?.message.toString(),this@MainActivity)
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun resendOTP(email: String?) {
        val resendOTPMap = HashMap<String, String>()
        resendOTPMap["email"] = "$email"
        userViewModel.reSendOTP(resendOTPMap)
    }

    private fun setupResendOtpObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.resendOtpResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()

                            when (newResponse.http_code) {
                                OTP_SEND_SUCCESS -> {
                                    //Successful Response
                                    Toast.makeText(
                                        this@MainActivity,
                                        "send OTP Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sp.edit()
                                        .putString("access_token", newResponse.data?.access_token)
                                        .apply()
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun forgotPasswordDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        forgotPasswordDialog = dialog
        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(false)

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener {
            loginDialog.show()
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.tvSendCodeBtn).setOnClickListener {
            val resetPasswordEmail =
                dialog.findViewById<EditText>(R.id.etEmailForResetPassword).text.toString()
            if (resetPasswordEmail.isNotEmpty()) {
                val forgotPasswordData = HashMap<String, String>()
                forgotPasswordData["email"] = resetPasswordEmail

                userViewModel.forgotPassword(forgotPasswordData)
            } else {
                Toast.makeText(this@MainActivity, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    private fun setupForgotPasswordObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.forgotPasswordResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()

                            when (newResponse.http_code) {
                                FORGOT_PASSWORD_SUCCESS -> {
                                    //Successful Response
                                    forgotPasswordDialog.dismiss()
                                    checkEmailDialog()
                                    sp.edit()
                                        .putString("email", newResponse.data?.email)
                                        .putString("access_token", newResponse.data?.access_token)
                                        .apply()
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun checkEmailDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_check_email)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        checkEmailDialog = dialog

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tvContinueBtn).setOnClickListener {
            val otp = dialog.findViewById<EditText>(R.id.etVerifyEmailCode).text.toString()
            if (otp.isNotEmpty()) {
                val otpVerifyMap = HashMap<String, String>()
                otpVerifyMap["code"] = otp
                val token = "Bearer ${sp.getString("access_token", null)}"

                userViewModel.otpVerify(otpVerifyMap, token)
            } else {
                Toast.makeText(this@MainActivity, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        dialog.findViewById<TextView>(R.id.tvReSendOtp).setOnClickListener {
            Toast.makeText(this@MainActivity, "resend otp", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }

    private fun setupOtpVerifyObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.otpVerifyResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()
                            when (newResponse.http_code) {
                                OTP_VERIFY_SUCCESS -> {
                                    //Successful Response
                                    checkEmailDialog.dismiss()
                                    createNewPasswordDialog()
                                    sp.edit()
                                        .putString("access_token", newResponse.data?.access_token)
                                        .apply()
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun createNewPasswordDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_create_new_password)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        newPasswordDialog = dialog
        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(false)

        dialog.findViewById<ImageView>(R.id.ivCloseIcon).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.tvResetPassword).setOnClickListener {
            val password = dialog.findViewById<TextInputEditText>(R.id.tietPassword).text.toString()
            val rePassword =
                dialog.findViewById<TextInputEditText>(R.id.tietRetypePassword).text.toString()

            if (password.isNotEmpty() || rePassword.isNotEmpty()) {
                val resetPasswordMap = HashMap<String, String>()
                resetPasswordMap["new_password"] = password
                resetPasswordMap["confirm_password"] = rePassword
                val token = "Bearer ${sp.getString("access_token", null)}"

                userViewModel.resetPassword(resetPasswordMap, token)
            } else {
                Toast.makeText(this@MainActivity, "Field cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    private fun setupResetPasswordObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.resetPasswordResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()

                            when (newResponse.http_code) {
                                RESET_PASSWORD_SUCCESS -> {
                                    //Successful Response
                                    newPasswordDialog.dismiss()
                                    successDialog(newResponse.data?.message.toString(),this@MainActivity)
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data?.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                    if (account != null) {
                        account.idToken?.let { gToken ->
                            Timber.d("Google IdToken : $gToken")
                            val googleLoginData = HashMap<String, String>()
                            googleLoginData["google_token"] = gToken

                            //setupGoogleLoginObservers
                            userViewModel.googleLogin(googleLoginData)
                        }
                    }
                } catch (e: ApiException) {
                    Timber.d(e.message)
                }
            }
        }

    private fun setupGoogleLoginObservers() {
        lifecycleScope.launchWhenStarted {
            userViewModel.googleLoginResponse.collect { response ->
                when (response) {
                    is NetworkState.Success -> {
                        loader?.hideLoader()
                        response.data?.let { newResponse ->
                            EspressoIdlingResource.decrement()
                            when (newResponse.http_code) {
                                GOOGLE_SIGNING_SUCCESS -> {
                                    //Successful Response
                                    loginDialog.dismiss()
                                    successDialog(newResponse.data.message.toString(),this@MainActivity)
                                    sp.edit()
                                        .putBoolean("isLogin", true)
                                        .putString("access_token", newResponse.data.access_token)
                                        .apply()
                                }
                                COMMON_NOT_FOUND -> {
                                    //Not found
                                    errorDialog(
                                        newResponse.data.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                COMMON_UNPROCESSABLE_ENTITY -> {
                                    //Validation Error
                                    errorDialog(
                                        newResponse.data.message.toString(),
                                        this@MainActivity
                                    )
                                }
                                else -> {}
                            }
                        }
                    }

                    is NetworkState.Loading -> {
                        loader?.showLoader()
                    }

                    is NetworkState.Error -> {
                        loader?.hideLoader()
                        response.message?.let {
                            errorDialog(it, this@MainActivity)
                        }
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            userViewModel.errorMessage.collect { value ->
                if (value.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
                }
                userViewModel.hideErrorToast()
            }
        }
    }

    private fun biometricsAuthentication() {
        executor = ContextCompat.getMainExecutor(this)
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricsAuthUser(executor)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                //binding.textVM.text = "Biometric authentication not supported."
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Timber.i(TAG, "BIOMETRIC_STATUS_UNKNOWN")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Timber.i(TAG, "BIOMETRIC_ERROR_HW_UNAVAILABLE")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Timber.i(TAG, "BIOMETRIC_ERROR_NONE_ENROLLED")
                //binding.textVM.text = "Biometric authentication not enable, please set biometrics authentication."

                val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_WEAK or DEVICE_CREDENTIAL
                        )
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                } else {
                    Intent(Settings.ACTION_SECURITY_SETTINGS)
                }
                startActivityForResult(enrollIntent, 11)
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Timber.i(TAG, "BIOMETRIC_ERROR_UNSUPPORTED")
            }
            else -> {
                Timber.i(TAG, "FAILED!!")
            }
        }
    }

    private fun biometricsAuthUser(executor: Executor) {
        biometricPrompt = BiometricPrompt(this@MainActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Timber.i(TAG, "Success ${result.authenticationType}")
                }

                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Timber.i(TAG, "Error : $errorCode  $errString")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Timber.i(TAG, "onAuthenticationFailed!!")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(getString(R.string.auth_title))
            setSubtitle(getString(R.string.auth_subtitle))
            setDescription(getString(R.string.auth_description))
            setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            setConfirmationRequired(false)
        }.build()
        //when only one fingerprint or face lock showing without pattern or pin or password//
        //setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
        //use BIOMETRIC_STRONG
        //setNegativeButtonText("Cancel")
        biometricPrompt.authenticate(promptInfo)
    }

    @SuppressLint("NewApi")
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, NotificationRemainder::class.java)
        val title = "Notification Alarm"
        val message = "Demo Notification Message"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        intent.putExtra(_ID, notificationID)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            else
                PendingIntent.FLAG_ONE_SHOT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
        notificationID++
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) +
                        ", " + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    @SuppressLint("NewApi")
    private fun getTime(): Long {
        val minute = 59
        val hour = 11
        val day = 17
        val month = 7
        val year = 2022

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @SuppressLint("NewApi", "WrongConstant")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Alarm"
            val description = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = description

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startDownloadWorker() {
        data.apply {
            putString(WORKER_NAME, "syncStart")
        }

        val constaints: Constraints = Constraints.Builder()
            //.setRequiresCharging(true)
            //.setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // use for one time tasks
        val syncStart = OneTimeWorkRequestBuilder<SyncStartWorker>()
            .setConstraints(constaints)
            .setInputData(data.build())
            .build()

        val syncUploadData = OneTimeWorkRequestBuilder<SyncUploadDataWorker>()
            .setConstraints(constaints)
            .build()

        val syncUploadFiles = OneTimeWorkRequestBuilder<SyncUploadFilesWorker>()
            .setConstraints(constaints)
            .build()

        val syncUploadCompleted = OneTimeWorkRequestBuilder<SyncUploadCompletedWorker>()
            .setConstraints(constaints)
            .build()

        val syncDownloadData = OneTimeWorkRequestBuilder<SyncDownloadDataWorker>()
            .setConstraints(constaints)
            .build()

        val syncStop = OneTimeWorkRequestBuilder<SyncStopWorker>()
            .setConstraints(constaints)
            .build()


        workManager
            .beginWith(syncStart)
            .then(syncUploadData)
            .then(syncUploadFiles)
            .then(syncUploadCompleted)
            .then(syncDownloadData)
            .then(syncStop)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(syncStart.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            Timber.i(TAG, "Task ${WorkInfo.State.values()} is Complete ")
                            //success(it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI) ?: "")
                        }
                        WorkInfo.State.FAILED -> {
                            //failed("Downloading failed!")
                        }
                        WorkInfo.State.BLOCKED -> {
                            //block("Downloading block!")
                        }
                        WorkInfo.State.ENQUEUED -> {
                            //enqueued("Downloading enqueued!")
                        }
                        WorkInfo.State.CANCELLED -> {
                            //canceled("Downloading canceled!")
                        }
                        WorkInfo.State.RUNNING -> {
                            //Log.i(TAG, "Task ${WorkInfo.State.values()} is running ")
                        }
                        else -> {
                            //failed("Something went wrong")
                        }
                    }
                }
            }
    }

    override fun itemClickListener(country: String) {
        this.constrainLayoutForCountrySearch.isVisible = false
        this.constrainLayoutForCountryReg.isVisible = true
        this.countryName.text = country
        this.tvCountrySearch.text = country
        this.countryName.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        this.tvCountrySearch.setTextColor(ContextCompat.getColor(this, R.color.text_color))
    }
}
