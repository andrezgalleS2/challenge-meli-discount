package com.technical.test.meli.challenge.dto.discount;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DiscountItemsResponse {
    ArrayList<Object> items;
    private Number code;
    private String message;
    private String error;
}
