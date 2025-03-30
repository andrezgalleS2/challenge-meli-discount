package com.technical.test.meli.challenge.Application.Dto.ItemsResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("site_id")
    private String siteId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("seller_id")
    private Number sellerId;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("last_updated")
    private String lastUpdated;

}