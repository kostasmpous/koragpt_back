package com.koragpt.koragpt.models.enums;


import com.fasterxml.jackson.annotation.JsonValue;

public enum Models {
    GPT_4O_MINI("gpt-4o-mini"),
    GPT_4_1_MINI("gpt-4.1-mini"),
    GPT_4_1_MINI_HIGH("gpt-4.1-mini-high"),
    GPT_3_5_TURBO("gpt-3.5-turbo"); // example

    private final String value;

    Models(String value) {
        this.value = value;
    }

    @JsonValue  // ensures JSON uses the actual API string
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
