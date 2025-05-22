package com.github.sugunasriram.fisloanlibv4.fis_code.views.igm

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomDropDownField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DashedBorderCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToIssueListScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.ImageUpload
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.Images
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.igm.CreateIssueViewModel
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

    val loanState = if (orderState.contains("Loan SANCTIONED", ignoreCase = true)) {
        "SANCTIONED"
    } else if (orderState.contains(" Loan CONSENT_REQUIRED", ignoreCase = true)) {
        "CONSENT_REQUIRED"
    } else if (orderState.contains("Loan Disbursed", ignoreCase = true)) {
        "DISBURSED"
    } else {
        "INITIATED"
    }

    val categoryErrorMessage = stringResource(R.string.please_select_category)
    val subCategoryErrorMessage = stringResource(R.string.please_select_sub_category)
    val shortDescErrorMessage = stringResource(R.string.please_enter_short_description)
    val longDescErrorMessage = stringResource(R.string.please_enter_long_description)
    val properShortDescErrorMessage = stringResource(R.string.please_enter_proper_short_description)
    val properLongDescErrorMessage = stringResource(R.string.please_enter_proper_long_description)
    var categorySelectedText by remember { mutableStateOf("") }
    var categoryExpand by remember { mutableStateOf(false) }
    val categoryList: List<String>
    val onCategoryDismiss: () -> Unit = { categoryExpand = false }
    val onCategorySelected: (String) -> Unit = { selectedText ->
        categorySelectedText = selectedText
        createIssuePLViewModel.getIssueWithSubCategories(context, selectedText)
        createIssuePLViewModel.updateCategoryError(null)
    }

    var subCategorySelectedText by remember { mutableStateOf("") }
    var subCategoryExpand by remember { mutableStateOf(false) }
    var subCategoryList by remember { mutableStateOf(listOf<String>()) }
    val onSubCategoryDismiss: () -> Unit = { subCategoryExpand = false }
    val onSubCategorySelected: (String) -> Unit =
        { selectedText ->
            subCategorySelectedText = selectedText
            createIssuePLViewModel.updateSubCategoryError(null)
        }

    LaunchedEffect(categorySelectedText) {
        if (categorySelectedText.isNotEmpty()) {
            createIssuePLViewModel.getIssueWithSubCategories(context, categorySelectedText)
            subCategorySelectedText = ""
            createIssuePLViewModel.removeImage()
            createIssuePLViewModel.clearShortDesc()
            createIssuePLViewModel.onLongDescriptionChanged("")
        }
    }

    LaunchedEffect(subIssueCategoryList) {
        val updatedSubCategoryList =
            subIssueCategoryList?.data?.subCategories?.mapNotNull { it?.issueSubCategory }
                ?: emptyList()
        subCategoryList = updatedSubCategoryList
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
                        orderId = "12345",
                        fromFlow = fromFlow,
                        providerId = "12345",
                        loanState = "No Need",
                        fromScreen = "Create Issue"
                    )
                } else {
                    if (issueListLoaded || subIssueLoaded) {
                        val updatedCategoryList =
                            issueCategoriesList?.data?.mapNotNull { it?.name } ?: emptyList()
                        categoryList = updatedCategoryList
                        FixedTopBottomScreen(
                            navController = navController,
                            topBarBackgroundColor = appOrange,
                            topBarText = stringResource(R.string.raise_issue),
                            showBackButton = true,
                            onBackClick = { navController.popBackStack() },
                            showBottom = true,
                            showSingleButton = true,
                            primaryButtonText = stringResource(R.string.submit),
                            onPrimaryButtonClick = {
                                shortDesc?.let {
                                    longDesc?.let { it1 ->
                                        onCreateIssueButtonClick(
                                            categoryFocus = categoryFocus,
                                            subCategoryFocus = subCategoryFocus,
                                            imageUploadResponse = imageUploadResponse,
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
                                            fromFlow = fromFlow,
                                            categoryErrorMessage = categoryErrorMessage,
                                            subCategoryErrorMessage = subCategoryErrorMessage,
                                            shortDescErrorMessage = shortDescErrorMessage,
                                            longDescErrorMessage = longDescErrorMessage,
                                            properLongDescErrorMessage = properLongDescErrorMessage,
                                            properShortDescErrorMessage = properShortDescErrorMessage
                                        )
                                    }
                                }
                            },
                            backgroundColor = appWhite
                        ) {
                            RegisterText(
                                text = stringResource(id = R.string.select_the_appropriate_issue),
                                textColor = appBlack,
                                style = normal16Text700,
                                bottom = 5.dp,
                                textAlign = TextAlign.Start,
                                boxAlign = Alignment.TopStart,
                                start = 10.dp,
                                top = 20.dp
                            )

                            /* Category */
                            CustomDropDownField(
                                selectedText = categorySelectedText,
                                hint = stringResource(id = R.string.category),
                                expand = categoryExpand,
                                setExpand = { categoryExpand = it },
                                itemList = categoryList,
                                onDismiss = onCategoryDismiss,
                                onItemSelected = onCategorySelected,
                                focus = categoryFocus,
                                error = categoryError,
                                start = 15.dp, bottom = 8.dp
                            )

                            /* Sub Category */
                            CustomDropDownField(
                                selectedText = subCategorySelectedText, expand = subCategoryExpand,
                                hint = stringResource(id = R.string.sub_category),
                                setExpand = { subCategoryExpand = it },
                                itemList = subCategoryList,
                                onDismiss = onSubCategoryDismiss,
                                onItemSelected = onSubCategorySelected,
                                error = subCategoryError, start = 15.dp
                            )
                            StartingText(
                                text = stringResource(id = R.string.tell_us_about_the_problem_you_are_facing),
                                style = normal16Text700,
                                textColor = appBlack,
                                start = 10.dp,
                                end = 10.dp,
                                top = 15.dp
                            )
                            RegisterText(
                                text = stringResource(id = R.string.describe_the_issue),
                                textColor = appBlack,
                                style = normal16Text700,
                                bottom = 5.dp,
                                textAlign = TextAlign.Start,
                                boxAlign = Alignment.TopStart,
                                start = 10.dp,
                                top = 15.dp
                            )
                            IssueDescriptionFields(
                                shortDesc = shortDesc,
                                shortDescFocus = shortDescFocus,
                                shortDescError = onShortDescError,
                                longDescFocus = longDescFocus,
                                createIssuePLViewModel = createIssuePLViewModel,
                                longDesc = longDesc,
                                longDescError = longDescError
                            )
                            IssuesWithImage(
                                imageUploaded = imageUploaded,
                                imageUploading = imageUploading,
                                context = context,
                                createIssuePLViewModel = createIssuePLViewModel,
                                showImageNotUploadedError = showImageNotUploadedError
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

@Composable
fun IssuesWithImage(
    imageUploaded: Boolean,
    imageUploading: Boolean,
    createIssuePLViewModel: CreateIssueViewModel,
    context: Context,
    showImageNotUploadedError: Boolean
) {
    Spacer(modifier = Modifier.height(8.dp))
    UploadImageCard(
        isError = showImageNotUploadedError,
        imageUploaded = imageUploaded,
        imageUploading = imageUploading,
        context = context,
        createIssueViewModel = createIssuePLViewModel,
        cardDataColor = if (showImageNotUploadedError) errorRed else appOrange
    )
}

fun onCreateIssueButtonClick(
    categoryFocus: FocusRequester,
    subCategoryFocus: FocusRequester,
    imageUploadResponse: ImageUpload?,
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
    fromFlow: String,
    categoryErrorMessage: String,
    subCategoryErrorMessage: String,
    shortDescErrorMessage: String,
    longDescErrorMessage: String,
    properShortDescErrorMessage: String,
    properLongDescErrorMessage: String
) {
    val images = imageUploadResponse?.data ?: emptyList()
    var isValid = true
    if (categorySelectedText.isEmpty()) {
        createIssuePLViewModel.updateCategoryError(categoryErrorMessage)
        isValid = false
    }
    if (subCategorySelectedText.isEmpty()) {
        createIssuePLViewModel.updateSubCategoryError(subCategoryErrorMessage)
        isValid = false
    }
    if (images.isEmpty()) {
        createIssuePLViewModel.updateImageNotUploadedErrorMessage()
        isValid = false
    }
    if (shortDesc.isEmpty()) {
        createIssuePLViewModel.updateShortDescError(shortDescErrorMessage)
        isValid = false
    }
    if (longDesc.isEmpty()) {
        createIssuePLViewModel.updateLongDescError(longDescErrorMessage)
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

@Composable
fun IssueDescriptionFields(
    shortDesc: String?,
    shortDescFocus: FocusRequester,
    shortDescError: String?,
    longDescFocus: FocusRequester,
    createIssuePLViewModel: CreateIssueViewModel,
    longDesc: String?,
    longDescError: String?
) {
    Column() {
        OutlinedTextField(
            value = shortDesc.orEmpty(),
            label = {
                Text(
                    text = stringResource(id = R.string.short_description),
                    color = hintGray,
                    style = normal18Text500,
                    textAlign = TextAlign.Start
                )
            },
            onValueChange = {
                createIssuePLViewModel.onShortDescChanged(it)
                createIssuePLViewModel.updateShortDescError(null)
            },
            modifier = Modifier
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
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
    Column() {
        OutlinedTextField(
            value = longDesc.orEmpty(),
            label = {
                Text(
                    text = stringResource(id = R.string.long_description),
                    color = hintGray,
                    style = normal18Text500,
                    textAlign = TextAlign.Start
                )
            },
            onValueChange = {
                createIssuePLViewModel.onLongDescriptionChanged(it)
                createIssuePLViewModel.updateLongDescError(null)
            },
            modifier = Modifier.height(100.dp)
                .fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .background(appWhite, shape = RoundedCornerShape(16.dp)),
            textStyle = normal18Text500,
            isError = longDescError?.isNotEmpty() == true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
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

@Composable
fun UploadImageCard(
    isError: Boolean,
    imageUploaded: Boolean,
    imageUploading: Boolean,
    context: Context,
    createIssueViewModel: CreateIssueViewModel,
    cardDataColor: Color
) {
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var imagesData by remember { mutableStateOf<List<Images>>(emptyList()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            selectedImageUris = uris.take(3) // Limit to 3 images
            imagesData = selectedImageUris.map { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val base64String = bitmapToBase64(bitmap)
                val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                Images(mimetype = mimeType, base64 = base64String)
            }

            if (imagesData.isNotEmpty() && !imageUploading) {
                createIssueViewModel.imageUpload(
                    imageUploadBody = ImageUploadBody(images = imagesData),
                    context = context
                )
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RegisterText(
            text = stringResource(id = R.string.upload_the_issue_photo),
            textColor = appBlack,
            style = normal16Text700,
            bottom = 5.dp,
            textAlign = TextAlign.Start,
            boxAlign = Alignment.TopStart,
            modifier = Modifier.weight(0.8f)
        )
        if (selectedImageUris.size == 3) {
            Image(
                painter = painterResource(R.drawable.edit_image),
                contentDescription = null,
                modifier = Modifier.weight(0.2f).clickable {
                    launcher.launch("image/*")
                }
            )
        }
    }

    when {
        imageUploading -> { CenterProgress() }
        imageUploaded || selectedImageUris.isNotEmpty() -> {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedImageUris) { uri ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                if (selectedImageUris.size < 3) {
                    item {
                        DashedBorderCard(
                            onClick = { launcher.launch("image/*") },
                            label = "Image ${selectedImageUris.size + 1}",
                            tintColor = cardDataColor
                        )
                    }
                }
            }
        }
        else -> {
            DashedBorderCard(
                onClick = { launcher.launch("image/*") },
                label = if (isError) "Upload Image" else "Image ${selectedImageUris.size + 1}",
                tintColor = cardDataColor
            )
        }
    }
}

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
