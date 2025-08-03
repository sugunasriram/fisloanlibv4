package com.github.sugunasriram.fisloanlibv4.fiscode.views.igm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomDropDownField
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DashedBorderCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToIssueListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUpload
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.Images
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.igm.CreateIssueViewModel
import java.io.ByteArrayOutputStream

@Preview(showBackground = true)
@Composable
fun PreviewCreateIssueScreen() {
    CreateIssueScreen(rememberNavController(), "", "", "", "Personal Loan")
}

@Composable
fun CreateIssueScreen(
    navController: NavHostController,
    orderId: String,
    providerId: String,
    orderState: String,
    fromFlow: String
) {
    val context = LocalContext.current
    val createIssuePLViewModel: CreateIssueViewModel = viewModel()

    val shortDesc: String? by createIssuePLViewModel.shortDesc.observeAsState("")
    val longDesc: String? by createIssuePLViewModel.longDesc.observeAsState("")
    val onShortDescError: String? by createIssuePLViewModel.shortDescError.observeAsState("")
    val longDescError: String? by createIssuePLViewModel.longDescError.observeAsState("")

    val issueListLoading by createIssuePLViewModel.issueListLoading.collectAsState()
    val issueListLoaded by createIssuePLViewModel.issueListLoaded.collectAsState()
    val issueCategoriesList by createIssuePLViewModel.issueCategories.collectAsState()
    val subIssueLoading by createIssuePLViewModel.subIssueLoading.collectAsState()
    val subIssueLoaded by createIssuePLViewModel.subIssueLoaded.collectAsState()
    val subIssueCategoryList by createIssuePLViewModel.subIssueCategory.collectAsState()
    val imageUploading by createIssuePLViewModel.imageUploading.collectAsState()
    val imageUploaded by createIssuePLViewModel.imageUploaded.collectAsState()
    val imageUploadResponse by createIssuePLViewModel.imageUploadResponse.collectAsState()
    val issueCreating by createIssuePLViewModel.issueCreating.collectAsState()
    val issueCreated by createIssuePLViewModel.issueCreated.collectAsState()
    val categoryError by createIssuePLViewModel.categoryError.collectAsState()
    val subCategoryError by createIssuePLViewModel.subCategoryError.collectAsState()
    val showImageNotUploadedError by createIssuePLViewModel.showImageNotUploadedError.collectAsState()
    val errorMsg by createIssuePLViewModel.imageUploadError.collectAsState()

    val navigationToSignIn by createIssuePLViewModel.navigationToSignIn.collectAsState()

    val showInternetScreen by createIssuePLViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by createIssuePLViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by createIssuePLViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by createIssuePLViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by createIssuePLViewModel.unAuthorizedUser.observeAsState(false)

    val categoryFocus = FocusRequester()
    val subCategoryFocus = FocusRequester()
    val shortDescFocus = FocusRequester()
    val longDescFocus = FocusRequester()

    val loanState = if (orderState.contains("SANCTIONED", ignoreCase = true)) {
        "SANCTIONED"
    } else if (orderState.contains("CONSENT_REQUIRED", ignoreCase = true)) {
        "CONSENT_REQUIRED"
    } else if (orderState.contains("Disbursed", ignoreCase = true)) {
        "DISBURSED"
    } else {
        "INITIATED"
    }

    var categorySelectedText by remember { mutableStateOf("") }
    var categoryExpand by remember { mutableStateOf(false) }
    val categoryList: List<String>
    var subCategorySelectedText by remember { mutableStateOf("") }
    var subCategoryExpand by remember { mutableStateOf(false) }
    var subCategoryList by remember { mutableStateOf(listOf<String>()) }
    val onCategoryDismiss: () -> Unit = { categoryExpand = false }
    val onCategorySelected: (String) -> Unit = { selectedText ->
        categorySelectedText = selectedText
        if (categorySelectedText.isNotEmpty()) {
            createIssuePLViewModel.getIssueWithSubCategories(context, categorySelectedText)
            subCategorySelectedText = ""
            createIssuePLViewModel.removeImage()
            createIssuePLViewModel.clearShortDesc()
            createIssuePLViewModel.onLongDescriptionChanged("")
        }
        createIssuePLViewModel.getIssueWithSubCategories(context, selectedText)
        createIssuePLViewModel.updateCategoryError(null)
        createIssuePLViewModel.updateSubCategoryError(null)
        createIssuePLViewModel.updateShortDescError(null)
        createIssuePLViewModel.updateLongDescError(null)
        createIssuePLViewModel.removeImage()
        createIssuePLViewModel.clearImageUploadError()
    }


    val onSubCategoryDismiss: () -> Unit = { subCategoryExpand = false }
    val onSubCategorySelected: (String) -> Unit =
        { selectedText ->
            subCategorySelectedText = selectedText
            createIssuePLViewModel.updateSubCategoryError(null)
        }
    val isSubmitEnabled = !imageUploading

    LaunchedEffect(subIssueCategoryList) {
        val updatedSubCategoryList =
            subIssueCategoryList?.data?.subCategories?.mapNotNull { it?.issueSubCategory }
                ?: emptyList()
        subCategoryList = updatedSubCategoryList
    }
    var hasUploadedOnce by remember { mutableStateOf(false) }
    var imageUploadRequired by remember { mutableStateOf(false) }
    var hasSelectedFromGallery by remember { mutableStateOf(false) }
    BackHandler {
        navController.popBackStack()
    }

    val handleSubmit: () -> Unit = if (isSubmitEnabled) {
        {
            shortDesc?.let {
                longDesc?.let { it1 ->
                    onCreateIssueButtonClick(
                        categoryFocus = categoryFocus,
                        subCategoryFocus = subCategoryFocus,
                        imageUploadResponse = imageUploadResponse,
                        imageUploadRequired = imageUploadRequired,
                        context = context,
                        shortDesc = it,
                        longDescFocus = longDescFocus,
                        longDesc = it1,
                        orderId = orderId,
                        categorySelectedText = categorySelectedText,
                        createIssuePLViewModel = createIssuePLViewModel,
                        subCategorySelectedText = subCategorySelectedText,
                        providerId = providerId,
                        loanState = loanState,
                        shortDescFocus = shortDescFocus,
                        fromFlow = fromFlow
                    )
                }
            }
        }
    } else {
        {
            CommonMethods().toastMessage(
                                context,
                                context.getString(R.string.please_wait_while_the_image_is_uploading))

        } // do nothing
    }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            if (issueListLoading || subIssueLoading || issueCreating) {
                CenterProgress()
            } else {
                if (issueCreated) {
                    navigateToIssueListScreen(
                        navController = navController,
                        orderId = orderId,
                        fromFlow = fromFlow,
                        providerId = providerId,
                        loanState = loanState,
                        fromScreen = "Loan Detail"
                    )
                } else {
//                    if (issueListLoaded || subIssueLoaded) {
                    if (issueListLoaded ) {
                        val updatedCategoryList =
                            issueCategoriesList?.data?.mapNotNull { it?.name } ?: emptyList()
                        categoryList = updatedCategoryList
                        FixedTopBottomScreen(
                            navController = navController,
                            topBarBackgroundColor = appOrange,
                            topBarText = stringResource(R.string.raise_issue),
                            showBackButton = true,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            showBottom = true,
                            showSingleButton = true,
                            showErrorMsg =  categoryError != null ||
                                    subCategoryError != null ||
                                    showImageNotUploadedError ||
                                    !onShortDescError.isNullOrEmpty() ||
                                    !longDescError.isNullOrEmpty(),
                            errorMsg =stringResource(R.string.please_enter_all_mandatory_details_and_upload_photo),
                            primaryButtonText = stringResource(R.string.submit),
                            onPrimaryButtonClick = handleSubmit,
                            backgroundColor = appWhite
                        ) {
                            Row(
                                modifier=Modifier.padding(start = 10.dp, top=20.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start) {
                                Text(
                                    text =  stringResource(id = R.string.select_the_appropriate_issue),
                                    color = appBlack,
                                    style = normal16Text700,
                                )
                                Text(
                                    text = " *",
                                    color = appRed,
                                    style = normal14Text400,
                                )
                            }
                            /* Category */
                            CustomDropDownField(
                                selectedText = categorySelectedText,
                                hint = stringResource(id = R.string.category),
                                expand = categoryExpand,
                                setExpand = { categoryExpand = it },
                                itemList = categoryList,
                                onDismiss = onCategoryDismiss,
                                onItemSelected = onCategorySelected,
                                modifier = Modifier.focusRequester(categoryFocus),
                                focus = categoryFocus,
                                error = categoryError,
                                start = 10.dp, end = 10.dp
                            )

                            /* Sub Category */
                            CustomDropDownField(
                                selectedText = subCategorySelectedText, expand = subCategoryExpand,
                                hint = stringResource(id = R.string.sub_category),
                                setExpand = { subCategoryExpand = it },
                                modifier = Modifier.focusRequester(subCategoryFocus),
                                itemList = subCategoryList,
                                onDismiss = onSubCategoryDismiss,
                                onItemSelected = onSubCategorySelected,
                                error = subCategoryError, start = 10.dp, end = 10.dp,
                                enabled = categorySelectedText.isNotEmpty()
                            )
                            StartingText(
                                text = stringResource(id = R.string.tell_us_about_the_problem_you_are_facing),
                                style = normal16Text700,
                                textColor = appBlack,
                                start = 10.dp,
                                end = 10.dp,
                                top = 15.dp
                            )
                            Row(
                                modifier=Modifier.padding(start = 10.dp, top=20.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start) {
                                Text(
                                    text =  stringResource(id = R.string.describe_the_issue),
                                    color = appBlack,
                                    style = normal16Text700,
                                )
                                Text(
                                    text = " *",
                                    color = appRed,
                                    style = normal14Text400,
                                )
                            }

                            IssueDescriptionFields(
                                shortDesc = shortDesc,
                                shortDescFocus = shortDescFocus,
                                shortDescError = onShortDescError,
                                longDescFocus = longDescFocus,
                                createIssuePLViewModel = createIssuePLViewModel,
                                longDesc = longDesc,
                                longDescError = longDescError,
                                context = context
                            )
//                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier=Modifier.padding(start = 10.dp, top=20.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start) {
                                Text(
                                    text =  stringResource(id = R.string.upload_the_issue_photo),
                                    color = appBlack,
                                    style = normal16Text700,
                                )
                                Text(
                                    text = " *",
                                    color = appRed,
                                    style = normal14Text400,
                                )
                            }

                            UploadImageCard(
                                isError = showImageNotUploadedError,
                                imageUploaded = imageUploaded,
                                imageUploading = imageUploading,
                                context = context,
                                createIssueViewModel = createIssuePLViewModel,
//                                cardDataColor = if (hasSelectedFromGallery)appOrange else if (showImageNotUploadedError) errorRed else appOrange,
                                cardDataColor = if (showImageNotUploadedError) errorRed else appOrange,
                                imageUploadRequired = imageUploadRequired,
                                setImageUploadRequired = { imageUploadRequired = it },
                                hasUploadedOnce = hasUploadedOnce,
                                setHasUploadedOnce = { hasUploadedOnce = it },
                                hasSelectedFromGalery = hasSelectedFromGallery,
                                setHasSelectedFromGalery = { hasSelectedFromGallery = it }
                            )
                        }
                    } else {
                        createIssuePLViewModel.getIssueCategories(context)
                    }
                }
            }
        }
    }
}

fun onCreateIssueButtonClick(
    categoryFocus: FocusRequester,
    subCategoryFocus: FocusRequester,
    imageUploadResponse: ImageUpload?,
    imageUploadRequired: Boolean,
    shortDescFocus: FocusRequester,
    longDescFocus: FocusRequester,
    shortDesc: String,
    longDesc: String,
    context: Context,
    categorySelectedText: String,
    createIssuePLViewModel: CreateIssueViewModel,
    orderId: String,
    subCategorySelectedText: String,
    providerId: String,
    loanState: String,
    fromFlow: String
) {
    val images = imageUploadResponse?.data ?: emptyList()
    var isValid = true

    if (images.isEmpty()) {
        createIssuePLViewModel.updateImageNotUploadedErrorMessage(context.getString(R.string.please_select_images_to_upload))
        isValid = false
    }
//    if (imageUploadRequired) {
//        createIssuePLViewModel.updateImageNotUploadedErrorMessage(context.getString(R.string.image_not_uploaded))
//        isValid = false
//    }
    if (longDesc.isEmpty()) {
        longDescFocus.requestFocus()
        createIssuePLViewModel.updateLongDescError(context.getString(R.string.please_enter_description))
        isValid = false
    } else if (hasOnlyInteger(longDesc.trim())) {
        longDescFocus.requestFocus()
        createIssuePLViewModel.updateLongDescError(context.getString(R.string.please_enter_proper_description))
        isValid = false
    } else if (!hasAtLeastOneAlphabet(longDesc.trim())) {
        longDescFocus.requestFocus()
        createIssuePLViewModel.updateLongDescError(context.getString(R.string.please_enter_proper_description))
        isValid = false
    }
    if (shortDesc.isEmpty()) {
        createIssuePLViewModel.updateShortDescError(context.getString(R.string.please_enter_subject))
        shortDescFocus.requestFocus()
        isValid = false
    } else if (hasOnlyInteger(shortDesc.trim())) {
        shortDescFocus.requestFocus()
        createIssuePLViewModel.updateShortDescError(context.getString(R.string.please_enter_proper_subject))
        isValid = false
    } else if (!hasAtLeastOneAlphabet(shortDesc.trim())) {
        shortDescFocus.requestFocus()
        createIssuePLViewModel.updateShortDescError(context.getString(R.string.please_enter_proper_subject))
        isValid = false
    }
    if (subCategorySelectedText.isEmpty()) {
        subCategoryFocus.requestFocus()
        createIssuePLViewModel.updateSubCategoryError(context.getString(R.string.please_select_sub_category))
        isValid = false
    }
    if (categorySelectedText.isEmpty()) {
        categoryFocus.requestFocus()
        createIssuePLViewModel.updateCategoryError(context.getString(R.string.please_select_category))
        isValid = false
    }
    if (isValid) {
        createIssuePLViewModel.updateValidation(
            shortDesc = shortDesc, longDesc = longDesc,
            categorySelectedText = categorySelectedText,
            subCategorySelectedText = subCategorySelectedText,
            image = images, context = context, orderId = orderId,
            providerId = providerId, orderState = loanState,
            fromFlow = fromFlow
        )
    }
}

private fun hasOnlyInteger(input: String): Boolean {
    return input.matches(Regex("^-?\\d+$"))
}

fun hasAtLeastOneAlphabet(text: String): Boolean {
    return text.contains(Regex("[a-zA-Z]"))
}

@Composable
fun IssueDescriptionFields(
    shortDesc: String?,
    shortDescFocus: FocusRequester,
    shortDescError: String?,
    longDescFocus: FocusRequester,
    createIssuePLViewModel: CreateIssueViewModel,
    longDesc: String?,
    longDescError: String?,
    context: Context
) {
    val focusManager = LocalFocusManager.current
    Column() {
        OutlinedTextField(
            value = shortDesc.orEmpty(),
            label = {
                Text(
                    text = stringResource(id = R.string.subject),
                    color = hintGray,
                    style = normal18Text500,
                    textAlign = TextAlign.Start
                )
            },
            onValueChange = {
                createIssuePLViewModel.onShortDescChanged(it)
                createIssuePLViewModel.updateShortDescError(null)
            },
            modifier = Modifier.focusRequester(shortDescFocus)
                .fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                .background(appWhite, shape = RoundedCornerShape(16.dp)),
            textStyle = normal18Text500,
            isError = shortDescError?.isNotEmpty() == true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = appOrange,
                cursorColor = cursorColor,
                errorBorderColor = errorRed
            )
        )
        if (!shortDescError.isNullOrEmpty()) {
            Text(
                text = shortDescError,
                style = normal12Text400,
                color = errorRed,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            )
        }
    }
    Column() {
        OutlinedTextField(
            value = longDesc.orEmpty(),
            label = {
                Text(
                    text = stringResource(id = R.string.description),
                    color = hintGray,
                    style = normal18Text500,
                    textAlign = TextAlign.Start
                )
            },
            onValueChange = {
                createIssuePLViewModel.onLongDescriptionChanged(it)
                createIssuePLViewModel.updateLongDescError(null)
            },
            modifier = Modifier.height(100.dp).focusRequester(longDescFocus)
                .fillMaxWidth().padding(top = 5.dp, start = 10.dp, end = 10.dp)
                .background(appWhite, shape = RoundedCornerShape(16.dp)),
            textStyle = normal18Text500,
            isError = longDescError?.isNotEmpty() == true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            maxLines = 5,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = appOrange,
                cursorColor = cursorColor,
                errorBorderColor = errorRed
            )
        )
        if (!longDescError.isNullOrEmpty()) {
            Text(
                text = longDescError,
                style = normal12Text400,
                color = errorRed,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun UploadImageCard(
    isError: Boolean,
    imageUploaded: Boolean,
    imageUploading: Boolean,
    context: Context,
    createIssueViewModel: CreateIssueViewModel,
    cardDataColor: Color,
    imageUploadRequired: Boolean,
    setImageUploadRequired: (Boolean) -> Unit,
    hasUploadedOnce: Boolean,
    setHasUploadedOnce: (Boolean) -> Unit,
    hasSelectedFromGalery: Boolean,
    setHasSelectedFromGalery: (Boolean) -> Unit
) {
    var selectedImageUris by remember { mutableStateOf(List<Uri?>(3) { null }) }
    var imagesData by remember { mutableStateOf(List<Images?>(3) { null }) }
    var currentIndex by remember { mutableStateOf(-1) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null && currentIndex in 0..2) {
            selectedImageUris = selectedImageUris.toMutableList().also { it[currentIndex] = uri }
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            val base64String = bitmapToBase64(bitmap)
            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
            val imageData = Images(mimetype = mimeType, base64 = base64String)
            imagesData = imagesData.toMutableList().also { it[currentIndex] = imageData }

            setImageUploadRequired(false)
            setHasUploadedOnce(true)

            val validImages = imagesData.filterNotNull()
            if (validImages.isNotEmpty()) {
                createIssueViewModel.clearImageUploadError()
                createIssueViewModel.imageUpload(
                    imageUploadBody = ImageUploadBody(images = validImages),
                    context = context
                )
            }
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUploading) {
            CenterProgress()
        } else {
            val filledCount = selectedImageUris.count { it != null }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                selectedImageUris.forEachIndexed { index, uri ->
                    if (uri != null) {
                        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    currentIndex = index
                                    launcher.launch("image/*")
                                }
                        ) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.LightGray),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                    .clickable {
                                        selectedImageUris = selectedImageUris.toMutableList().also {
                                            it[index] = null
                                        }
                                        imagesData = imagesData.toMutableList().also {
                                            it[index] = null
                                        }
                                        setImageUploadRequired(false)
                                        setHasUploadedOnce(true)

                                        val validImages = imagesData.filterNotNull()
                                        createIssueViewModel.clearImageUploadError()
                                        createIssueViewModel.imageUpload(
                                            imageUploadBody = ImageUploadBody(images = validImages),
                                            context = context
                                        )
                                    }
                                    .size(20.dp)
                            )
                        }
                    } else if (index == filledCount && filledCount < 3) {
                        // Show only the next available upload card
                        DashedBorderCard(
                            onClick = {
                                currentIndex = index
                                launcher.launch("image/*")
                                setHasSelectedFromGalery(true)
                            },
                            label = "Image ${index + 1}",
                            tintColor = cardDataColor,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }
    }
}

//@SuppressLint("SuspiciousIndentation")
//@Composable
//fun UploadImageCard(
//    isError: Boolean,
//    imageUploaded: Boolean,
//    imageUploading: Boolean,
//    context: Context,
//    createIssueViewModel: CreateIssueViewModel,
//    cardDataColor: Color,
//    imageUploadRequired: Boolean,
//    setImageUploadRequired: (Boolean) -> Unit,
//    hasUploadedOnce: Boolean,
//    setHasUploadedOnce: (Boolean) -> Unit,
//    hasSelectedFromGalery: Boolean,
//    setHasSelectedFromGalery: (Boolean) -> Unit
//) {
//    var selectedImageUris by remember { mutableStateOf(List<Uri?>(3) { null }) }
//    var imagesData by remember { mutableStateOf(List<Images?>(3) { null }) }
//    var currentIndex by remember { mutableStateOf(-1) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        if (uri != null && currentIndex in 0..2) {
//            selectedImageUris = selectedImageUris.toMutableList().also { it[currentIndex] = uri }
//            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//            val base64String = bitmapToBase64(bitmap)
//            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
//            val imageData = Images(mimetype = mimeType, base64 = base64String)
//            imagesData = imagesData.toMutableList().also { it[currentIndex] = imageData }
//
//            setImageUploadRequired(false) // since auto-upload, no button needed
//            setHasUploadedOnce(true)
//
//            val validImages = imagesData.filterNotNull()
//            if (validImages.isNotEmpty()) {
//                createIssueViewModel.clearImageUploadError()
//                createIssueViewModel.imageUpload(
//                    imageUploadBody = ImageUploadBody(images = validImages),
//                    context = context
//                )
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        if (imageUploading) {
//            CenterProgress()
//        } else {
//            val singleImageSelected = selectedImageUris.count { it != null } == 1
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 10.dp, horizontal = 10.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                selectedImageUris.forEachIndexed { index, uri ->
//                    if (uri != null) {
//                        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//                        Box(
//                            modifier = Modifier
//                                .size(100.dp)
//                                .clickable {
//                                    currentIndex = index
//                                    launcher.launch("image/*")
//                                }
//                        ) {
//                            Card(
//                                shape = RoundedCornerShape(10.dp),
//                                border = BorderStroke(1.dp, Color.LightGray),
//                                modifier = Modifier.fillMaxSize()
//                            ) {
//                                Image(
//                                    bitmap = bitmap.asImageBitmap(),
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier.fillMaxSize()
//                                )
//                            }
//
//                            Icon(
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Delete",
//                                tint = Color.White,
//                                modifier = Modifier
//                                    .align(Alignment.TopEnd)
//                                    .padding(4.dp)
//                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
//                                    .clickable {
//                                        selectedImageUris = selectedImageUris.toMutableList().also {
//                                            it[index] = null
//                                        }
//                                        imagesData = imagesData.toMutableList().also {
//                                            it[index] = null
//                                        }
//                                        setImageUploadRequired(false)
//                                        setHasUploadedOnce(true)
//
//                                        val validImages = imagesData.filterNotNull()
////                                        if (validImages.isNotEmpty()) {
//                                            createIssueViewModel.clearImageUploadError()
//                                            createIssueViewModel.imageUpload(
//                                                imageUploadBody = ImageUploadBody(images = validImages),
//                                                context = context
//                                            )
////                                        }
//                                    }
//                                    .size(20.dp)
//                            )
//                        }
//                    } else {
//                        DashedBorderCard(
//                            onClick = {
//                                currentIndex = index
//                                launcher.launch("image/*")
//                                setHasSelectedFromGalery(true)
//                            },
//                            label = "Image ${index + 1}",
//                            tintColor = cardDataColor,
//                            modifier = Modifier.size(100.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    val newWidth: Int
    val newHeight: Int

    if (bitmap.width > bitmap.height) {
        newWidth = maxWidth
        newHeight = (newWidth / aspectRatio).toInt()
    } else {
        newHeight = maxHeight
        newWidth = (newHeight * aspectRatio).toInt()
    }

    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val resizedBitmap = resizeBitmap(bitmap, 800, 600)
    val byteArrayOutputStream = ByteArrayOutputStream()
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}
