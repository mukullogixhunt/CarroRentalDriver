package com.carro.chauffeur.listener;

import com.carro.chauffeur.model.BookingListModel;

public interface BookingClickListener {
    void onSeeDetailsClick(BookingListModel bookingListModel);
    void onCallClick(BookingListModel bookingListModel);
    void onMsgClick(BookingListModel bookingListModel);
}
