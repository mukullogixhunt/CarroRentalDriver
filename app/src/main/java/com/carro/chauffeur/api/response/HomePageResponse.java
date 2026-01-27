package com.carro.chauffeur.api.response;

import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.HomePageModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<HomePageModel> data;

    public List<HomePageModel> getData() {
        return data;
    }

    public void setData(List<HomePageModel> data) {
        this.data = data;
    }
}
