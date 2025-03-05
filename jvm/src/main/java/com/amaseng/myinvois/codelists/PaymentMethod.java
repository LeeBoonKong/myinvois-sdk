package com.amaseng.myinvois.codelists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("01", "Cash"),
    CHEQUE("02", "Cheque"),
    BANK_TRANSFER("03", "Bank Transfer"),
    CREDIT_CARD("04", "Credit Card"),
    DEBIT_CARD("05", "Debit Card"),
    E_WALLET("06", "e-Wallet / Digital Wallet"),
    DIGITAL_BANK("07", "Digital Bank"),
    OTHERS("08", "Others");

    private final String code;
    private final String description;
}