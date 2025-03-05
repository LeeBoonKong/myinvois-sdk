package com.amaseng.myinvois.codelists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxType {
    SALES_TAX("01", "Sales Tax"),
    SERVICE_TAX("02", "Service Tax"),
    TOURISM_TAX("03", "Tourism Tax"),
    HIGH_VALUE_GOODS_TAX("04", "High-Value Goods Tax"),
    SALES_TAX_LOW_VALUE_GOODS("05", "Sales Tax on Low Value Goods"),
    NOT_APPLICABLE("06", "Not Applicable"),
    TAX_EXEMPTION("E", "Tax exemption (where applicable)");

    private final String code;
    private final String description;
}