package com.technical.test.meli.challenge.Application.Dto.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesResponse {

    private String id;
    private String name;

    @JsonProperty("path_from_root")
    private PathFromRoot[] pathFromRoot;

    @JsonProperty("children_categories")
    private ChildrenCategories[] childrenCategories;
}
