package com.carro.chauffeur.ui.fragment.bookings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.carro.chauffeur.ui.activity.BookingDetailsActivity;
import com.carro.chauffeur.utils.Utils;
import com.google.gson.Gson;
import com.carro.chauffeur.R;
import com.carro.chauffeur.api.ApiClient;
import com.carro.chauffeur.api.ApiInterface;
import com.carro.chauffeur.api.response.BookingListResponse;
import com.carro.chauffeur.api.response.CarListResponse;
import com.carro.chauffeur.api.response.DriverListResponse;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.databinding.BookingSuccessDialogBinding;
import com.carro.chauffeur.databinding.ConfirmBookingDialogBinding;
import com.carro.chauffeur.databinding.FragmentBookingHistoryBinding;
import com.carro.chauffeur.listener.BookingClickListener;
import com.carro.chauffeur.model.BookingListModel;
import com.carro.chauffeur.model.CarListModel;
import com.carro.chauffeur.model.DriverListModel;
import com.carro.chauffeur.model.LoginModel;
import com.carro.chauffeur.ui.adapter.BookingListAdapter;
import com.carro.chauffeur.ui.common.BaseFragment;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingHistoryFragment extends BaseFragment implements BookingClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewBookingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingHistoryFragment newInstance(String param1, String param2) {
        BookingHistoryFragment fragment = new BookingHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentBookingHistoryBinding binding;
    ConfirmBookingDialogBinding confirmBookingDialogBinding;
    Dialog dialog, dialog1;
    LoginModel loginModel = new LoginModel();
    BookingListAdapter bookingListAdapter;
    private List<CarListModel> carListModels = new ArrayList<>();
    private List<DriverListModel> driverListModels = new ArrayList<>();
    List<BookingListModel> bookingListModelList = new ArrayList<>();

    String car = "";
    String driver = "";
    String booking_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingHistoryBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        initialization();
    }

    private void initialization() {
        getBookingHistoryApi();
    }


    @Override
    public void onCallClick(BookingListModel bookingListModel) {
        booking_id = bookingListModel.getmBkingId();
    }

    @Override
    public void onSeeDetailsClick(BookingListModel bookingListModel) {
        booking_id = bookingListModel.getmBkingId();
        Intent intent = new Intent(requireActivity(), BookingDetailsActivity.class);
        intent.putExtra("BOOKING_ID", booking_id);
        startActivity(intent);
    }
    @Override
    public void onMsgClick(BookingListModel bookingListModel) {
        booking_id = bookingListModel.getmBkingId();
    }

    private void getBookingHistoryApi() {

        showLoader();

        binding.lvNoData.setVisibility(View.VISIBLE);
        binding.rvNewBookings.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BookingListResponse> call = apiInterface.booking_history(loginModel.getmDriverId());
        call.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvNewBookings.setVisibility(View.VISIBLE);

                            bookingListModelList.clear();
                            bookingListModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.rvNewBookings.setLayoutManager(linearLayoutManager);
                            bookingListAdapter = new BookingListAdapter(getContext(), bookingListModelList, true, BookingHistoryFragment.this);
                            binding.rvNewBookings.setAdapter(bookingListAdapter);

                        } else {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.VISIBLE);
                            binding.rvNewBookings.setVisibility(View.GONE);
                        }
                    } else {
                        hideLoader();
                        binding.lvNoData.setVisibility(View.VISIBLE);
                        binding.rvNewBookings.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    binding.rvNewBookings.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                binding.lvNoData.setVisibility(View.VISIBLE);
                binding.rvNewBookings.setVisibility(View.GONE);
                showError("Something went wrong");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getBookingHistoryApi();
    }

}