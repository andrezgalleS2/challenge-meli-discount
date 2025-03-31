package com.technical.test.meli.challenge.Application.Usescase.Discount.Items;

import com.technical.test.meli.challenge.Application.Dto.Discount.DiscountItemsResponse;
import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;
import com.technical.test.meli.challenge.Infrastructure.Adapter.GetItems.IExternalGetItems;
import com.technical.test.meli.challenge.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

            Map<Number, List<ItemsResponse>> groupedItemsForSeller = groupItemsBySellerId(items);
            List<String> nonOverlappingItemIds = new ArrayList<>();


            for (List<ItemsResponse> itemsIterable : groupedItemsForSeller.values()) {

                List<ItemsResponse> nonOverlappingItems = Utils.getLargestNonOverlappingSet(itemsIterable);

                sortItemsByDateRange(Objects.requireNonNull(nonOverlappingItems));

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

    public static Map<Number, List<ItemsResponse>> groupItemsBySellerId(ItemsResponse[] items) {
        Map<Number, List<ItemsResponse>> groupedItems = new HashMap<>();
        for (ItemsResponse item : items) {
            Number sellerId = item.getBody().getSellerId();
            groupedItems.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(item);
        }
        return groupedItems;
    }

    public static void sortItemsByDateRange(List<ItemsResponse> items) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        items.sort((item1, item2) -> {
            try {
                Date date1 = sdf.parse(item1.getBody().getDateCreated());
                Date date2 = sdf.parse(item2.getBody().getDateCreated());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                logger.error("Error in sortItemsByDateRange", e);
                return 0;
            }
        });
    }
}