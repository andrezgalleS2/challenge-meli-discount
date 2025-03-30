package com.technical.test.meli.challenge.Infrastructure.Adapter.GetItems;

import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;

public interface IExternalGetItems {
    ItemsResponse[] getItems(String ids);
}
