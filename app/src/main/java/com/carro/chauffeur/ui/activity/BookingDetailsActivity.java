package com.carro.chauffeur.ui.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.carro.chauffeur.R;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.BookingDetailResponse;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.databinding.ActivityBookingDetailsBinding;
import com.carro.chauffeur.databinding.CustomerReviewDialogBinding;
import com.carro.chauffeur.model.BookingListModel;
import com.carro.chauffeur.ui.common.BaseActivity;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.DateFormater;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.IndianCurrencyFormat;
import com.carro.chauffeur.utils.LocationUtil;
import com.carro.chauffeur.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends BaseActivity {
    ActivityBookingDetailsBinding binding;

    Double currentLat;
    Double currentLng;
    private Uri tollFileUri = null;
    private Uri parkingFileUri = null;
    private Uri otherFileUri = null;

    private static final int REQ_TOLL = 101;
    private static final int REQ_PARKING = 102;
    private static final int REQ_OTHER = 103;
    CustomerReviewDialogBinding customerReviewBinding;
    BottomSheetDialog customerReviewDialog;
    String bKingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookingDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
       customerReviewBinding = CustomerReviewDialogBinding.inflate(getLayoutInflater());
        customerReviewDialog= new BottomSheetDialog(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LocationUtil.getCurrentLocation(BookingDetailsActivity.this, new LocationUtil.LocationCallback() {
            @Override
            public void onLocationReceived(double lat, double lng) {
                currentLat = lat;
                currentLng = lng;
            }

            @Override
            public void onLocationError(String error) {
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        String bookingId = getIntent().getStringExtra("BOOKING_ID");
        if (bookingId != null) {
            getBookingDetails(bookingId);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (requestCode == REQ_TOLL) {
                tollFileUri = uri;
                binding.btnUploadToll.setText("Toll Receipt Selected");
            } else if (requestCode == REQ_PARKING) {
                parkingFileUri = uri;
                binding.btnUploadParking.setText("Parking File Selected");
            } else if (requestCode == REQ_OTHER) {
                otherFileUri = uri;
                binding.btnUploadOther.setText("Other File Selected");
            }
        }
    }

    private void getBookingDetails(String bookingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BookingDetailResponse> call = apiInterface.getBookingDetails(bookingId);
        call.enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookingDetailResponse> call, @NonNull Response<BookingDetailResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        BookingListModel item = response.body().getData().get(0);
                        setTripData(item);
                        bKingId=item.getmBkingId();
                        if (item.getmBkingFrom().isEmpty()) {
                            binding.tvFromLocation.setText("---");
                        } else {
                            binding.tvFromLocation.setText(item.getmBkingFrom());
                        }

                        if (item.getmBkingTo().isEmpty()) {
                            binding.tvToLocation.setText("---");
                        } else {
                            binding.tvToLocation.setText(item.getmBkingTo());
                        }
                        switch (item.getmBkingStatus()) {

                            case "2": // Accepted
                                binding.btnReachedPickupLocation.setVisibility(VISIBLE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(VISIBLE);
                                binding.tvNavigationBtn.setText("Navigate to Pickup Location");
                                binding.cardTripCharges.setVisibility(GONE);
                                break;

                            case "5": // Reached Pickup Location
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(VISIBLE);
                                binding.btnNavigate.setVisibility(VISIBLE);
                                binding.tvNavigationBtn.setText("Navigate to Drop Location");
                                binding.cardTripCharges.setVisibility(GONE);
                                break;

                            case "6": // Reached Destination
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(VISIBLE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                binding.cardTripCharges.setVisibility(GONE);
                                binding.cardTripCharges.setVisibility(GONE);
                                break;

                            case "3": // Completed
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                binding.cardTripCharges.setVisibility(VISIBLE);
                                /**
                                 * 0-> not given review
                                 * 1-> skip review
                                 * 2-> given review
                                 */
                                if(item.getmBkingOStatus().equals("0")) {
                                    showCustomerReviewBottomDialog(item.getmBkingId(), item.getmBkingUser());
                                }
                                break;

                            case "4": // Cancelled
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                binding.cardTripCharges.setVisibility(GONE);
                                break;

                            default:
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                binding.cardTripCharges.setVisibility(GONE);
                                break;
                        }

                        if (item.getmBkingType().equals("4")) {
                            binding.tvCarType.setText(item.getmBusTitle() + " \n( Bus )");
                        } else {
                            binding.tvCarType.setText(item.getmCtypeTitle() + "\n(" + item.getmCtypeNumber().toUpperCase() + ")");
                        }

                        Glide.with(BookingDetailsActivity.this)
                                .load(ImagePathDecider.getCarImagePath() + item.getmCtypeImg())
                                .error(R.drawable.img_no_profile)
                                .into(binding.ivCarImage);

                        binding.tvCustomerName.setText(item.getmCustName());
                        binding.tvBookingId.setText("Booking Id : " + item.getmBkingId());
                        binding.tvPickupDetails.setText("Pickup : " + item.getmBkingFrom());
                        binding.tvAmount.setText(
                                new IndianCurrencyFormat().inCuFormatText(
                                        item.getmBkingTotal() == null || item.getmBkingTotal().isEmpty()
                                                ? "0"
                                                : (Double.parseDouble(item.getmBkingTotal()) * 0.8)+""
                                )
                        );

                        String pick_date;
                        String pick_time;
                        pick_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
                        pick_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
                        binding.tvFromDate.setText(pick_date + " " + pick_time);
                        //binding.tvTripTime.setText(pick_date + " " + pick_time);

                        String drop_date;
                        String drop_time;
                        drop_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingReturn());
                        drop_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingReturnAt());
                        binding.tvToDate.setText(drop_date + " " + drop_time);

                        switch (item.getmBkingType()) {
                            case "1":
                                if (!item.getmBkingRoadType().isEmpty()) {
                                    switch (item.getmBkingRoadType()) {
                                        case "1":
                                            binding.tvTripType.setText("One Way Trip");
                                            break;
                                        case "2":
                                            binding.tvTripType.setText("Round Trip");
                                            break;
                                        case "3":
                                            binding.tvTripType.setText("Hourly Trip");
                                            break;
                                        case "4":
                                            binding.tvTripType.setText("Airport");
                                            break;
                                    }
                                }
                                break;

                            case "2":
                                binding.tvTripType.setText("Self Drive Service");

                                break;
                            case "3":
                                binding.tvTripType.setText("Luxury Cars Service");
                                binding.ivView.setVisibility(GONE);
                                binding.ivLastDn.setVisibility(GONE);
                                binding.ivFirstDn.setVisibility(GONE);
                                binding.tvToDate.setVisibility(GONE);
                                binding.tvToLocation.setVisibility(GONE);
                                break;
                            case "4":
                                binding.tvTripType.setText("Bus Booking Service");
                                binding.ivView.setVisibility(GONE);
                                binding.ivLastDn.setVisibility(GONE);
                                binding.ivFirstDn.setVisibility(GONE);
                                binding.tvToDate.setVisibility(GONE);
                                binding.tvToLocation.setVisibility(GONE);
                                break;
                        }
                        if(item.getmBkingOStatus().equals("0")){
                            binding.ivCall.setVisibility(VISIBLE);
                            binding.ivMsg.setVisibility(VISIBLE);
                        }else{
                            binding.ivCall.setVisibility(GONE);
                            binding.ivMsg.setVisibility(GONE);
                        }
                        binding.ivCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.openDialer(BookingDetailsActivity.this, item.getmCustMobile());
                            }
                        });
                        binding.ivMsg.setOnClickListener(v -> {
                            Utils.openSms(BookingDetailsActivity.this, item.getmCustMobile(), "Hi," + item.getmCustName());
                        });
                        binding.btnNavigate.setOnClickListener(v -> {
                            if (item.getmBkingStatus().equals("2")) {
                                //navigate to pickup loc
                                Utils.openRouteInGoogleMaps(BookingDetailsActivity.this,
                                        currentLat + "",
                                        currentLng + "",
                                        item.getmCustCurLocLat(),
                                        item.getmCustCurLocLng());
                            }
                            if (item.getmBkingStatus().equals("5")) {
                                //navigate to drop loc
                                Utils.openRouteInGoogleMaps(BookingDetailsActivity.this,
                                        item.getmCustCurLocLat(),
                                        item.getmCustCurLocLng(),
                                        item.getmCustCurLocLat(),
                                        item.getmCustCurLocLng());
                            }
                        });

                        binding.btnReachedPickupLocation.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                updateBookingStatus(item.getmBkingId(),"5");
                            }
                        });
                        binding.btnReachedDestinationLocation.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                updateBookingStatus(item.getmBkingId(),"6");
                            }
                        });
                        binding.btnRideCompleted.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                showRideCompleteConfirmation(item.getmBkingId(),view);
                                //completeBookingAPi(item.getmBkingId());
                            }
                        });
                    } else {
                        showError(response.message());
                    }
                } catch (Exception e) {
                    showError("An error occurred.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingDetailResponse> call, @NonNull Throwable t) {
                showError("Something went wrong");
            }
        });
    }
    private void showRideCompleteConfirmation(String bookingId, SlideToActView slideView) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Complete Ride")
                .setMessage("Are you sure you want to complete this ride?")
                .setCancelable(false)
                .setPositiveButton("Yes, Complete", (dialog, which) -> {
                    updateBookingStatus(bookingId,"3");
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    slideView.resetSlider();
                })
                .show();
    }

    private void updateBookingStatus(String bookingId,String bookingStatusCode) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.updateBookingStatus(bookingId,bookingStatusCode);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            getBookingDetails(bookingId);
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

            }
        });
    }

    private void updateCustomerReview(String bookingId,String customerBkingId,String rating, String remark,String bookNext,String bookingDate,String reviewType) {
        /**
         *  reviewType
         *  1-> for given
         *  2-> for skip review
         */
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.updateCustomerReview(bookingId,customerBkingId,rating,remark,bookNext,bookingDate,reviewType);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            getBookingDetails(bookingId);
                            customerReviewDialog.dismiss();
                        } else {

                        }
                    } else {
                        customerReviewDialog.dismiss();
                    }

                } catch (Exception e) {
                    customerReviewDialog.dismiss();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                customerReviewDialog.dismiss();
            }
        });
    }
    private void showCustomerReviewBottomDialog(String bookingId,String customerBKingId) {
        customerReviewDialog.setContentView(customerReviewBinding.getRoot());
        customerReviewDialog.setCancelable(false);
        customerReviewDialog.show();
        customerReviewBinding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            customerReviewBinding.tvRatingValue.setText(rating + "/5");
        });

        customerReviewBinding.rbYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                customerReviewBinding.layoutDateTime.setVisibility(View.VISIBLE);
            }
        });

        customerReviewBinding.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                customerReviewBinding.layoutDateTime.setVisibility(View.GONE);
            }
        });
        customerReviewBinding.tvSelectDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        customerReviewBinding.tvSelectDate.setText(formattedDate);
                    },
                    2024, 0, 1 // default date
            );
            datePickerDialog.show();
        });
        customerReviewBinding.btnSkip.setOnClickListener(v ->
                updateCustomerReview(bookingId,customerBKingId,"","","","","2")
        );
        customerReviewBinding.btnSubmit.setOnClickListener(v -> {
            float rating = customerReviewBinding.ratingBar.getRating();
            String remarks = customerReviewBinding.etRemarks.getText().toString().trim();
            boolean wantAgain = customerReviewBinding.rbYes.isChecked();
            String date = customerReviewBinding.tvSelectDate.getText().toString();
           updateCustomerReview(bookingId,customerBKingId,rating+"",remarks,wantAgain?"1":"0",date,"1");
        });
    }
    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES,
                new String[]{"application/pdf", "image/*"});
        startActivityForResult(intent, requestCode);
    }

    private void submitTripCharges(String bkingId) {

        String bookingId = bkingId;

        // Meal Provided
        ArrayList<String> meals = new ArrayList<>();
        if (binding.cbTea.isChecked()) meals.add("Tea");
        if (binding.cbLunch.isChecked()) meals.add("Lunch");
        if (binding.cbDinner.isChecked()) meals.add("Dinner");
        String mealProvided = TextUtils.join(",", meals);

        // Toll
        String toll = binding.rbTollYes.isChecked() ? "Yes" : "No";

        // Parking
        String parking = binding.rbParkingYes.isChecked() ? "Yes" : "No";

        // Other (skip allowed)
        String otherTitle = binding.etOtherChargeTitle.getText().toString().trim();

        MultipartBody.Part tollImg =
                toll.equals("Yes") ? Utils.prepareFilePart("toll_tax_img", tollFileUri,BookingDetailsActivity.this) : null;

        MultipartBody.Part parkingImg =
                parking.equals("Yes") ? Utils.prepareFilePart("parking_img", parkingFileUri,BookingDetailsActivity.this) : null;

        MultipartBody.Part otherImg =
                Utils.prepareFilePart("other_img", otherFileUri,BookingDetailsActivity.this);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = api.updateTripCharge(
                Utils.toRequestBody(bookingId),
                Utils.toRequestBody(mealProvided),
                Utils.toRequestBody(toll),
                tollImg,
                Utils.toRequestBody(parking),
                parkingImg,
                Utils.toRequestBody(otherTitle),
                otherImg
        );

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() != null &&
                        response.body().getResult().equals(Constant.SUCCESS_RESPONSE)) {

                    showAlert("Trip charges submitted");
                    getBookingDetails(bookingId);

                } else {
                    showAlert("Submission failed");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
               showAlert("Something went wrong");
            }
        });
    }
    private boolean validateTripCharges() {
        if (binding.rbTollYes.isChecked() && tollFileUri == null) {
            showAlert("Please upload Toll receipt (PDF)");
            return false;
        }
        if (binding.rbParkingYes.isChecked() && parkingFileUri == null) {
            showAlert("Please upload Parking receipt");
            return false;
        }
        String otherTitle = binding.etOtherChargeTitle.getText().toString().trim();
        if (!otherTitle.isEmpty() && otherFileUri == null) {
            showAlert("Please upload document for Other Charges");
            return false;
        }
        if (otherTitle.isEmpty() && otherFileUri != null) {
            showAlert("Please enter title for Other Charges");
            return false;
        }
        return true;
    }


    private void setTripData(BookingListModel item) {
        binding.cbTea.setChecked("Tea".equalsIgnoreCase(item.getmBkingMealProvide()));
        binding.cbLunch.setChecked("Lunch".equalsIgnoreCase(item.getmBkingMealProvide()));
        binding.cbDinner.setChecked("Dinner".equalsIgnoreCase(item.getmBkingMealProvide()));

        boolean tollUploaded = !TextUtils.isEmpty(item.getmBkingTollTaxImg());

        if ("Yes".equalsIgnoreCase(item.getmBkingTollTax())) {
            binding.rbTollYes.setChecked(true);
        } else {
            binding.rbTollNo.setChecked(true);
        }

        binding.btnUploadToll.setVisibility(tollUploaded ? View.GONE : View.VISIBLE);
        binding.btnViewToll.setVisibility(tollUploaded ? View.VISIBLE : View.GONE);

        if (tollUploaded) {
            String tollUrl = ImagePathDecider.getBookingImgPath() + item.getmBkingTollTaxImg();
            binding.btnViewToll.setOnClickListener(v ->
                    Utils.openInBrowser(tollUrl, BookingDetailsActivity.this));
        }
        boolean parkingUploaded = !TextUtils.isEmpty(item.getmBkingParkingImg());

        if ("Yes".equalsIgnoreCase(item.getmBkingParking())) {
            binding.rbParkingYes.setChecked(true);
        } else {
            binding.rbParkingNo.setChecked(true);
        }

        binding.btnUploadParking.setVisibility(parkingUploaded ? View.GONE : View.VISIBLE);
        binding.btnViewParking.setVisibility(parkingUploaded ? View.VISIBLE : View.GONE);

        if (parkingUploaded) {
            String parkingUrl = ImagePathDecider.getBookingImgPath() + item.getmBkingParkingImg();
            binding.btnViewParking.setOnClickListener(v ->
                    Utils.openInBrowser(parkingUrl, BookingDetailsActivity.this));
        }
        boolean otherUploaded = !TextUtils.isEmpty(item.getmBkingOtherImg());

        if (!TextUtils.isEmpty(item.getmBkingOtherTitle())) {
            binding.etOtherChargeTitle.setText(item.getmBkingOtherTitle());
            binding.etOtherChargeTitle.setEnabled(false);
            binding.etOtherChargeTitle.setFocusable(false);
            binding.etOtherChargeTitle.setClickable(false);
        } else {
            binding.etOtherChargeTitle.setEnabled(true);
            binding.etOtherChargeTitle.setFocusable(true);
            binding.etOtherChargeTitle.setFocusableInTouchMode(true);
        }

        binding.btnUploadOther.setVisibility(otherUploaded ? View.GONE : View.VISIBLE);
        binding.btnViewOther.setVisibility(otherUploaded ? View.VISIBLE : View.GONE);

        if (otherUploaded) {
            String otherUrl = ImagePathDecider.getBookingImgPath() + item.getmBkingOtherImg();
            binding.btnViewOther.setOnClickListener(v ->
                    Utils.openInBrowser(otherUrl, BookingDetailsActivity.this));
        }

        boolean alreadySubmitted = tollUploaded || parkingUploaded || otherUploaded;
        binding.btnSubmitCharges.setVisibility(alreadySubmitted ? View.GONE : View.VISIBLE);

        binding.rgTollTax.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTollYes && !tollUploaded) {
                binding.btnUploadToll.setVisibility(View.VISIBLE);
            } else {
                binding.btnUploadToll.setVisibility(View.GONE);
                tollFileUri = null;
            }
        });

        binding.rgParking.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbParkingYes && !parkingUploaded) {
                binding.btnUploadParking.setVisibility(View.VISIBLE);
            } else {
                binding.btnUploadParking.setVisibility(View.GONE);
                parkingFileUri = null;
            }
        });

        binding.btnUploadToll.setOnClickListener(v -> openFilePicker(REQ_TOLL));
        binding.btnUploadParking.setOnClickListener(v -> openFilePicker(REQ_PARKING));
        binding.btnUploadOther.setOnClickListener(v -> openFilePicker(REQ_OTHER));

        binding.btnSubmitCharges.setOnClickListener(v -> {
            if (validateTripCharges()) {
                submitTripCharges(bKingId);
            }
        });
    }


}