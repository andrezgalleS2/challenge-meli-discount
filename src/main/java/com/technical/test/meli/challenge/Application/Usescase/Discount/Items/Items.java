package com.technical.test.meli.challenge.Application.Usescase.Discount.Items;

import com.technical.test.meli.challenge.Application.Dto.Discount.DiscountItemsResponse;

import java.text.ParseException;
import java.util.List;

public interface Items {
    public DiscountItemsResponse getItemsWithDiscount(String ids);
}
