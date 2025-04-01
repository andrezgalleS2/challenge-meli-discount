package com.technical.test.meli.challenge.dto.categories;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesItemResponse {
    private List<CategoryItemIdResponse> categories;

}
