package com.indiancosmeticsbd.app.Model.SignIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserValidate {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errorno")
    @Expose
    private String errorno;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content")
    @Expose
    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorno() {
        return errorno;
    }

    public void setErrorno(String errorno) {
        this.errorno = errorno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public class Content {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("token")
        @Expose
        private String token;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

}
