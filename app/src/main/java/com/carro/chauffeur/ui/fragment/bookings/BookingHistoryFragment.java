package com.carro.chauffeur.ui.fragment.bookings;

import android.app.Dialog;
import android.content.DialogInterface;
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

    private void confirmBookingDialog() {
        getCarListApi();
        getDriverListApi();
        confirmBookingDialogBinding = ConfirmBookingDialogBinding.inflate(getLayoutInflater());
        dialog = new Dialog(getContext(), R.style.my_dialog);
        dialog.setContentView(confirmBookingDialogBinding.getRoot());
        dialog.setCancelable(true);
        dialog.create();
        dialog.show();

        confirmBookingDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmBookingDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    updateBookingAPi();
                    dialog.dismiss();
                }
            }
        });
    }

    private void bookingSuccessDialog() {
        BookingSuccessDialogBinding bookingSuccessDialogBinding;
        bookingSuccessDialogBinding = BookingSuccessDialogBinding.inflate(getLayoutInflater());
        dialog1 = new Dialog(getContext(), R.style.my_dialog);
        dialog1.setContentView(bookingSuccessDialogBinding.getRoot());
        dialog1.setCancelable(true);
        dialog1.create();
        dialog1.show();
        bookingSuccessDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        bookingSuccessDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                getBookingHistoryApi();


            }
        });
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
    public void onBookingClick(BookingListModel bookingListModel, String b_status) {
        booking_id = bookingListModel.getmBkingId();
        if (b_status.equals("accept")) {
            if (bookingListModel.getmBkingType().equals("4")) {
                updateBookingAPi();
            } else {
                confirmBookingDialog();
            }
        } else if (b_status.equals("cancel")) {
            cancelBookingAPi();

        }
    }

    private void getCarListApi() {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CarListResponse> call = apiInterface.car(loginModel.getmDriverId());
        call.enqueue(new Callback<CarListResponse>() {
            @Override
            public void onResponse(Call<CarListResponse> call, Response<CarListResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            carListModels.clear();
                            carListModels.add(new CarListModel("Select Car"));
                            carListModels.addAll(response.body().getData());

                            ArrayAdapter<CarListModel> itemAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, carListModels);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            confirmBookingDialogBinding.spCar.setAdapter(itemAdapter);

                            confirmBookingDialogBinding.spCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    car = carListModels.get(position).getmCtypeId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } else {
                            hideLoader();
                            showError(response.message());
                        }
                    } else {
                        hideLoader();
                        showError(response.message());
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CarListResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    private void getDriverListApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DriverListResponse> call = apiInterface.driver(loginModel.getmDriverId());
        call.enqueue(new Callback<DriverListResponse>() {
            @Override
            public void onResponse(Call<DriverListResponse> call, Response<DriverListResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            driverListModels.clear();
                            driverListModels.add(new DriverListModel("Select Driver"));
                            driverListModels.addAll(response.body().getData());

                            ArrayAdapter<DriverListModel> itemAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, driverListModels);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            confirmBookingDialogBinding.spDriver.setAdapter(itemAdapter);

                            confirmBookingDialogBinding.spDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    driver = driverListModels.get(position).getmDriverId();
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
            public void onFailure(Call<DriverListResponse> call, Throwable t) {

                Log.e("Failure", t.toString());

            }
        });
    }

    private void updateBookingAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_car_driver(car, driver, booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            bookingSuccessDialog();
                            getBookingHistoryApi();

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

    private void cancelBookingAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.cancel_booking(booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            alertMessage();
                            getBookingHistoryApi();

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

    @Override
    public void onResume() {
        super.onResume();
        getBookingHistoryApi();
    }

    private boolean validate() {
        boolean valid = true;

//        if (confirmBookingDialogBinding.spCar.getSelectedItemPosition() == 0) {  // The first item is considered invalid
//            TextView errorText = (TextView) confirmBookingDialogBinding.spCar.getSelectedView();
//            errorText.setError("");  // Set error on selected item
//            errorText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red)); // Change the color to red
//            errorText.setText("Please select a car"); // Show error message
//            valid = false;
//        }
        if (confirmBookingDialogBinding.spDriver.getSelectedItemPosition() == 0) {  // The first item is considered invalid
            TextView errorText = (TextView) confirmBookingDialogBinding.spDriver.getSelectedView();
            errorText.setError("");  // Set error on selected item
            errorText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red)); // Change the color to red
            errorText.setText("Please select a Driver"); // Show error message
            valid = false;
        }
        return valid;
    }

    private void alertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Booking Cancelled Successfully!...");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}