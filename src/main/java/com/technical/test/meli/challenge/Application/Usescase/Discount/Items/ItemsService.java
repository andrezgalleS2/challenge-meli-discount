package com.technical.test.meli.challenge.Application.Usescase.Discount.Items;

import com.technical.test.meli.challenge.Application.Dto.Discount.DiscountItemsResponse;
import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;
import com.technical.test.meli.challenge.Infrastructure.Adapter.GetItems.IExternalGetItems;
import com.technical.test.meli.challenge.mocks.Mocks;
import com.technical.test.meli.challenge.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemsService implements Items {

    private final IExternalGetItems externalGetItems;

    private static final Logger logger = LoggerFactory.getLogger(ItemsService.class);

    @Override
    public DiscountItemsResponse getItemsWithDiscount(String ids) {

        try{

            DiscountItemsResponse discountItemsResponse = new DiscountItemsResponse();

            if (!Utils.isValidIds(ids)) {
                discountItemsResponse.setCode("400");
                discountItemsResponse.setMessage("Error en el Request de entrada");
                discountItemsResponse.setError("Ids no validos");
                return discountItemsResponse;
            }

            ItemsResponse[] items = externalGetItems.getItems(ids);
            //List<ItemsResponse> mockItems = Mocks.getItemsResponse();
            //ItemsResponse[] items = mockItems.toArray(new ItemsResponse[0]);

            Map<Number, List<ItemsResponse>> groupedItemsForSeller = Utils.groupItemsBySellerId(items);
            List<String> nonOverlappingItemIds = new ArrayList<>();


            for (List<ItemsResponse> itemsIterable : groupedItemsForSeller.values()) {

                List<ItemsResponse> nonOverlappingItems = Utils.getLargestNonOverlappingSet(itemsIterable);

                Utils.sortItemsByDateRange(Objects.requireNonNull(nonOverlappingItems));

                nonOverlappingItemIds.addAll(nonOverlappingItems.stream()
                        .map(item -> item.getBody().getId())
                        .toList());
            }


            discountItemsResponse.setCode("200");
            discountItemsResponse.setMessage("Consulta exitosa");
            discountItemsResponse.setItems(new ArrayList<>(nonOverlappingItemIds));
            discountItemsResponse.setError(null);
            return discountItemsResponse;

        }catch (Exception e){
            logger.error("Error in getItemsWithDiscount", e);
            return null;
        }

    }
}