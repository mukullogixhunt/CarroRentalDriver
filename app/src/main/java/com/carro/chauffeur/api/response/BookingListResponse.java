package com.carro.chauffeur.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.BookingListModel;

import java.util.List;

public class BookingListResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<BookingListModel> data;

    public List<BookingListModel> getData() {
        return data;
    }

    public void setData(List<BookingListModel> data) {
        this.data = data;
    }
}
