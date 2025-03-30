package com.technical.test.meli.challenge.Application.Dto.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesResponseService {
    private CategoryResponse[] category;
    private String code;
    private String message;
    private String error;
}
