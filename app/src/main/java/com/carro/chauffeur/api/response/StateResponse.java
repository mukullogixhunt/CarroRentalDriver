package com.carro.chauffeur.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.StateModel;

import java.util.List;

public class StateResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<StateModel> data;

    public List<StateModel> getData() {
        return data;
    }

    public void setData(List<StateModel> data) {
        this.data = data;
    }
}
