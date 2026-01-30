package com.carro.chauffeur.ui.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.carro.chauffeur.model.BookingListModel;
import com.carro.chauffeur.ui.common.BaseActivity;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.DateFormater;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.IndianCurrencyFormat;
import com.carro.chauffeur.utils.LocationUtil;
import com.carro.chauffeur.utils.Utils;
import com.ncorti.slidetoact.SlideToActView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends BaseActivity {
    ActivityBookingDetailsBinding binding;

    Double currentLat;
    Double currentLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookingDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
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

    private void getBookingDetails(String bookingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BookingDetailResponse> call = apiInterface.getBookingDetails(bookingId);
        call.enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookingDetailResponse> call, @NonNull Response<BookingDetailResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        BookingListModel item = response.body().getData().get(0);
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
                                break;

                            case "5": // Reached Pickup Location
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(VISIBLE);
                                binding.btnNavigate.setVisibility(VISIBLE);
                                binding.tvNavigationBtn.setText("Navigate to Drop Location");
                                break;

                            case "6": // Reached Destination
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(VISIBLE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                break;

                            case "3": // Completed
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                break;

                            case "4": // Cancelled
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
                                break;

                            default:
                                binding.btnReachedPickupLocation.setVisibility(GONE);
                                binding.btnRideCompleted.setVisibility(GONE);
                                binding.btnReachedDestinationLocation.setVisibility(GONE);
                                binding.btnNavigate.setVisibility(GONE);
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
                        binding.tvAmount.setText(new IndianCurrencyFormat().inCuFormatText(item.getmBkingTotal()));

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

                        binding.ivCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.openDialer(BookingDetailsActivity.this, item.getmCustMobile());
                            }
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
                        binding.ivMsg.setOnClickListener(v -> {
                            Utils.openSms(BookingDetailsActivity.this, item.getmCustMobile(), "Hi," + item.getmCustName());
                        });
                        binding.btnReachedPickupLocation.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                reachedPickupLocation(item.getmBkingId());
                            }
                        });
                        binding.btnReachedDestinationLocation.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                reachedDropLocation(item.getmBkingId());
                            }
                        });
                        binding.btnRideCompleted.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                            @Override
                            public void onSlideComplete(@NonNull SlideToActView view) {
                                completeBookingAPi(item.getmBkingId());
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

    private void reachedPickupLocation(String bookingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.reachedPickupLocation(bookingId,"5");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        getBookingDetails(bookingId);
                    } else {
                        showError(response.message());
                    }
                } catch (Exception e) {
                    showError("An error occurred.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                showError("Something went wrong");
            }
        });
    }

    private void reachedDropLocation(String bookingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.reachedDropLocation(bookingId,"6");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        getBookingDetails(bookingId);
                    } else {
                        showError(response.message());
                    }
                } catch (Exception e) {
                    showError("An error occurred.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                showError("Something went wrong");
            }
        });
    }

    private void completeBookingAPi(String bookingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.complete_booking(bookingId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            showAlert("Booking Completed Successfully!...");
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

}