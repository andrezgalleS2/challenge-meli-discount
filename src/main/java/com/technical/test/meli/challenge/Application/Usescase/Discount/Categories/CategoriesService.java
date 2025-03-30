package com.technical.test.meli.challenge.Application.Usescase.Discount.Categories;

import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;
import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponse;
import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponseService;
import com.technical.test.meli.challenge.Application.Dto.categories.CategoryResponse;
import com.technical.test.meli.challenge.Infrastructure.Adapter.GetCategories.IExternalGetCategories;
import com.technical.test.meli.challenge.Infrastructure.Adapter.GetItems.IExternalGetItems;
import com.technical.test.meli.challenge.mocks.Mocks;
import com.technical.test.meli.challenge.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoriesService implements ICategories {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    private final IExternalGetItems externalGetItems;
    private final IExternalGetCategories externalGetCategories;

    @Override
    public CategoriesResponseService getCategoriesByItem(String ids) {
        try {
            CategoriesResponseService response = new CategoriesResponseService();

            if (!Utils.isValidIds(ids)) {
                response.setCode("400");
                response.setMessage("Error en el Request de entrada");
                response.setError("Ids no validos");
                return response;
            }

            //List<ItemsResponse> mockItems = Mocks.getItemsResponse();
            //ItemsResponse[] items = mockItems.toArray(new ItemsResponse[0]);
            ItemsResponse[] items = externalGetItems.getItems(ids);

            Map<String, List<ItemsResponse>> groupedItemsForCategory = groupItemsByCategory(items);
            Map<String, List<ItemsResponse>> nonOverlappingItems = getNonOverlappingItems(groupedItemsForCategory);

            List<CategoryResponse> categoryResponses = createCategoryResponses(nonOverlappingItems);

            response.setCode("200");
            response.setMessage("Consulta exitosa");
            response.setCategory(categoryResponses.toArray(new CategoryResponse[0]));
            response.setError(null);

            return response;
        } catch (Exception e) {
            logger.error("Error in getCategoriesByItem", e);
            return null;
        }
    }

    private Map<String, List<ItemsResponse>> groupItemsByCategory(ItemsResponse[] items) {
        Map<String, List<ItemsResponse>> groupedItemsForCategory = new HashMap<>();
        for (ItemsResponse item : items) {
            String categoryId = item.getBody().getCategoryId();

            //CategoriesResponse categories = Mocks.getItemsForCategory(categoryId);
            CategoriesResponse categories = externalGetCategories.getCategories(categoryId);

            String categoryPath = categories.getPathFromRoot()[0].getId();
            groupedItemsForCategory.computeIfAbsent(categoryPath, k -> new ArrayList<>()).add(item);
        }
        return groupedItemsForCategory;
    }

    private Map<String, List<ItemsResponse>> getNonOverlappingItems(Map<String, List<ItemsResponse>> groupedItemsForCategory) {
        Map<String, List<ItemsResponse>> nonOverlappingItems = new HashMap<>();
        for (Map.Entry<String, List<ItemsResponse>> entry : groupedItemsForCategory.entrySet()) {
            List<ItemsResponse> itemsForCategory = entry.getValue();
            List<ItemsResponse> nonOverlappingItemsForCategory = Utils.getLargestNonOverlappingSet(itemsForCategory);
            nonOverlappingItems.put(entry.getKey(), nonOverlappingItemsForCategory);
        }
        return nonOverlappingItems;
    }

    private List<CategoryResponse> createCategoryResponses(Map<String, List<ItemsResponse>> nonOverlappingItems) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Map.Entry<String, List<ItemsResponse>> entry : nonOverlappingItems.entrySet()) {
            List<String> itemIds = new ArrayList<>();
            for (ItemsResponse item : entry.getValue()) {
                itemIds.add(item.getBody().getId());
            }
            categoryResponses.add(new CategoryResponse(entry.getKey(), itemIds));
        }
        return categoryResponses;
    }
}