package com.technical.test.meli.challenge.adapter;

import com.technical.test.meli.challenge.dto.categories.CategoriesResponse;

public interface IExternalGetCategories {
    public CategoriesResponse getCategories(String id);
}
