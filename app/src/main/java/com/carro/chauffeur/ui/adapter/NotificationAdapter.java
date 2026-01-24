package com.carro.chauffeur.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carro.chauffeur.R;
import com.carro.chauffeur.databinding.NotificationItemBinding;
import com.carro.chauffeur.listener.NotificationClickListener;
import com.carro.chauffeur.model.NotificationModel;
import com.carro.chauffeur.utils.Constant;
import com.carro.chauffeur.utils.DateFormater;
import com.carro.chauffeur.utils.ImagePathDecider;
import com.carro.chauffeur.utils.Utils;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<NotificationModel> items;
    NotificationClickListener notificationClickListener;

    public NotificationAdapter(Context context, List<NotificationModel> items, NotificationClickListener notificationClickListener) {
        this.context = context;
        this.items = items;
        this.notificationClickListener = notificationClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding binding = NotificationItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel item = items.get(holder.getAdapterPosition());
        holder.binding.tvMsg.setText(item.getmNotifMessage());
        holder.binding.tvTime.setText(Utils.formatTimeString(Constant.HHMMSS,Constant.HHMMSSA,item.getmNotifTime()));
        holder.binding.tvDate.setText(DateFormater.changeDateFormat(Constant.yyyyMMdd,Constant.ddMMyyyy,item.getmNotifDate()));

        if (position != 0) {
            String prevDate = items.get(position - 1).getmNotifDate();
            if (prevDate.equals(item.getmNotifDate())) {
                holder.binding.tvDate.setVisibility(View.GONE);
            } else {
                holder.binding.tvDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.binding.tvDate.setVisibility(View.VISIBLE);
        }

        if (item.getmNotifBooking() != null && !item.getmNotifBooking().isEmpty() &&
                (item.getmBkingVendor() == null || item.getmBkingVendor().isEmpty())) {
            holder.binding.tvAccept.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvAccept.setVisibility(View.GONE);
        }


        holder.binding.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationClickListener.onNotifClick(item);
            }
        });

        Glide.with(context)
                .load(ImagePathDecider.getNotificationImagePath()+item.getmNotifImage())
                .error(R.drawable.img_no_profile)
                .into(holder.binding.ivNotification);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;

        public ViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
