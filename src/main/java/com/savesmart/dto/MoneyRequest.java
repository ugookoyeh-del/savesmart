package com.savesmart.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record MoneyRequest(
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @Size(max = 250) String note
) {}
