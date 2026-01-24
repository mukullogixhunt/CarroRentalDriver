package com.carro.chauffeur.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.gson.Gson;
import com.carro.chauffeur.BuildConfig;
import com.carro.chauffeur.R;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.BranchResponse;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.databinding.ActivityAddCarBinding;
import com.carro.chauffeur.model.BranchModel;
import com.carro.chauffeur.model.CarBrandModel;
import com.carro.chauffeur.model.CarListModel;
import com.carro.chauffeur.model.CarNameModel;
import com.carro.chauffeur.model.CarTypeModel;
import com.carro.chauffeur.ui.common.BaseActivity;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.PreferenceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarActivity extends BaseActivity {

    ActivityAddCarBinding binding;
    List<CarBrandModel> carBrandModelList = new ArrayList<>();
    List<CarTypeModel> carTypeModelList = new ArrayList<>();
    List<CarNameModel> carNameModelList = new ArrayList<>();
    List<BranchModel> branchModelList = new ArrayList<>();
    private List<String> fuelList = new ArrayList<>();
    private List<String> serviceList = new ArrayList<>();
    private List<String> driverList = new ArrayList<>();
    private List<String> acTypeList = new ArrayList<>();
    String branch = "";
    String branch_name = "";
    String car_fuel = "";
    String user_id = "";
    String acType = "";
    String driverTye = "";
    String serviceType = "";
    String carType = "";
    String carId = "";
    private Dialog dialog;
    private Uri imageUri;
    private File uploadImg = null;

    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;

    CarListModel carListModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });        getPreference();

    }

    private void getPreference() {
        user_id = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, AddCarActivity.this);
        String carJson = getIntent().getStringExtra(Constant.BundleExtras.CAR_DETAIL);
        carListModel = new Gson().fromJson(carJson, CarListModel.class);

        carType=getIntent().getStringExtra(Constant.BundleExtras.CAR_TYPE);
        initialization();
    }

    private void initialization() {

        getBranchAPi();
        initiateFuel();
        initiateAcType();
        initiateService();
        initiateDriverType();

        if (carType.equals("update")){
            setData();
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCarActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    addCarAPi();
                }
            }
        });

        binding.tvUploadCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerDialog();
            }
        });




    }

    private void setData(){
        carId=carListModel.getmCtypeId();
        binding.etCarName.setText(carListModel.getmCtypeTitle());
        binding.etCarNumber.setText(carListModel.getmCtypeNumber());
        binding.tvCarImg.setText(carListModel.getmCtypeImg());
        binding.etSeats.setText(carListModel.getmCtypeSeat());
        binding.etBags.setText(carListModel.getmCtypeLuggage());
        binding.etPrice.setText(carListModel.getmCtypePrice());
        binding.etTc.setText(Html.fromHtml(carListModel.getmCtypeTc()));
        binding.etInclusion.setText(Html.fromHtml(carListModel.getmCtypeInclusion()));
        binding.etExclusion.setText(Html.fromHtml(carListModel.getmCtypeExclusion()));
    }

    private void initiateFuel() {
        fuelList.clear();
        fuelList.add("Select Fuel");
        fuelList.add("Petrol");
        fuelList.add("Diesel");
        ArrayAdapter genderAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, fuelList);
        binding.spFuelType.setAdapter(genderAdapter);

        if (carListModel != null && carListModel.getmCtypeFuel() != null && !carListModel.getmCtypeFuel().isEmpty()) {
            if (carListModel.getmCtypeFuel().equalsIgnoreCase("Petrol")) {
                binding.spFuelType.setSelection(1);
            } else {
                binding.spFuelType.setSelection(2);
            }
        }


        binding.spFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_fuel = binding.spFuelType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initiateService() {
        serviceList.clear();
        serviceList.add("Select Service");
        serviceList.add("Cab");
        serviceList.add("Self");
        serviceList.add("Luxury");
        ArrayAdapter serviceAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, serviceList);
        binding.spServiceType.setAdapter(serviceAdapter);

        if (carListModel != null && carListModel.getmCtypeServtype() != null && !carListModel.getmCtypeServtype().isEmpty()) {
            if (carListModel.getmCtypeServtype().equalsIgnoreCase("1")) {
                binding.spServiceType.setSelection(1);
            } else if (carListModel.getmCtypeServtype().equalsIgnoreCase("2")) {
                binding.spServiceType.setSelection(2);
            } else {
                binding.spServiceType.setSelection(3);
            }
        }

        binding.spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceType = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initiateDriverType() {
        driverList.clear();
        driverList.add("Select Driver Type");
        driverList.add("Manual");
        driverList.add("Automatic");
        ArrayAdapter driverAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, driverList);
        binding.spDriverType.setAdapter(driverAdapter);

        if (carListModel != null && carListModel.getmCtypeDrivetype() != null && !carListModel.getmCtypeDrivetype().isEmpty()) {
            if (carListModel.getmCtypeDrivetype().equalsIgnoreCase("Manual")) {
                binding.spDriverType.setSelection(1);
            } else {
                binding.spDriverType.setSelection(2);
            }
        }


        binding.spDriverType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driverTye = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initiateAcType() {
        acTypeList.clear();
        acTypeList.add("Select Ac Type");
        acTypeList.add("Ac");
        acTypeList.add("Non Ac");
        ArrayAdapter acAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, acTypeList);
        binding.spAcType.setAdapter(acAdapter);

        if (carListModel != null && carListModel.getmCtypeAC() != null && !carListModel.getmCtypeAC().isEmpty()) {
            if (carListModel.getmCtypeAC().equalsIgnoreCase("1")) {
                binding.spAcType.setSelection(1);
            } else {
                binding.spAcType.setSelection(2);
            }
        }

        binding.spAcType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                acType = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//
//    private void getCarBrandAPi() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<CarBrandResponse> call = apiInterface.carBrand();
//        call.enqueue(new Callback<CarBrandResponse>() {
//            @Override
//            public void onResponse(Call<CarBrandResponse> call, Response<CarBrandResponse> response) {
//                //  hideLoader();
//                try {
//                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
//                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
//
//                            carBrandModelList.clear();
//                            carBrandModelList.add(new CarBrandModel("Select Brand"));
//                            carBrandModelList.addAll(response.body().getData());
//
//
//                            ArrayAdapter<CarBrandModel> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, carBrandModelList);
//                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//                            binding.spCarBrand.setAdapter(itemAdapter);
//
//                            binding.spCarBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    car_brand = carBrandModelList.get(position).getmBrandId();
//                                    getCarModelAPi(car_brand);
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//
//
//                        } else {
//                        }
//                    } else {
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CarBrandResponse> call, Throwable t) {
//                showError("something went wrong");
//
//
//            }
//        });
//    }
//    private void getCarTypeAPi() {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<CarTypeResponse> call = apiInterface.cartype();
//        call.enqueue(new Callback<CarTypeResponse>() {
//            @Override
//            public void onResponse(Call<CarTypeResponse> call, Response<CarTypeResponse> response) {
//                //  hideLoader();
//                try {
//                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
//                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
//
//                            carTypeModelList.clear();
//                            carTypeModelList.add(new CarTypeModel("Select Car Type"));
//                            carTypeModelList.addAll(response.body().getData());
//
//
//                            ArrayAdapter<CarTypeModel> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, carTypeModelList);
//                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//                            binding.spCarType.setAdapter(itemAdapter);
//
//                            binding.spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    car_type = carTypeModelList.get(position).getmCtypeId();
//
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//
//
//                        } else {
//                        }
//                    } else {
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CarTypeResponse> call, Throwable t) {
//                showError("something went wrong");
//
//
//            }
//        });
//    }
//
//    private void getCarModelAPi(String brand) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<CarNameResponse> call = apiInterface.carModel(brand);
//        call.enqueue(new Callback<CarNameResponse>() {
//            @Override
//            public void onResponse(Call<CarNameResponse> call, Response<CarNameResponse> response) {
//                //  hideLoader();
//                try {
//                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
//                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
//
//                            carNameModelList.clear();
//                            carNameModelList.add(new CarNameModel("Select Model"));
//                            carNameModelList.addAll(response.body().getData());
//
//
//                            ArrayAdapter<CarNameModel> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, carNameModelList);
//                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//                            binding.spCarName.setAdapter(itemAdapter);
//
//                            binding.spCarName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    car_model = carNameModelList.get(position).getmModelId();
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//
//
//                        } else {
//                        }
//                    } else {
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CarNameResponse> call, Throwable t) {
//                showError("something went wrong");
//
//
//            }
//        });
//    }

    private void getBranchAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BranchResponse> call = apiInterface.get_branch();
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            branchModelList.clear();
                            branchModelList.add(new BranchModel("Select Branch"));
                            branchModelList.addAll(response.body().getData());


                            ArrayAdapter<BranchModel> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, branchModelList);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            binding.spBranch.setAdapter(itemAdapter);

                            if (carListModel.getmCtypeBranch() != null && !carListModel.getmCtypeBranch().isEmpty()) {
                                int branchSelected = 1;
                                for (int i = 1; i < branchModelList.size(); i++) {
                                    if (branchModelList.get(i).getmBranchId().equals(carListModel.getmCtypeBranch())) {
                                        branchSelected = i;
                                    }
                                }
                                binding.spBranch.setSelection(branchSelected);
                            }






                            binding.spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    branch = branchModelList.get(position).getmBranchId();
                                    branch_name = branchModelList.get(position).getmBranchTitle();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } else {
                        }
                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                showError("something went wrong");


            }
        });
    }

    private void addCarAPi() {

        String carNumber = binding.etCarNumber.getText().toString().toUpperCase();
        String carSeats = binding.etSeats.getText().toString();
        String carName = binding.etCarName.getText().toString();
        String carLuggage = binding.etBags.getText().toString();
        String carPrice = binding.etPrice.getText().toString();
        String carTc = binding.etTc.getText().toString();
        String carInclusion = binding.etInclusion.getText().toString();
        String carExclusion = binding.etExclusion.getText().toString();

        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);
        RequestBody rbService = RequestBody.create(MediaType.parse("text/plain"), serviceType);
        RequestBody rbBranch = RequestBody.create(MediaType.parse("text/plain"), branch);
        RequestBody rbCarName = RequestBody.create(MediaType.parse("text/plain"), carName);
        RequestBody rbDriverType = RequestBody.create(MediaType.parse("text/plain"), driverTye);
        RequestBody rbCarNumber = RequestBody.create(MediaType.parse("text/plain"), carNumber);
        RequestBody rbCarFuel = RequestBody.create(MediaType.parse("text/plain"), car_fuel);
        RequestBody rbAc = RequestBody.create(MediaType.parse("text/plain"), acType);
        RequestBody rbSeats = RequestBody.create(MediaType.parse("text/plain"), carSeats);
        RequestBody rbLuggage = RequestBody.create(MediaType.parse("text/plain"), carLuggage);
        RequestBody rbPrice= RequestBody.create(MediaType.parse("text/plain"), carPrice);
        RequestBody rbTc= RequestBody.create(MediaType.parse("text/plain"), carTc);
        RequestBody rbInclusion = RequestBody.create(MediaType.parse("text/plain"), carInclusion);
        RequestBody rbExclusion = RequestBody.create(MediaType.parse("text/plain"), carExclusion);
        RequestBody rbStatus = RequestBody.create(MediaType.parse("text/plain"), "2");

        MultipartBody.Part carImagePart = null;
        if (uploadImg != null) {
            carImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.CAR_IMG, uploadImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), uploadImg));
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.insert_car(rbUserId,rbCarId,rbStatus, rbTc, rbExclusion,
                rbInclusion,rbBranch,rbCarName,rbDriverType,
                rbCarNumber, rbCarFuel, rbSeats,rbPrice,rbAc,rbLuggage,rbService, carImagePart);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            showError(response.body().getMessage());
                            getOnBackPressedDispatcher().onBackPressed();

                        } else {
                        }
                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showError("something went wrong");


            }
        });
    }

    private void imagePickerDialog() {
        dialog = new Dialog(AddCarActivity.this, R.style.my_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.image_selection_dialog);
        dialog.show();

        ImageView ivCamera = dialog.findViewById(R.id.ivCamera);
        ImageView ivGallery = dialog.findViewById(R.id.ivGallery);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        ivCamera.setOnClickListener(view -> checkCameraPermission());
        ivGallery.setOnClickListener(view -> checkGalleryPermission());
        tvCancel.setOnClickListener(view -> dialog.dismiss());
    }

    private void checkCameraPermission() {

        if (dialog != null) {
            dialog.dismiss();
        }

        if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            openCamera();
        } else {
            openCamera();
        }
    }

    ///////////////////////// check gallery permission///////////
    private void checkGalleryPermission() {
        if (dialog != null) {
            dialog.dismiss();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
                openGallery();
            } else {
                openGallery();
            }
        }
    }

    /////////////////// for open camera /////////////////////
    private void openCamera() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat(Constant.yyyyMMdd_HHmmss, Locale.getDefault()).format(new java.util.Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        try {
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", AddCarActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(AddCarActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra(Constant.FILENAME, imageFileName);
            cameraActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // Set the MIME type to allow any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            galleryActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            launchImageCropper();
        }
    });


    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // File selected successfully
            imageUri = result.getData().getData();
            launchImageCropper();
            // Now you can use the selectedFileUri as needed
        }
    });


    private void launchImageCropper() {
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.imageSourceIncludeGallery = false;
        cropImageOptions.imageSourceIncludeCamera = true;
        cropImageOptions.outputCompressQuality = 60;
        cropImageOptions.aspectRatioX = 1;
        cropImageOptions.aspectRatioY = 1;
        cropImageOptions.fixAspectRatio = true;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(imageUri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(AddCarActivity.this, true);

            uploadImg = new File(croppedImagePath);
            binding.tvCarImg.setText(getImageName(croppedImagePath));

        }
    });

    private String getImageName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }


    private boolean validate() {
        boolean valid = true;
        if (binding.etCarNumber.getText().toString().isEmpty()) {
            binding.etCarNumber.setError("Please enter your car number..!");
            valid = false;
        } else {
            binding.etCarNumber.setError(null);
        }

        if (binding.etSeats.getText().toString().isEmpty()) {
            binding.etSeats.setError("Please enter number seats..!");
            valid = false;
        } else {
            binding.etSeats.setError(null);
        }

        if (binding.spFuelType.getSelectedItemPosition() < 1) {
            Toast.makeText(this, "Please Choose Your Fuel Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (binding.spBranch.getSelectedItemPosition() < 1) {
            Toast.makeText(this, "Please Select Branch", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        if (uploadImg == null) {
//            Toast.makeText(this, "Please Upload Your Car Image", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }


        return valid;
    }


}