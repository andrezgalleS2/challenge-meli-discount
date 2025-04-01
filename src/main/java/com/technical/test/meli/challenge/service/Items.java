package com.technical.test.meli.challenge.service;

import com.technical.test.meli.challenge.dto.discount.DiscountItemsResponse;

public interface Items {
    public DiscountItemsResponse getItemsWithDiscount(String ids);
}
