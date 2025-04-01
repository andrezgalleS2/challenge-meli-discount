package com.technical.test.meli.challenge.service.impl;

import com.technical.test.meli.challenge.dto.itemsResponse.ItemsResponse;
import com.technical.test.meli.challenge.dto.categories.CategoriesResponse;
import com.technical.test.meli.challenge.dto.categories.CategoriesResponseService;
import com.technical.test.meli.challenge.dto.categories.CategoryResponse;
import com.technical.test.meli.challenge.adapter.IExternalGetCategories;
import com.technical.test.meli.challenge.adapter.IExternalGetItems;
import com.technical.test.meli.challenge.util.Constants;
import com.technical.test.meli.challenge.util.FunctionsUtils;
import com.technical.test.meli.challenge.service.ICategories;
import jakarta.servlet.http.HttpServletResponse;
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

            if (!FunctionsUtils.isValidIds(ids)) {
                response.setCode(HttpServletResponse.SC_UNAUTHORIZED);
                response.setMessage(Constants.RESPONSE_SERVICE_ERROR_IN_REQUEST);
                response.setError(Constants.RESPONSE_SERVICE_ERROR_ID_INVALIDS);
                return response;
            }

            //List<ItemsResponse> mockItems = Mocks.getItemsResponse();
            //ItemsResponse[] items = mockItems.toArray(new ItemsResponse[0]);
            ItemsResponse[] items = externalGetItems.getItems(ids);

            Map<String, List<ItemsResponse>> groupedItemsForCategory = groupItemsByCategory(items);
            Map<String, List<ItemsResponse>> nonOverlappingItems = getNonOverlappingItems(groupedItemsForCategory);

            List<CategoryResponse> categoryResponses = createCategoryResponses(nonOverlappingItems);

            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.RESPONSE_SERVICE_SUCCESS);
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
            List<ItemsResponse> nonOverlappingItemsForCategory = FunctionsUtils.getLargestNonOverlappingSet(itemsForCategory);
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