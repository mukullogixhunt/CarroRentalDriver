package com.carro.chauffeur.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.LoginModel;

import java.util.List;

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<LoginModel> data;

    public List<LoginModel> getData() {
        return data;
    }

    public void setData(List<LoginModel> data) {
        this.data = data;
    }
}
