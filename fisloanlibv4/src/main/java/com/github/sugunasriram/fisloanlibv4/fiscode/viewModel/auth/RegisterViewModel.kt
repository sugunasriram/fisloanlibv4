package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AddressModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Data
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.DocumentUpload
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Logout
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PanVerificationResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PincodeModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ProfileFocusRequester
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Signup
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateProfile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterViewModel : ViewModel() {

    private val _showInternetScreen = MutableLiveData(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unexpectedError = MutableLiveData(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _unAuthorizedUser = MutableLiveData(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }


    private val _showErrorMsg = MutableLiveData(false)
    val showErrorMsg: LiveData<Boolean>  = _showErrorMsg

    fun onSetError(value: Boolean) {
        _showErrorMsg.value=value
    }


    private val _errorMsg: MutableLiveData<String?> = MutableLiveData("")
    val errorMsg: LiveData<String?> = _errorMsg

    private val _isAddress1Validated = MutableLiveData(true)
    val isAddress1Validated: LiveData<Boolean>  = _isAddress1Validated

    private val _isAddress2Validated = MutableLiveData(true)
    val isAddress2Validated: LiveData<Boolean>  = _isAddress2Validated

    private val _firstName: MutableLiveData<String?> = MutableLiveData("")
    val firstName: LiveData<String?> = _firstName

    private val _firstNameError: MutableLiveData<String?> = MutableLiveData("")
    val firstNameError: LiveData<String?> = _firstNameError

    fun onFirstNameChanged(value: String, context: Context) {
//        val sanitizedInput = value.replace(Regex("[^a-zA-Z ]"), "")
//        _firstName.value = sanitizedInput.take(30)
        _firstName.value = value
        if (value.length < 4) {
            _firstNameError.value = context.getString(R.string.first_name_should_contain_minimum_4_letters)
        } else if (value.length >= 30) {
            _firstNameError.value = context.getString(R.string.first_name_not_exceeded_30_characters)
        }else {
            _firstNameError.value = null
        }
    }

    private val _showDocumentNotUploadedError = MutableStateFlow(false)
    val showDocumentNotUploadedError: StateFlow<Boolean> = _showDocumentNotUploadedError

    private val _documentUploading = MutableStateFlow(false)
    val documentUploading: StateFlow<Boolean> = _documentUploading

    private val _documentUploaded = MutableStateFlow(false)
    val documentUploaded: StateFlow<Boolean> = _documentUploaded

    private val _documentUploadResponse = MutableStateFlow<DocumentUpload?>(null)
    val documentUploadResponse: StateFlow<DocumentUpload?> = _documentUploadResponse


    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    private val _lastNameError = MutableLiveData<String?>(null)
    val lastNameError: LiveData<String?> = _lastNameError

    fun onLastNameChanged(value: String,context: Context) {
//        val sanitizedInput = value.replace(Regex("[^a-zA-Z ]"), "").take(30)
        _lastName.value = value
        if (value.length >= 30) {
            _lastNameError.value = context.getString(R.string.last_name_not_exceeded_30_characters)
        } else {
            _lastNameError.value = null
        }
    }

    var count = 0

    private val _personalEmailId: MutableLiveData<String?> = MutableLiveData(null)
    val personalEmailId: LiveData<String?> = _personalEmailId
    private val _personalEmailIdError: MutableLiveData<String?> = MutableLiveData(null)
    val personalEmailIdError: LiveData<String?> = _personalEmailIdError

    fun onPersonalEmailIdChanged(value: String,context: Context) {
        _personalEmailId.value = value
        if(!CommonMethods().isValidEmail(value)){
            _personalEmailIdError.value = context.getString(R.string.enter_valid_personal_email_id)
        } else if(value == _officialEmailId.value ) {
            _personalEmailIdError.value = context.getString(R.string.personal_mail_id_should_not_be_same)
        } else{
            _personalEmailIdError.value = null
        }
    }

    private val _officialEmailId: MutableLiveData<String?> = MutableLiveData(null)
    val officialEmailId: LiveData<String?> = _officialEmailId
    private val _officialEmailIdError: MutableLiveData<String?> = MutableLiveData(null)
    val officialEmailIdError: LiveData<String?> = _officialEmailIdError

    fun onOfficialEmailIdChanged(value: String,context: Context) {
        _officialEmailId.value = value
        if(!_employeeType.value.equals("Salaried",ignoreCase = true)){
            _officialEmailIdError.value = null
        }
        if(!CommonMethods().isValidEmail(value)){
            _officialEmailIdError.value = context.getString(R.string.enter_valid__official_email_id)
        } else if(value == _personalEmailId.value ) {
            _officialEmailIdError.value = context.getString(R.string.official_mail_id_should_not_be_same)
        }
        else {
            _officialEmailIdError.value = null
        }
    }
    fun clearOfficialEmail() {
//        _officialEmailId.value = null
        _officialEmailIdError.value = null
        _companyNameError.value=null
    }

    private val _phoneNumber: MutableLiveData<String?> = MutableLiveData(null)
    val phoneNumber: LiveData<String?> = _phoneNumber
    private val _phoneNumberError: MutableLiveData<String?> = MutableLiveData(null)
    val phoneNumberError: LiveData<String?> = _phoneNumberError

    fun onPhoneNumberChanged(value: String,context: Context) {
        _phoneNumber.value = value
        if(value.length <=10){
            _phoneNumberError.value = context.getString(R.string.enter_valid_phone_number)
        } else {
            _phoneNumberError.value = null
        }
    }

    private val _dob: MutableLiveData<String?> = MutableLiveData(null)
    val dob: LiveData<String?> = _dob
    private val _dobError: MutableLiveData<String?> = MutableLiveData(null)
    val dobError: LiveData<String?> = _dobError

    fun onDobChanged(value: String, context: Context) {
        _dob.value = value.take(10)
        val pattern = Regex("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$")

        if (!pattern.matches(value)) {
            _dobError.value = context.getString(R.string.please_enter_date_format)
            return
        }
        if (!isValidAdultDob(value)) {
            _dobError.value = context.getString(R.string.you_must_be_18_years)
        } else {
            _dobError.value = null
        }
    }
    private fun isValidAdultDob(dobString: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val dobDate = dateFormat.parse(dobString) ?: return false

            val calendarDob = Calendar.getInstance().apply { time = dobDate }

            val calendar18YearsAgo = Calendar.getInstance().apply {
                add(Calendar.YEAR, -18)
                add(Calendar.DAY_OF_YEAR, -1) // Must be before this
            }

            calendarDob.before(calendar18YearsAgo)
        } catch (e: Exception) {
            false
        }
    }

    private val _gender: MutableLiveData<String?> = MutableLiveData(null)
    val gender: LiveData<String?> = _gender
    private val _genderError: MutableLiveData<String?> = MutableLiveData(null)
    val genderError: LiveData<String?> = _genderError

    fun onGenderChanged(value: String,context: Context) {
        _gender.value = value
        _genderError.value=null
    }

    private val _panNumber: MutableLiveData<String?> = MutableLiveData(null)
    val panNumber: LiveData<String?> = _panNumber
    private val _panError: MutableLiveData<String?> = MutableLiveData(null)
    val panError: LiveData<String?> = _panError

    private val _panApiThrewError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val panApiThrewError: StateFlow<Boolean> = _panApiThrewError

    fun onPanNumberChanged(value: String,context: Context,navController: NavHostController) {
//        val sanitizedInput = value.replace(Regex("[^a-zA-Z0-9]"), "").take(10)
        _panNumber.value = value
        if(CommonMethods().isValidPanNumber(value) != true){
            _panError.value = context.getString(R.string.enter_valid_pan_number)
        } else {
//            getUserNameFormPan(value,navController)
            _panError.value = null
        }
    }

    private fun getUserNameFormPan(
        pan: String,
        navController: NavHostController,
        checkForAccessToken: Boolean = false
    ) {
        viewModelScope.launch {
            runCatching {
                ApiRepository.getUserNameFromPan(pan)
            }.onSuccess {
                handleGetUserNameFormPanSuccess(it)
                count++
            }.onFailure { error ->
                if (error is ResponseException &&
                    error.response.status.value == 401
                ) {
                    //Get Access Token using RefreshToken
                    if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                        getUserNameFormPan(pan, navController, false)
                    } else {
                        // If unable to refresh the token, navigate to the sign-in page
                        withContext(Dispatchers.Main) {
                            navigateSignInPage(navController)
                        }
                    }
                } else {
                    handleGetUserNameFormPanFailure()
                }
            }
        }
    }

    private fun handleGetUserNameFormPanFailure() {
        _panApiThrewError.value = true
    }

    private fun handleGetUserNameFormPanSuccess(panVerificationResponse: PanVerificationResponse?) {
      panVerificationResponse?.let {
          _firstName.value =  it.data.first_name
          _lastName.value = it.data.last_name
          _panApiThrewError.value = false
      }
    }

    private val _employeeType: MutableLiveData<String?> = MutableLiveData(null)
    val employeeType: LiveData<String?> = _employeeType
    private val _employeeTypeError: MutableLiveData<String?> = MutableLiveData(null)
    val employeeTypeError: LiveData<String?> = _employeeTypeError

    fun onEmployeeTypeChanged(value: String,context: Context) {
        _employeeType.value = value
        _employeeTypeError.value = null
    }

    private val _companyName: MutableLiveData<String?> = MutableLiveData(null)
    val companyName: LiveData<String?> = _companyName
    private val _companyNameError: MutableLiveData<String?> = MutableLiveData(null)
    val companyNameError: LiveData<String?> = _companyNameError

    fun onCompanyNameChanged(value: String) {
//        val sanitizedInput = value.replace(Regex("[^a-zA-Z0-9 ./&(),]"), "").take(50)
        _companyName.value = value
        if (value.length <= 50) {
            _companyNameError.value = null
        }
    }

    private val _udyamNumber: MutableLiveData<String?> = MutableLiveData(null)
    val udyamNumber: LiveData<String?> = _udyamNumber
    private val _udyamNumberError: MutableLiveData<String?> = MutableLiveData(null)
    val udyamNumberError: LiveData<String?> = _udyamNumberError

fun onUdyamNumberChanged(value: String, context: Context) {
    if (value.isEmpty()) {
        _udyamNumber.value = null
        _udyamNumberError.value = null
    } else {
//        val sanitizedInput = value.replace(Regex("[^a-zA-Z0-9-]"), "").take(19)
        _udyamNumber.value = value
        if (CommonMethods().isValidUdyamNumber(value)!= true) {
            _udyamNumberError.value = context.getString(R.string.enter_valid_udyam_number)
        } else {
            _udyamNumberError.value = null
        }
    }
}

    private val _officialAddressField: MutableLiveData<AddressModel?> = MutableLiveData(null)
    val officialAddressField: LiveData<AddressModel?> = _officialAddressField

    private val _officialAddress: MutableLiveData<String?> = MutableLiveData(null)
    val officialAddress: LiveData<String?> = _officialAddress
    private val _officialAddressError: MutableLiveData<String?> = MutableLiveData(null)
    val officialAddressError: LiveData<String?> = _officialAddressError

    fun onOfficialAddressChanged(address: String) {
        _officialAddress.value = listOf(_area1.value,_town1.value, _city1.value, _state1.value, _pinCode1.value)
            .filterNot { it.isNullOrEmpty() }
            .joinToString(", ")
        _officialAddressError.value=null
    }

    private val _pinCode1: MutableLiveData<String?> = MutableLiveData(null)
    val pinCode1: LiveData<String?> = _pinCode1
    private val _pinCode1Error: MutableLiveData<String?> = MutableLiveData(null)
    val pinCode1Error: LiveData<String?> = _pinCode1Error

    fun onPinCode1Changed(value: String, context: Context) {
        val sanitizedValue = value.take(6).filter { it.isDigit() } // Only take first 6 digits
        _pinCode1.value = sanitizedValue

        if (sanitizedValue.length < 6) {
            _pinCode1Error.value = context.getString(R.string.enter_valid_pincode)
            _city1.value = ""
            _state1.value=""
            _isAddress1Validated.value=false
        } else {
            getCity1(sanitizedValue, context)
            _pinCode1Error.value = null
            _isAddress1Validated.value=true
        }
    }

    private val _area1: MutableLiveData<String?> = MutableLiveData(null)
    val area1: LiveData<String?> = _area1
    private val _area1Error: MutableLiveData<String?> = MutableLiveData(null)
    val area1Error: LiveData<String?> = _area1Error

    fun onArea1Changed(value: String, context: Context) {
        val allowedSpecialChars = ",.-/#'&"
        val sanitizedValue = value.filter {
            it.isLetterOrDigit() && it.code < 128 || it.isWhitespace() || it in allowedSpecialChars
        }.take(100)

        _area1.value = sanitizedValue

        val hasInvalidChars = value.any {
            !(it.isLetterOrDigit() && it.code < 128) && !it.isWhitespace() && it !in allowedSpecialChars
        }

        when {
            sanitizedValue.length > 100 -> {
                _area1Error.value = context.getString(R.string.max_100_characters_allowed)
                _isAddress1Validated.value = false
            }
            sanitizedValue.length < 10 -> {
                _area1Error.value = context.getString(R.string.please_enter_min_10_characters)
                _isAddress1Validated.value = false
            }
            sanitizedValue.trim().all { it.isDigit() } -> {
                _area1Error.value = context.getString(R.string.please_enter_valid_address)
                _isAddress1Validated.value = false
            }
            hasInvalidChars -> {
                _area1Error.value = context.getString(R.string.special_characters_not_allowed)
                _isAddress1Validated.value = false
            }
            else -> {
                _area1Error.value = null
                _isAddress1Validated.value = true
            }
        }
    }

    private val _town1: MutableLiveData<String?> = MutableLiveData(null)
    val town1: LiveData<String?> = _town1
    private val _town1Error: MutableLiveData<String?> = MutableLiveData(null)
    val town1Error: LiveData<String?> = _town1Error

    fun onTown1Changed(value: String, context: Context) {
        val allowedSpecialChars = ",.-/#'&"

        // Only allow ASCII letters, digits, whitespace, and allowed special characters
        val sanitizedValue = value.filter {
            (it.isLetterOrDigit() && it.code < 128) || it.isWhitespace() || it in allowedSpecialChars
        }.take(100)

        _town1.value = sanitizedValue

        val hasInvalidChars = value.any {
            !(it.isLetterOrDigit() && it.code < 128) && !it.isWhitespace() && it !in allowedSpecialChars
        }

        when {
            sanitizedValue.length > 100 -> {
                _town1Error.value = context.getString(R.string.max_100_characters_allowed)
                _isAddress1Validated.value = false
            }
            sanitizedValue.length < 3 -> {
                _town1Error.value = context.getString(R.string.please_enter_min_3_characters)
                _isAddress1Validated.value = false
            }
            sanitizedValue.trim().all { it.isDigit() } -> {
                _town1Error.value = context.getString(R.string.please_enter_valid_address)
                _isAddress1Validated.value = false
            }
            hasInvalidChars -> {
                _town1Error.value = context.getString(R.string.special_characters_not_allowed)
                _isAddress1Validated.value = false
            }
            else -> {
                _town1Error.value = null
                _isAddress1Validated.value = true
            }
        }
    }

    fun documentUpload(documentUploadBody: ImageUploadBody, navController : NavHostController, context: Context) {
        _documentUploading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleDocumentUpload(documentUploadBody, navController, context)
        }
    }
    fun clearDocumentUploadError() {
        _showDocumentNotUploadedError.value = false
    }

    private suspend fun handleDocumentUpload(
        documentUploadBody: ImageUploadBody,
        navController: NavHostController,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.documentUpload(documentUploadBody)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _documentUploading.value = false
                _documentUploaded.value = true
                _documentUploadResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleDocumentUpload(
                        documentUploadBody = documentUploadBody,
                        context = context,
                        navController = navController,
                        checkForAccessToken = false
                    )
                } else {
                    withContext(Dispatchers.Main) {
                        navigateSignInPage(navController)
                    }
                }
            } else {
                handleFailure(context = context, error = error)
            }
        }
    }



    private val _city1: MutableLiveData<String?> = MutableLiveData(null)
    val city1: LiveData<String?> = _city1
    private val _city1Error: MutableLiveData<String?> = MutableLiveData(null)
    val city1Error: LiveData<String?> = _city1Error

    fun onCity1Changed(value: String,context: Context) {
        _city1.value = value
        _city1Error.value=null
        _isAddress1Validated.value=true
    }

    private val _state1: MutableLiveData<String?> = MutableLiveData(null)
    val state1: LiveData<String?> = _state1
    private val _state1Error: MutableLiveData<String?> = MutableLiveData(null)
    val state1Error: LiveData<String?> = _state1Error

    fun onState1Changed(value: String,context: Context) {
        _state1.value = value
        _state1Error.value=null
        _isAddress1Validated.value=true
    }

//    private val _permanentAddressField: MutableLiveData<String?> = MutableLiveData(null)
//    val permanentAddressField: LiveData<String?> = _permanentAddressField

    private val _permanentAddressField: MutableLiveData<AddressModel?> = MutableLiveData(null)
    val permanentAddressField: LiveData<AddressModel?> = _permanentAddressField

    private val _permanentAddress: MutableLiveData<String?> = MutableLiveData(null)
    val permanentAddress: LiveData<String?> = _permanentAddress
    private val _permanentAddressError: MutableLiveData<String?> = MutableLiveData(null)
    val permanentAddressError: LiveData<String?> = _permanentAddressError

    fun onPermanentAddressChanged(address: String) {
        _permanentAddress.value = listOf(_area2.value,_town2.value, _city2.value, _state2.value, _pinCode2.value)
            .filterNot { it.isNullOrEmpty() }
            .joinToString(", ")
        _permanentAddressError.value=null
        _isAddress2Validated.value=true
    }

    private val _pinCode2: MutableLiveData<String?> = MutableLiveData(null)
    val pinCode2: LiveData<String?> = _pinCode2
    private val _pinCode2Error: MutableLiveData<String?> = MutableLiveData(null)
    val pinCode2Error: LiveData<String?> = _pinCode2Error

    fun onPinCode2Changed(value: String,context: Context) {
        val sanitizedValue = value.take(6).filter { it.isDigit() } // Only take first 6 digits
        _pinCode2.value = sanitizedValue

        if (sanitizedValue.length < 6) {
            _pinCode2Error.value = context.getString(R.string.enter_valid_pincode)
            _city2.value = ""
            _state2.value=""
            _isAddress2Validated.value=false
        } else {
            getCity2(sanitizedValue, context)
            _pinCode2Error.value = null
            _isAddress2Validated.value=true
        }
    }

    private val _area2: MutableLiveData<String?> = MutableLiveData(null)
    val area2: LiveData<String?> = _area2
    private val _area2Error: MutableLiveData<String?> = MutableLiveData(null)
    val area2Error: LiveData<String?> = _area2Error

    fun onArea2Changed(value: String,context: Context) {
        _area2.value = value.take(100)
        val allowedSpecialChars = ",.-/#'&"
        val hasInvalidChars = value.any {
            !it.isLetterOrDigit() && !it.isWhitespace() && it !in allowedSpecialChars
        }
        if (value.length > 100) {
            _area2Error.value = context.getString(R.string.max_100_characters_allowed)
            _isAddress2Validated.value=false
            return
        }
        if (value.length < 10) {
            _area2Error.value = context.getString(R.string.please_enter_min_10_characters)
            _isAddress2Validated.value=false
            return
        }

        if (value.trim().all { it.isDigit() }) {
            _area2Error.value = context.getString(R.string.please_enter_valid_address)
            _isAddress2Validated.value = false
            return
        }

        if (hasInvalidChars) {
            _area2Error.value = context.getString(R.string.special_characters_not_allowed)
            _isAddress2Validated.value=false
//                "Only letters, numbers, spaces, and , . - / # ' are allowed."
        } else {
            _area2Error.value = null
            _isAddress2Validated.value=true
        }
    }

    private val _city2: MutableLiveData<String?> = MutableLiveData(null)
    val city2: LiveData<String?> = _city2
    private val _city2Error: MutableLiveData<String?> = MutableLiveData(null)
    val city2Error: LiveData<String?> = _city2Error

    fun onCity2Changed(value: String,context: Context) {
        _city2.value = value
        _city2Error.value=null
        _isAddress2Validated.value=true
    }

    private val _town2: MutableLiveData<String?> = MutableLiveData(null)
    val town2: LiveData<String?> = _town2
    private val _town2Error: MutableLiveData<String?> = MutableLiveData(null)
    val town2Error: LiveData<String?> = _town2Error

    fun onTown2Changed(value: String,context: Context) {
        _town2.value = value.take(100)
        val allowedSpecialChars = ",.-/#'&"
        val hasInvalidChars = value.any {
            !it.isLetterOrDigit() && !it.isWhitespace() && it !in allowedSpecialChars
        }
        if (value.length > 100) {
            _town2Error.value = context.getString(R.string.max_100_characters_allowed)
            _isAddress2Validated.value=false
            return
        }
        if (value.length < 3) {
            _town2Error.value = context.getString(R.string.please_enter_min_3_characters)
            _isAddress2Validated.value=false
            return
        }

        if (value.trim().all { it.isDigit() }) {
            _town2Error.value = context.getString(R.string.please_enter_valid_address)
            _isAddress2Validated.value = false
            return
        }
        if (hasInvalidChars) {
            _town2Error.value = context.getString(R.string.special_characters_not_allowed)
            _isAddress2Validated.value=false
//                "Only letters, numbers, spaces, and , . - / # ' are allowed."
        } else {
            _town2Error.value = null
            _isAddress2Validated.value=true
        }
    }

    private val _state2: MutableLiveData<String?> = MutableLiveData(null)
    val state2: LiveData<String?> = _state2
    private val _state2Error: MutableLiveData<String?> = MutableLiveData(null)
    val state2Error: LiveData<String?> = _state2Error

    fun onState2Changed(value: String,context: Context) {
        _state2.value = value
        _state2Error.value=null
        _isAddress2Validated.value=true
    }
    private val _income: MutableLiveData<String?> = MutableLiveData("")
    val income: LiveData<String?> = _income

    @OptIn(ExperimentalMaterialApi::class)
    fun address1Validation(
        scope: CoroutineScope, bottomSheetStateValue: ModalBottomSheetState, pinCode: String,
        area: String, town: String, city: String,state:String, context: Context,
        areaFocus:FocusRequester,townFocus:FocusRequester,pincodeFocus:FocusRequester
    ) {
        var isAddress1Valid=true
        if (town.isEmpty()) {
            _town1Error.value=context.getString(R.string.enter_valid_town)
            townFocus.requestFocus()
            isAddress1Valid=false
        }
        if (pinCode.isEmpty()) {
            _pinCode1Error.value=context.getString(R.string.enter_valid_pincode)
            pincodeFocus.requestFocus()
            isAddress1Valid=false
        }
        if (area.isEmpty()) {
            _area1Error.value=context.getString(R.string.enter_valid_area)
            areaFocus.requestFocus()
            isAddress1Valid=false
        }
        if(isAddress1Valid && _isAddress1Validated.value==true){
            val fullAddress= AddressModel(area, town, city, state,pinCode)
            _officialAddressField.value = fullAddress
            scope.launch { bottomSheetStateValue.hide() }
            _officialAddressError.value=null
        }
    }

    fun clearAddress() {
        _area1.value=null
        _town1.value=null
        _city1.value=null
        _state1.value =null
        _pinCode1.value=null

        _area1Error.value= null
        _town1Error.value= null
        _pinCode1Error.value = null

            val fullAddress= AddressModel(null, null, null, null,null)
            _officialAddressField.value = fullAddress
            _officialAddressError.value=null
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun address2Validation(
        scope: CoroutineScope, bottomSheetStateValue: ModalBottomSheetState, pinCode: String,
        area: String, town: String, city: String,state:String, context: Context,
    ) {
        var isAddress2Valid=true
        if (pinCode.isEmpty()) {
            _pinCode2Error.value=context.getString(R.string.enter_valid_pincode)
            isAddress2Valid=false
        }
        if (area.isEmpty()) {
            _area2Error.value=context.getString(R.string.enter_valid_area)
            isAddress2Valid=false
        }
        if (town.isEmpty()) {
            _town2Error.value=context.getString(R.string.enter_valid_town)
            isAddress2Valid=false
        }
        if(isAddress2Valid && _isAddress2Validated.value==true){
            val fullAddress= AddressModel(area, town, city, state,pinCode)
            _permanentAddressField.value = fullAddress
            scope.launch { bottomSheetStateValue.hide() }
            _permanentAddressError.value=null
        }
    }

    fun onClickUpdateProfile(
        navController: NavHostController,
        isUploadDocument: Boolean,
        context: Context,
        officialAddressField:AddressModel,
//        permanentAddressField:AddressModel,
        profile: Profile,
        profileRequester: ProfileFocusRequester,
//        isGST:Boolean
    ) {
        clearMessage()
        if (!validateProfile(profile, officialAddressField,context,profileRequester)) {
            _showErrorMsg.value=true
            _errorMsg.value=context.getString(R.string.please_check_all_fields)
        }else if(isUploadDocument && (profile.statements.isNullOrEmpty()|| profile.invoices.isNullOrEmpty())){
            _showErrorMsg.value=true
            _errorMsg.value=context.getString(R.string.documents_not_uploaded_please_click_upload_documents_button)
        }else if( _checkBox.value == false){
            _showErrorMsg.value=true
            _errorMsg.value=context.getString(R.string.please_accept_our_agent_may_call_u)
        } else {
            _showErrorMsg.value=false
            updateUserDetails(profile, context, navController)
        }
    }
    private fun validateProfile(profile: Profile,
                                officialAddressField:AddressModel,
//                                permanentAddressField:AddressModel,
                                context: Context,   profileRequester: ProfileFocusRequester): Boolean {
        var isProfileValid = true
        val dobPattern = Regex("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$")

        if(profile.udyamNumber !=null){
            if(CommonMethods().isValidUdyamNumber(profile.udyamNumber)!=true){
                _udyamNumberError.value = context.getString(R.string.enter_valid_udyam_number)
                profileRequester.udyamNumberFocus.requestFocus()
                isProfileValid = false
            }
        }
        if(profile.companyName !=null){
           if (profile.companyName.length > 50) {
                _companyNameError.value =context.getString(R.string.company_name_should_not_exceed_50_characters)
                profileRequester.companyNameFocus.requestFocus()
                isProfileValid = false
            }
        }
        if (profile.employmentType.equals("salaried")) {
            if (profile.companyName.isNullOrEmpty()) {
                _companyNameError.value = context.getString(R.string.enter_company_name)
                profileRequester.companyNameFocus.requestFocus()
                isProfileValid = false
            }else if (profile.companyName.length > 50) {
                _companyNameError.value = context.getString(R.string.company_name_should_not_exceed_50_characters)
                profileRequester.companyNameFocus.requestFocus()
                isProfileValid = false
            }
        }
        if (profile.gender.isNullOrEmpty()) {
            _genderError.value = context.getString(R.string.select_gender)
            profileRequester.genderFocus.requestFocus()
            isProfileValid = false
        }
        if (profile.dob.isNullOrEmpty()) {
            _dobError.value = context.getString(R.string.enter_dob)
            profileRequester.dobFocus.requestFocus()
            isProfileValid = false
        }else if(!dobPattern.matches(profile.dob)) {
            _dobError.value = context.getString(R.string.please_enter_date_format)
            profileRequester.dobFocus.requestFocus()
            isProfileValid = false
        }else if(!isValidAdultDob(profile.dob)) {
            _dobError.value = context.getString(R.string.you_must_be_18_years)
            profileRequester.dobFocus.requestFocus()
            isProfileValid = false
        }
        if(profile.officialEmail !=null){
            if(!CommonMethods().isValidEmail(profile.officialEmail)){
                _officialEmailIdError.value = context.getString(R.string.enter_valid__official_email_id)
                profileRequester.officialEmailIdFocus.requestFocus()
                isProfileValid = false
            }else if(profile.officialEmail == profile.email ) {
                _officialEmailIdError.value = context.getString(R.string.official_mail_id_should_not_be_same)
                profileRequester.officialEmailIdFocus.requestFocus()
                isProfileValid = false
            }
        }

        if(profile.employmentType.equals("salaried")){
            if (profile.officialEmail.isNullOrEmpty()) {
                _officialEmailIdError.value = context.getString(R.string.enter_valid_email_id)
                profileRequester.officialEmailIdFocus.requestFocus()
                isProfileValid = false
            }
        }
        if (profile.email.isNullOrEmpty()) {
            _personalEmailIdError.value = context.getString(R.string.enter_valid_email_id)
            profileRequester.personalEmailIdFocus.requestFocus()
            isProfileValid = false
        }else if(!CommonMethods().isValidEmail(profile.email)){
            _personalEmailIdError.value = context.getString(R.string.enter_valid_personal_email_id)
//            _personalEmailIdError.value = "------valid email needed"
            profileRequester.personalEmailIdFocus.requestFocus()
            isProfileValid = false
        }
        if (profile.employmentType.isNullOrEmpty()) {
            _employeeTypeError.value = context.getString(R.string.select_employment_type)
            profileRequester.employeeTypeFocus.requestFocus()
            isProfileValid = false
        }

        if (profile.lastName.isNullOrEmpty()) {
            _lastNameError.value =context.getString(R.string.enter_valid_last_name)
            profileRequester.lastNameFocus.requestFocus()
            isProfileValid = false
        }else if (profile.lastName.length >= 30) {
            _lastNameError.value = context.getString(R.string.last_name_not_exceeded_30_characters)
            profileRequester.lastNameFocus.requestFocus()
            isProfileValid = false
        }
        if (profile.firstName?.isEmpty() == true) {
            _firstNameError.value =context.getString(R.string.enter_valid_first_name)
            profileRequester.firstNameFocus.requestFocus()
            isProfileValid = false
        }else if (profile.firstName?.length!! < 4) {
            _firstNameError.value = context.getString(R.string.first_name_should_contain_minimum_4_letters)
            profileRequester.firstNameFocus.requestFocus()
            isProfileValid = false
        } else if (profile.firstName.length >= 30) {
            _firstNameError.value = context.getString(R.string.first_name_not_exceeded_30_characters)
            profileRequester.firstNameFocus.requestFocus()
            isProfileValid = false
        }

        if (profile.panNumber.isNullOrEmpty()) {
            _panError.value = context.getString(R.string.enter_valid_pan_number)
            profileRequester.panFocus.requestFocus()
            isProfileValid = false
        }else if(CommonMethods().isValidPanNumber(profile.panNumber) != true){
            _panError.value = context.getString(R.string.enter_valid_pan_number)
            profileRequester.panFocus.requestFocus()
            isProfileValid = false
        }
//        if (isGST && profile.udyamNumber.isNullOrEmpty()) {
//            _udyamNumberError.value = context.getString(R.string.enter_valid_udyam_number)
//            isProfileValidated = false
//        }
        return isProfileValid
    }

    private val _mobileNumber: MutableLiveData<String?> = MutableLiveData()
    val mobileNumber: LiveData<String?> = _mobileNumber


    private val _checkBox: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkBox: LiveData<Boolean> = _checkBox

    fun onCheckChanged(checkBox: Boolean) {
        _checkBox.value = checkBox
        if(checkBox){
            onValidatingError(checkBox,null)
        }else{
            onValidatingError(checkBox,"Please accept that our agent may call you to assist further")
        }
        updateGeneralError(null)
    }

    private val _showValidationError: MutableLiveData<Boolean> = MutableLiveData(false)
    val showValidationError: LiveData<Boolean> = _showValidationError
    private val _errorValidationMsg: MutableLiveData<String?> = MutableLiveData(null)
    val errorValidationMsg: LiveData<String?> = _errorValidationMsg

    fun onValidatingError(value: Boolean,errorMsg: String?) {
        _showValidationError.value = value
        _errorValidationMsg.value=errorMsg

    }

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError
    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingSucess = MutableStateFlow(false)
    val isLoadingSucess: StateFlow<Boolean> = _isLoadingSucess

    private val _successResponse = MutableStateFlow<Signup?>(null)
    val successResponse: StateFlow<Signup?> = _successResponse



    private val _shouldShowKeyboard: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowKeyboard: LiveData<Boolean> = _shouldShowKeyboard

    fun resetKeyboardRequest() {
        _shouldShowKeyboard.value = false
    }

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _upDated = MutableStateFlow(false)
    val upDated: StateFlow<Boolean> = _upDated


    private val _shownMsg = MutableStateFlow(false)
    val shownMsg: StateFlow<Boolean> = _shownMsg

    private val _updateResponse = MutableStateFlow<UpdateProfile?>(null)
    val updateResponse: StateFlow<UpdateProfile?> = _updateResponse

    fun updateShownMsg(boolean: Boolean) {
        _shownMsg.value = boolean
    }

    private fun updateUserDetails(
        profile: Profile, context: Context, navController: NavHostController
    ) {
        _isUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateUserDetails(profile, context, navController)
        }
    }

    private suspend fun handleUpdateUserDetails(
        profile: Profile, context: Context, navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateUserDetails(profile)
        }.onSuccess { response ->
            response?.let {
                handleUpdateUserDetailsSuccess(response, profile)
            }
        }.onFailure { error ->
            //Session Management
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleUpdateUserDetails(profile, context, navController, false)
                } else {
                    // If unable to refresh the token, navigate to the sign-in page
                    withContext(Dispatchers.Main) {
                        navigateSignInPage(navController)
                    }
                }
            } else {
                handleFailure(context = context, error = error)
            }
        }
    }

    private suspend fun handleUpdateUserDetailsSuccess(response: UpdateProfile, profile: Profile) {
        withContext(Dispatchers.Main) {
            _isUpdating.value = false
            _upDated.value = true
            _shownMsg.value = false
            _updateResponse.value = response
        }
    }

    private suspend fun handleFailure(context: Context, error: Throwable) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error, context = context, updateErrorMessage = ::updateErrorMessage,
                    _showServerIssueScreen = _showServerIssueScreen, _middleLoan = _middleLoan,
                    _unAuthorizedUser = _unAuthorizedUser, _unexpectedError = _unexpectedError,
                    _showLoader = _showLoader
                )
            } else {
                CommonMethods().handleGeneralException(
                    error = error, _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen, _unexpectedError = _unexpectedError
                )
            }
        }
        _isUpdating.value = false
    }


    private val _checkBoxDetail: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkBoxDetail: LiveData<Boolean> = _checkBoxDetail

    fun onCheckBoxDetailChanged(checkBoxDetail: Boolean) {
        _checkBoxDetail.value = checkBoxDetail
    }

    fun onCheckBoxDetailReset() {
        _checkBoxDetail.value = false
    }

    private val _inProgress = MutableStateFlow(false)
    val inProgress: StateFlow<Boolean> = _inProgress

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    private val _getUserResponse = MutableStateFlow<ProfileResponse?>(null)
    val getUserResponse: StateFlow<ProfileResponse?> = _getUserResponse

    fun getUserDetail(context: Context, navController: NavHostController) {
        _inProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUserDetail(context, navController)
        }
    }

    private suspend fun handleUserDetail(
        context: Context, navController: NavHostController, checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getUserDetail()
        }.onSuccess { response ->
            response?.let {
                handleSuccessUserDetail(response)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    // Retry getting user details with the new access token
                    handleUserDetail(context, navController, false)
                } else {
                    // If unable to refresh the token, navigate to the sign-in page
                    withContext(Dispatchers.Main) {
                        navigateSignInPage(navController)
                    }
                }
            } else {
                // Handle other types of failures
                handleFailure(error = error, context =  context)
            }
        }
    }

    private suspend fun handleSuccessUserDetail(response: ProfileResponse?) {
        withContext(Dispatchers.Main) {
            response?.let { it ->
                _firstName.value = it.data?.firstName ?: ""
                _lastName.value = it.data?.lastName
                _personalEmailId.value = it.data?.email
                _officialEmailId.value = it.data?.officialEmail
                _phoneNumber.value = it.data?.mobileNumber
                _mobileNumber.value = it.data?.mobileNumber
                _dob.value = it.data?.dob
                _gender.value = it.data?.gender
                _panNumber.value = it.data?.panNumber
                _employeeType.value = it.data?.employmentType
                _companyName.value = it.data?.companyName
                _udyamNumber.value = it.data?.udyamNumber
                _income.value = it.data?.income
//                _officialAddress.value = it.data?.address1
//                _officialAddress.value = it.data?.address1
//                val address1 = it.data?.address1?.split(",")?.map { it.trim() }
//                _area1.value = address1?.getOrNull(0) ?: ""
//                _town1.value = address1?.getOrNull(1) ?: ""
                _area1.value = it.data?.address1
                _town1.value = it.data?.address2
                _city1.value = it.data?.city1
                _state1.value = it.data?.state1
                _pinCode1.value = it.data?.pincode1
                _officialAddressField.value =AddressModel(_area1.value, _town1.value, _city1.value, _state1.value, _pinCode1.value)
//                _permanentAddress.value = it.data?.address2
//                _permanentAddress.value = it.data?.address2
//                val address2 = it.data?.address2?.split(",")?.map { it.trim() }
//                _area2.value = address2?.getOrNull(0) ?: ""
//                _town2.value = address2?.getOrNull(1) ?: ""
//                _city2.value = it.data?.city2
//                _state2.value = it.data?.state2
//                _pinCode2.value = it.data?.pincode2

//                _officialAddressField.value =
//                    listOf(
//                    AddressModel(_area1.value, _town1.value, _city1.value, _state1.value, _pinCode1.value)
//                ).filterNot { it.isNullOrEmpty() }
//                    .joinToString(",")

//                _permanentAddressField.value =AddressModel(_area2.value, _town2.value, _city2.value, _state2.value, _pinCode2.value)
//                _permanentAddressField.value = listOf(
//                    _area2.value, _town2.value, _city2.value, _state2.value, _pinCode2.value
//                ).filterNot { it.isNullOrEmpty() }
//                    .joinToString(",")
                _inProgress.value = false
                _isCompleted.value = true
                _getUserResponse.value = it
                TokenManager.save("userName", it.data?.firstName.toString())
                TokenManager.save("mobileNumber", it.data?.mobileNumber.toString())
                TokenManager.save("address", it.data?.pincode1.toString())
                _documentUploadResponse.value = DocumentUpload(data = listOf(it.data?.invoices?.get(0),it.data?.statements?.get(0)))
            }
        }
    }

    private val _pinCode1Response = MutableStateFlow<PincodeModel?>(null)
    val pinCode1Response: StateFlow<PincodeModel?> = _pinCode1Response

    private fun getCity1(pinCode: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.getCity(pinCode)
            }.onSuccess { response ->
                response?.let {
                    _pinCode1Response.value = it
                    withContext(Dispatchers.Main) {
                        if (it.cities.isNullOrEmpty() || it.state.isNullOrEmpty()) {
                            Log.d("Null Or Empty", "City or state is null or empty")
                        } else {
                            _city1.value = it.cities[0]
                            _state1.value = it.state
                        }
                    }
                } ?: withContext(Dispatchers.Main) {
                    _pinCode1Error.value="Please enter valid pincode"
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    Log.d("PinCodeError", it.toString())
                }
            }
        }
    }

    private val _pinCode2Response = MutableStateFlow<PincodeModel?>(null)
    val pinCode2Response: StateFlow<PincodeModel?> = _pinCode2Response

    private fun getCity2(pinCode: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.getCity(pinCode)
            }.onSuccess { response ->
                response?.let {
                    _pinCode2Response.value = it
                    withContext(Dispatchers.Main) {
                        _city2.value = it.cities?.get(0)
                        _state2.value = it.state
                    }
                } ?: withContext(Dispatchers.Main) {
                    _pinCode2Error.value="Please enter valid pincode"
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    Log.d("PinCodeError", it.toString())
                }
            }
        }
    }

    private val _resendingOtp = MutableStateFlow(false)
    val resendingOtp: StateFlow<Boolean> = _resendingOtp

    private val _reSendedOtp = MutableStateFlow(false)
    val reSendedOtp: StateFlow<Boolean> = _reSendedOtp

    private val _resendOtpResponse = MutableStateFlow<Data?>(null)
    val resendOtpResponse: StateFlow<Data?> = _resendOtpResponse

    fun authResendOTP(orderId: String, context: Context, navController: NavHostController) {
        _resendingOtp.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleReSendOtp(orderId, context, navController)
        }
    }

    private suspend fun handleReSendOtp(
        orderId: String, context: Context, navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.authResendOTP(orderId)
        }.onSuccess { response ->
            response?.let {
                handleReSendOtpSuccess(response)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleReSendOtp(orderId, context, navController, false)
                } else {
                    // If unable to refresh the token, navigate to the sign-in page
                    withContext(Dispatchers.Main) {
                        navigateSignInPage(navController)
                    }
                }
            } else {
                handleReSendOtpFailure(error, context)
            }
        }
    }

    private suspend fun handleReSendOtpSuccess(response: Data) {
        withContext(Dispatchers.Main) {
            _reSendedOtp.value = true
            _resendOtpResponse.value = response
            _resendingOtp.value = false
        }
    }

    private suspend fun handleReSendOtpFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                handleReSendOtpResponseException(error, context)
            } else {
                CommonMethods().handleGeneralException(
                    error = error, _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen, _unexpectedError = _unexpectedError
                )
            }
            _resendingOtp.value = false
        }
    }

    private fun handleReSendOtpResponseException(error: ResponseException, context: Context) {
        val statusCode = error.response.status.value
        when (statusCode) {
            400 -> {
                CommonMethods().toastMessage(context,"Please Enter Valid Otp")
             }

            401 -> {
                _unAuthorizedUser.value = true
            }

            500 -> {
                _showServerIssueScreen.value = true
            }

            else -> {
                _unexpectedError.value = true
            }
        }
    }

    private val _logoutResponse = MutableStateFlow<Logout?>(null)
    val logoutResponse: StateFlow<Logout?> = _logoutResponse

    fun logout(refreshToken: String, navController: NavHostController,
               checkForAccessToken: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.logout(refreshToken)
            }.onSuccess { response ->
                withContext(Dispatchers.Main) {
                    response?.let {
                        _logoutResponse.value = it
                        TokenManager.save("accessToken", "")
                        navigateSignInPage(navController, true)
                    }
                }
            }.onFailure { error ->
                if (error is ResponseException &&
                    error.response.status.value == 401
                ) {
                    if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                        logout(refreshToken, navController,false)
                    } else {
                        withContext(Dispatchers.Main) {
                            _logoutResponse.value = null
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _logoutResponse.value = null
                    }
                }
                }
        }
    }


    private fun isNotValidAddress(input: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9,./#'\\-\\s]*$")
        return !regex.matches(input)
    }
}