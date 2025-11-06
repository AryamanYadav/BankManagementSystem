package com.Aryaman.BankManagementSystem.Payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public class ApiResponse {
    public ApiResponse(String message) {
        this.message = message;
    }

    private String message;
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}

