package com.carro.chauffeur.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.RecommendedModel;

import java.util.List;

public class RecommendedResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<RecommendedModel> data;

    public List<RecommendedModel> getData() {
        return data;
    }

    public void setData(List<RecommendedModel> data) {
        this.data = data;
    }
}
