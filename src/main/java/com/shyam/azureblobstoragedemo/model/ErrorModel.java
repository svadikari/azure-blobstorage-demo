package com.shyam.azureblobstoragedemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * ErrorModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-14T19:51:20.915Z")

public class ErrorModel {
    @JsonProperty("developerMessage")
    private String developerMessage = null;

    @JsonProperty("userMessage")
    private String userMessage = null;

    @JsonProperty("errorCode")
    private String errorCode = null;

    @JsonProperty("moreInfo")
    private String moreInfo = null;

    public ErrorModel(int value, String reasonPhrase, String message) {
        this.errorCode = String.valueOf(value);
        this.userMessage = reasonPhrase;
        this.developerMessage = message;
    }

    public ErrorModel developerMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public ErrorModel userMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public ErrorModel errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorModel moreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
        return this;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorModel errorModel = (ErrorModel) o;
        return Objects.equals(this.developerMessage, errorModel.developerMessage) &&
                Objects.equals(this.userMessage, errorModel.userMessage) &&
                Objects.equals(this.errorCode, errorModel.errorCode) &&
                Objects.equals(this.moreInfo, errorModel.moreInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerMessage, userMessage, errorCode, moreInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorModel {\n");

        sb.append("    developerMessage: ").append(toIndentedString(developerMessage)).append("\n");
        sb.append("    userMessage: ").append(toIndentedString(userMessage)).append("\n");
        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        sb.append("    moreInfo: ").append(toIndentedString(moreInfo)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

