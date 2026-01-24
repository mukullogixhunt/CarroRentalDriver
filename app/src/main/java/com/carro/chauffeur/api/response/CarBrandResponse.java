package com.carro.chauffeur.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.CarBrandModel;

import java.util.List;

public class CarBrandResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<CarBrandModel> data;

    public List<CarBrandModel> getData() {
        return data;
    }

    public void setData(List<CarBrandModel> data) {
        this.data = data;
    }
}
