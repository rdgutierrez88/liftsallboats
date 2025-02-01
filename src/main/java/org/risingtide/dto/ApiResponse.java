package org.risingtide.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class ApiResponse {
    @JsonProperty("transactionStatusCode")
    private int transactionStatusCode;
    @JsonProperty("transactionStatusDescription")
    private String transactionStatusDescription;
}
