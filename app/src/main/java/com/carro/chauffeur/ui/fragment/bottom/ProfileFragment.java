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
//        binding.tvEdit.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
//            startActivity(intent);
//        });

//        binding.tvEditDoc.setOnClickListener(v -> updateDocDialog());
//        binding.fabChangePhoto.setOnClickListener(v -> {
//            currentImageType = 2; // Profile Image
//            imagePickerDialog();
//        });
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