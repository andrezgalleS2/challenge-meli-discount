package com.technical.test.meli.challenge.service;

import com.technical.test.meli.challenge.dto.categories.CategoriesResponseService;

public interface ICategories {
    public CategoriesResponseService getCategoriesByItem(String ids);
}
