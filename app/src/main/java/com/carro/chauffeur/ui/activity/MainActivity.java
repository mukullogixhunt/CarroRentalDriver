package com.carro.chauffeur.ui.activity;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.HomePageResponse;
import com.carro.chauffeur.model.HomePageModel;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.carro.chauffeur.R;
import com.carro.chauffeur.databinding.ActivityMainBinding;
import com.carro.chauffeur.databinding.LogoutBottomDialogBinding;
import com.carro.chauffeur.model.LoginModel;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static NavController bottomNavController;
    Dialog dialog;
    LoginModel loginModel = new LoginModel();
    private List<HomePageModel> homePageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        getUserPreferences();

    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, MainActivity.this);
        loginModel = new Gson().fromJson(userData, LoginModel.class);


        initialization();
    }
    private void getHomeImage() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<HomePageResponse> call = apiService.home_page();
        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            homePageList.clear();
                            homePageList.addAll(response.body().getData());

                            if (response.body().getData().get(0).getmHcAdvImgSh().equalsIgnoreCase("1") && Utils.isAdvShow) {
                                showAdvertiseDialog(MainActivity.this, homePageList.get(0).getmHcAdvImg());
                            }

                        } else {


                        }
                    } else {

                    }
                } catch (Exception e) {
//                    log_e(this.getClass().getSimpleName(), "onResponse: ", e);

                }

            }

            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failure", t.toString());

//                showError("Something went wrong");
            }
        });
    }
    public static void showAdvertiseDialog(
            Context context,
            String item
    ) {
        Utils.isAdvShow = false;
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_advertise);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        ImageView imgAdvertise = dialog.findViewById(R.id.imgAdvertise);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);

        // Load image from m_adv_image
        Glide.with(context)
                .load(ImagePathDecider.getAdvertisementPath() + item)
                .placeholder(android.R.color.darker_gray)
                .dontTransform()
                .override(Target.SIZE_ORIGINAL)
                .into(imgAdvertise);
        // Close dialog
        imgClose.setOnClickListener(v -> dialog.dismiss());

        // Optional: click on image (open web link)
        imgAdvertise.setOnClickListener(v -> {

        });

        dialog.show();
    }

    private void initialization() {
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_nav_fragment);
        assert navHost != null;
        bottomNavController = navHost.getNavController();
        NavigationUI.setupWithNavController(binding.navBottom, bottomNavController);

        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBottomDialog();
            }
        });
        binding.ivNofification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
        getHomeImage();
    }

    public void navigateToFragment(int id) {
        bottomNavController.navigate(id);
    }

    public void navigateToFragment(int id, Bundle bundle) {
        bottomNavController.navigate(id, bundle);
    }

    public void removeFromBackStack(int fragmentCard) {
        bottomNavController.popBackStack(fragmentCard, true);
    }


    private void logoutBottomDialog() {
        LogoutBottomDialogBinding logoutBottomDialogBinding;
        logoutBottomDialogBinding = LogoutBottomDialogBinding.inflate(getLayoutInflater());

        dialog = new BottomSheetDialog(MainActivity.this);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(logoutBottomDialogBinding.getRoot());
        dialog.show();

        logoutBottomDialogBinding.ivCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        logoutBottomDialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logoutBottomDialogBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PreferenceUtils.setBoolean(Constant.PreferenceConstant.IS_LOGIN, false, MainActivity.this);
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}