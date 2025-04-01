package com.technical.test.meli.challenge.adapter;

import com.technical.test.meli.challenge.dto.itemsResponse.ItemsResponse;

public interface IExternalGetItems {
    ItemsResponse[] getItems(String ids);
}
