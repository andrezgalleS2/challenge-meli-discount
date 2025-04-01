package com.technical.test.meli.challenge.controller;

import com.technical.test.meli.challenge.dto.discount.DiscountItemsResponse;
import com.technical.test.meli.challenge.dto.categories.CategoriesResponseService;
import com.technical.test.meli.challenge.service.ICategories;
import com.technical.test.meli.challenge.service.Items;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meli")
public class DiscountController {

    private final Items itemsService;
    private final ICategories categoriesService;

    public DiscountController(Items itemsService, ICategories categoriesService) {
        this.itemsService = itemsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping(value = "/discount")
    public DiscountItemsResponse getItemsWithDiscount(@RequestParam("item_ids") String itemIds) {
            return itemsService.getItemsWithDiscount(itemIds);
    }

    @GetMapping(value = "/discount/categories")
    public CategoriesResponseService getItemsWithDiscountByCategory(@RequestParam("item_ids") String itemIds) {
            return categoriesService.getCategoriesByItem(itemIds);
    }
}