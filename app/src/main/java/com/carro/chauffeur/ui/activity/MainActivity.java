package com.carro.chauffeur.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.carro.chauffeur.R;
import com.carro.chauffeur.databinding.ActivityMainBinding;
import com.carro.chauffeur.databinding.LogoutBottomDialogBinding;
import com.carro.chauffeur.model.LoginModel;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static NavController bottomNavController;
    Dialog dialog;
    LoginModel loginModel = new LoginModel();

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