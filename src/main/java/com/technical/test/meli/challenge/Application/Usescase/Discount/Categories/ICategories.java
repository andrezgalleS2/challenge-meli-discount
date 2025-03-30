package com.technical.test.meli.challenge.Application.Usescase.Discount.Categories;

import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesItemResponse;
import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponse;
import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponseService;

public interface ICategories {
    public CategoriesResponseService getCategoriesByItem(String ids);
}
