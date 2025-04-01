package com.technical.test.meli.challenge.dto.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesResponseService {
    private CategoryResponse[] category;
    private Number code;
    private String message;
    private String error;
}
