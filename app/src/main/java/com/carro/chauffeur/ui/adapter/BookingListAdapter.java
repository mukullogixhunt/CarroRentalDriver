package com.carro.chauffeur.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carro.chauffeur.R;
import com.carro.chauffeur.databinding.BookingItemBinding;
import com.carro.chauffeur.listener.BookingClickListener;
import com.carro.chauffeur.model.BookingListModel;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.DateFormater;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.IndianCurrencyFormat;
import com.carro.chauffeur.utils.Utils;

import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    Context context;
    List<BookingListModel> items;
    BookingClickListener bookingClickListener;
    boolean isHistory;

    public BookingListAdapter(Context context, List<BookingListModel> items,boolean isHistory, BookingClickListener bookingClickListener) {
        this.context = context;
        this.items = items;
        this.isHistory = isHistory;
        this.bookingClickListener = bookingClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingItemBinding binding = BookingItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingListModel item = items.get(holder.getAdapterPosition());
        if(item.getmBkingFrom().isEmpty()){
            holder.binding.tvFromLocation.setText("---");
        }else{
            holder.binding.tvFromLocation.setText(item.getmBkingFrom());
        }

        if(item.getmBkingTo().isEmpty()){
            holder.binding.tvToLocation.setText("---");
        }else{
            holder.binding.tvToLocation.setText(item.getmBkingTo());
        }


        if (item.getmBkingType().equals("4")){
            holder.binding.tvCarType.setText(item.getmBusTitle()+" \n( Bus )");
        }else {
            holder.binding.tvCarType.setText(item.getmCtypeTitle() + "\n(" + item.getmCtypeNumber().toUpperCase() + ")");
        }


        if(isHistory){
            holder.binding.llButtons.setVisibility(View.GONE);
        }else{
            holder.binding.llButtons.setVisibility(View.VISIBLE);

        }

        Glide.with(context)
                .load(ImagePathDecider.getCarImagePath() + item.getmCtypeImg())
                .error(R.drawable.img_no_profile)
                .into(holder.binding.ivCarImage);


        holder.binding.tvCustomerName.setText(item.getmCustName());
        holder.binding.tvBookingId.setText("Booking Id : " + item.getmBkingId());
        holder.binding.tvPickupDetails.setText("Pickup : " + item.getmBkingFrom());
        holder.binding.tvAmount.setText(new IndianCurrencyFormat().inCuFormatText(item.getmBkingTotal()));

        String pick_date;
        String pick_time;
        pick_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
        pick_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
        holder.binding.tvFromDate.setText(pick_date + " " + pick_time);
        holder.binding.tvTripTime.setText(pick_date + " " + pick_time);

        String drop_date;
        String drop_time;
        drop_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingReturn());
        drop_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingReturnAt());
        holder.binding.tvToDate.setText(drop_date + " " + drop_time);

        if (item.getmBkingStatus().equals("1")) {
            holder.binding.btnAccept.setVisibility(View.VISIBLE);
            holder.binding.btnComplete.setVisibility(View.GONE);
            holder.binding.btnStatus.setText("Pending");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_dark));
        } else {
            holder.binding.btnAccept.setVisibility(View.GONE);
            holder.binding.btnComplete.setVisibility(View.VISIBLE);
            holder.binding.btnStatus.setText("Accepted");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green2));
        }

        switch (item.getmBkingType()) {
            case "1":
                if (!item.getmBkingRoadType().isEmpty()) {
                    switch (item.getmBkingRoadType()) {
                        case "1":
                            holder.binding.tvTripType.setText("One Way Trip");
                            break;
                        case "2":
                            holder.binding.tvTripType.setText("Round Trip");
                            break;
                        case "3":
                            holder.binding.tvTripType.setText("Hourly Trip");
                            break;
                        case "4":
                            holder.binding.tvTripType.setText("Airport");
                            break;
                    }
                }
                break;

            case "2":
                holder.binding.tvTripType.setText("Self Drive Service");

                break;
            case "3":
                holder.binding.tvTripType.setText("Luxury Cars Service");
                holder.binding.ivView.setVisibility(View.GONE);
                holder.binding.ivLastDn.setVisibility(View.GONE);
                holder.binding.ivFirstDn.setVisibility(View.GONE);
                holder.binding.tvToDate.setVisibility(View.GONE);
                holder.binding.tvToLocation.setVisibility(View.GONE);
                break;
            case "4":
                holder.binding.tvTripType.setText("Bus Booking Service");
                holder.binding.ivView.setVisibility(View.GONE);
                holder.binding.ivLastDn.setVisibility(View.GONE);
                holder.binding.ivFirstDn.setVisibility(View.GONE);
                holder.binding.tvToDate.setVisibility(View.GONE);
                holder.binding.tvToLocation.setVisibility(View.GONE);
                break;
        }


        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"accept");
            }
        });
        holder.binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"cancel");
            }
        });
        holder.binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"complete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BookingItemBinding binding;

        public ViewHolder(BookingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
