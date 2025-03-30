package com.technical.test.meli.challenge.Application.Dto.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryBodyResponse {
    @JsonProperty("category_id")
    private String categoryId;
}
