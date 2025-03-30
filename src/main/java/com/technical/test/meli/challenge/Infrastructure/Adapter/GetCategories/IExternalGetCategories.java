package com.technical.test.meli.challenge.Infrastructure.Adapter.GetCategories;

import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponse;

public interface IExternalGetCategories {
    public CategoriesResponse getCategories(String id);
}
