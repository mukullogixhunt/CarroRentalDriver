package com.carro.chauffeur.ui.fragment.bottom;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.carro.chauffeur.BuildConfig;
import com.carro.chauffeur.R;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.LoginResponse;
import com.carro.chauffeur.databinding.EditVendorDocDialogBinding;
import com.carro.chauffeur.databinding.FragmentProfileBinding;
import com.carro.chauffeur.model.LoginModel;
import com.carro.chauffeur.ui.activity.EditProfileActivity;
import com.carro.chauffeur.ui.common.BaseFragment;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.PreferenceUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProfileFragment";
    private FragmentProfileBinding binding;
    private LoginModel loginModel = new LoginModel();
    private Dialog imagePickDialog;
    private BottomSheetDialog docDialog;
    private EditVendorDocDialogBinding docDialogBinding;

    private Uri imageUri;
    private File licenceImgFile = null;
    private File profileImgFile = null;
    private int currentImageType; // 1 for Licence, 2 for Profile

    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserPreferences();
        initialization();
        getAppVersion();
    }

    @Override
    public void onResume() {
        super.onResume();
        userDetailsApi();
    }

    private void getUserPreferences() {
        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, requireContext());
        if (userData != null && !userData.isEmpty()) {
            loginModel = new Gson().fromJson(userData, LoginModel.class);
        }
    }

    private void initialization() {
        binding.tvEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        binding.tvEditDoc.setOnClickListener(v -> updateDocDialog());
        binding.fabChangePhoto.setOnClickListener(v -> {
            currentImageType = 2; // Profile Image
            imagePickerDialog();
        });
    }

    private void setData() {
        if (loginModel == null || getContext() == null) return;

        // Basic Details
        binding.rowName.tvLabel.setText(R.string.name);
        binding.rowName.tvValue.setText(loginModel.getmDriverName());

        binding.rowPhoneNumber.tvLabel.setText(R.string.phone_number);
        binding.rowPhoneNumber.tvValue.setText(loginModel.getmDriverMobile());

        binding.rowLicenceExpiry.tvLabel.setText(R.string.licence_expiry_date);
        binding.rowLicenceExpiry.tvValue.setText(loginModel.getmDriverDrivelicExpdate());

        Glide.with(getContext())
                .load(ImagePathDecider.getDriverImagePath() + loginModel.getmDriverImg())
                .placeholder(R.drawable.img_no_profile)
                .error(R.drawable.img_no_profile)
                .into(binding.ivUser);


        // Load License Image
        Glide.with(getContext())
                .load(ImagePathDecider.getDriverImagePath() + loginModel.getmDriverDrivelic()) // Assuming license images are in the same folder
                .placeholder(R.drawable.img_no_profile) // You can create a specific placeholder for license
                .error(R.drawable.img_no_profile)
                .into(binding.ivLicence);

    }

    private void userDetailsApi() {
        if (loginModel == null || loginModel.getmDriverId() == null) return;
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.user_details(loginModel.getmDriverId());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                hideLoader();
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        loginModel = response.body().getData().get(0);
                        PreferenceUtils.setString(Constant.PreferenceConstant.USER_DATA,
                                new Gson().toJson(loginModel), requireContext());
                        setData();
                    } else {
                        showError(response.message());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "userDetailsApi onResponse Exception", e);
                    showError("An error occurred.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "userDetailsApi onFailure", t);
                showError("Something went wrong");
            }
        });
    }

    private void updateDocDialog() {
        docDialogBinding = EditVendorDocDialogBinding.inflate(getLayoutInflater());
        docDialog = new BottomSheetDialog(requireContext());
        docDialog.setContentView(docDialogBinding.getRoot());
        docDialog.show();

        docDialogBinding.etLicenceExpiry.setText(loginModel.getmDriverDrivelicExpdate());
        docDialogBinding.tvLicenceImage.setText(getFileNameStatus(loginModel.getmDriverDrivelic()));

        docDialogBinding.etLicenceExpiry.setOnClickListener(v -> showDatePickerDialog());
        docDialogBinding.tvUploadLicence.setOnClickListener(v -> {
            currentImageType = 1; // Licence Image
            imagePickerDialog();
        });

        docDialogBinding.btnUpdateDoc.setOnClickListener(v -> {
            String expiryDate = docDialogBinding.etLicenceExpiry.getText().toString();
            updateDocsApi(expiryDate);
        });
    }

    private String getFileNameStatus(String fileName) {
        return (fileName != null && !fileName.isEmpty()) ? "Uploaded" : "Not Uploaded";
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                    docDialogBinding.etLicenceExpiry.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateDocsApi(String expiryDate) {
        if (licenceImgFile == null && (expiryDate == null || expiryDate.isEmpty())) {
            Toast.makeText(getContext(), "Please select an image or enter a date to update.", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), loginModel.getmDriverId());
        RequestBody rbLicExpDate = RequestBody.create(MediaType.parse("text/plain"), expiryDate);

        MultipartBody.Part licImagePart = null;
        if (licenceImgFile != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), licenceImgFile);
            licImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.LIC_IMG, licenceImgFile.getName(), requestFile);
        }

        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.update_doc_details(rbUserId, rbLicExpDate, licImagePart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                hideLoader();
                if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                    Toast.makeText(getContext(), "Documents Updated Successfully", Toast.LENGTH_SHORT).show();
                    docDialog.dismiss();
                    userDetailsApi(); // Refresh data
                } else {
                    showError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "updateDocsApi failure", t);
                showError("Something went wrong");
            }
        });
    }

    private void updateProfilePicApi() {
        if (profileImgFile == null) return;

        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), loginModel.getmDriverId());
        RequestBody rbUserName = RequestBody.create(MediaType.parse("text/plain"), loginModel.getmDriverName());
//        RequestBody rbUserAltMobile = RequestBody.create(MediaType.parse("text/plain"), loginModel.getmDriverAltMobile());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profileImgFile);
        MultipartBody.Part profilePicPart = MultipartBody.Part.createFormData(Constant.ApiKey.USER_PIC, profileImgFile.getName(), requestFile);

        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.update_profile(rbUserId, rbUserName, /*rbUserAltMobile,*/ profilePicPart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                hideLoader();
                if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                    Toast.makeText(getContext(), "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                    userDetailsApi();
                } else {
                    showError(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "updateProfilePicApi failure", t);
                showError("Something went wrong");
            }
        });
    }

    private void imagePickerDialog() {
        imagePickDialog = new Dialog(requireContext(), R.style.my_dialog);
        imagePickDialog.setCancelable(false);
        imagePickDialog.setContentView(R.layout.image_selection_dialog);
        imagePickDialog.show();
        imagePickDialog.findViewById(R.id.ivCamera).setOnClickListener(view -> checkCameraPermission());
        imagePickDialog.findViewById(R.id.ivGallery).setOnClickListener(view -> checkGalleryPermission());
        imagePickDialog.findViewById(R.id.tvCancel).setOnClickListener(view -> imagePickDialog.dismiss());
    }

    private void checkCameraPermission() {
        if (imagePickDialog != null) imagePickDialog.dismiss();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        } else {
            openCamera();
        }
    }

    private void checkGalleryPermission() {
        if (imagePickDialog != null) imagePickDialog.dismiss();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
            } else {
                openGallery();
            }
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File file = File.createTempFile("temp_image", ".jpg", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            launchImageCropper(imageUri);
        }
    });

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            launchImageCropper(result.getData().getData());
        }
    });

    private void launchImageCropper(Uri uri) {
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.imageSourceIncludeGallery = false;
        cropImageOptions.imageSourceIncludeCamera = true;
        cropImageOptions.outputCompressQuality = 60;
        cropImageOptions.fixAspectRatio = true;

        if (currentImageType == 1) { // Licence has a rectangular aspect ratio
            cropImageOptions.aspectRatioX = 16;
            cropImageOptions.aspectRatioY = 10;
        } else { // Profile pic is square
            cropImageOptions.aspectRatioX = 1;
            cropImageOptions.aspectRatioY = 1;
        }

        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(uri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    private final ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(requireContext(), true);
            if (croppedImagePath == null) return;

            File croppedFile = new File(croppedImagePath);

            if (currentImageType == 1) { // Licence Image
                licenceImgFile = croppedFile;
                if (docDialogBinding != null) docDialogBinding.tvLicenceImage.setText(croppedFile.getName());
            } else if (currentImageType == 2) { // Profile Image
                profileImgFile = croppedFile;
                updateProfilePicApi();
            }
        }
    });

    private void getAppVersion() {
        try {
            PackageInfo packageInfo = requireActivity().getPackageManager().getPackageInfo(requireActivity().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            binding.tvVersion.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}