package com.tech.picpay.challenge.client.dto;

public record CheckAuthorizeDTO(String status, DataDTO data) {
    public boolean getAuthorization() {
        return data.authorization();
    }
}

record DataDTO(boolean authorization) {
}
