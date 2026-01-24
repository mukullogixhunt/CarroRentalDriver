package com.carro.chauffeur.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.carro.chauffeur.R;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.NotificationResponse;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.databinding.ActivityNotificationBinding;
import com.carro.chauffeur.listener.NotificationClickListener;
import com.carro.chauffeur.model.NotificationModel;
import com.carro.chauffeur.ui.adapter.NotificationAdapter;
import com.carro.chauffeur.ui.common.BaseActivity;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements NotificationClickListener {

    ActivityNotificationBinding binding;

    NotificationAdapter notificationAdapter;

    List<NotificationModel> notificationModelList = new ArrayList<>();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });        getNotificationPreference();

    }


    private void getNotificationPreference() {
        userId = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, NotificationActivity.this);

        initialization();
    }

    private void initialization() {

        getUserNotificationApi();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });


    }

    private void getUserNotificationApi() {

        showLoader();

        binding.lvNoData.setVisibility(View.VISIBLE);
        binding.rvNotification.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationResponse> call = apiInterface.notification(userId);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvNotification.setVisibility(View.VISIBLE);

                            notificationModelList.clear();
                            notificationModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
                            binding.rvNotification.setLayoutManager(linearLayoutManager);
                            notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModelList,NotificationActivity.this);
                            binding.rvNotification.setAdapter(notificationAdapter);

                            notificationAdapter.notifyDataSetChanged();

                        } else {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.VISIBLE);
                            binding.rvNotification.setVisibility(View.GONE);
                        }
                    } else {
                        hideLoader();
                        binding.lvNoData.setVisibility(View.VISIBLE);
                        binding.rvNotification.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    binding.rvNotification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                binding.lvNoData.setVisibility(View.VISIBLE);
                binding.rvNotification.setVisibility(View.GONE);
                showError("Something went wrong");
            }
        });


    }
    private void getAcceptNotificationApi(String booking_id) {

        showLoader();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_notify_booking(userId,booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            getUserNotificationApi();
                            showAlert("Data Updated Successfully!");


                        } else {
                            hideLoader();

                        }
                    } else {
                        hideLoader();
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });


    }

    @Override
    public void onNotifClick(NotificationModel notificationModel) {
        getAcceptNotificationApi(notificationModel.getmNotifBooking());
    }
}