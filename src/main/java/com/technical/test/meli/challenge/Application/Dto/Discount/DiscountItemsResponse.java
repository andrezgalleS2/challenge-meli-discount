package com.technical.test.meli.challenge.Application.Dto.Discount;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DiscountItemsResponse {
    ArrayList<Object> items;
    private String code;
    private String message;
    private String error;
}
