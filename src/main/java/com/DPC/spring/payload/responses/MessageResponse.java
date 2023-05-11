package com.DPC.spring.payload.responses;

import lombok.Data;
import lombok.NonNull;

@Data
public class MessageResponse {
    @NonNull
    private String message;
}
