package com.fiap.tech.challenge.global.util.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatePatternEnum {

    DATE_FORMAT_yyyyMMdd ("yyyyMMdd"),
    DATE_FORMAT_yyyy_MM_dd ("yyyy-MM-dd"),
    DATE_FORMAT_yyyy_MM_dd_Combined ("yyyyMMddHHmm"),
    DATE_FORMAT_yyyy_MM_dd_HH_mm ("yyyy-MM-dd HH:mm"),
    DATE_FORMAT_dd_MM_yyyy ("dd-MM-yyyy"),
    DATE_FORMAT_dd_MM_yyyy_WITH_DOT ("dd.MM.yyyy"),
    DATE_FORMAT_mm_dd_yyyy_WITH_SLASH ("MM/dd/yyyy"),
    DATE_FORMAT_m_d_yyyy_WITH_SLASH ("M/d/yyyy"),
    DATE_FORMAT_dd_mm_yyyy_WITH_SLASH ("dd/MM/yyyy"),
    DATE_FORMAT_dd_MMM ("ddMMM"),
    DATE_FORMAT_dd_MM_yy ("ddMMyy"),
    DATE_FORMAT_dd_MMM_yyyy ("ddMMMyyyy"),
    DATE_FORMAT_dd_MM_yyyy_COMBINED ("ddMMyyyy"),
    DATE_FORMAT_dd_MM_yyyy_HH_mm_ss ("yyyy-MM-dd'T'HH:mm:ss"),
    DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSSXXX("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
    DATE_FORMAT_yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
    DATE_FORMAT_dd_MM_yyyy_HH_mm ("dd-MM-yyyy HH:mm"),
    DATE_FORMAT_HH_mm ("HH:mm"),
    DATE_FORMAT_YYYY_MM_DDTHH_mm_ssZ ("YYYY-MM-DD'T'HH:mm:ssZ");

    private String value;

    DatePatternEnum(String value) {
        this.value = value;
    }
}