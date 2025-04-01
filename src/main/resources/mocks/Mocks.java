package mocks;

import com.technical.test.meli.challenge.dto.itemsResponse.BodyResponse;
import com.technical.test.meli.challenge.dto.itemsResponse.ItemsResponse;
import com.technical.test.meli.challenge.dto.categories.CategoriesResponse;
import com.technical.test.meli.challenge.dto.categories.ChildrenCategories;
import com.technical.test.meli.challenge.dto.categories.PathFromRoot;

import java.util.ArrayList;
import java.util.List;

public class Mocks {

    public static List<ItemsResponse> getItemsResponse() {
        List<ItemsResponse> itemsResponses = new ArrayList<>();

        itemsResponses.add(createItem("MLA1", "2024-04-05T16:21:40.000Z", "2024-04-10T19:40:06.000Z", "MLA1001"));
        itemsResponses.add(createItem("MLA2", "2024-04-11T08:26:30.000Z", "2024-06-05T23:23:19.000Z", "MLA1002"));
        itemsResponses.add(createItem("MLA3", "2024-04-13T14:53:20.000Z", "2024-04-25T18:11:43.000Z", "MLA4001"));
        itemsResponses.add(createItem("MLA4", "2023-04-05T13:41:32.000Z", "2024-06-20T16:21:40.000Z","MLA1003"));
        itemsResponses.add(createItem("MLA5", "2024-06-04T09:17:36.000Z", "2024-07-05T20:51:32.000Z", "MLA4002"));

        return itemsResponses;
    }

    public static CategoriesResponse getItemsForCategory(String categoryId) {
        List<CategoriesResponse> categoriesResponses = new ArrayList<>();

        // Primera categoría
        PathFromRoot[] pathFromRoot1 = new PathFromRoot[1];
        pathFromRoot1[0] = new PathFromRoot();
        pathFromRoot1[0].setId("MLA1000");

        ChildrenCategories[] childrenCategories1 = new ChildrenCategories[3];
        childrenCategories1[0] = new ChildrenCategories();
        childrenCategories1[0].setId("MLA1001");
        childrenCategories1[1] = new ChildrenCategories();
        childrenCategories1[1].setId("MLA1002");
        childrenCategories1[2] = new ChildrenCategories();
        childrenCategories1[2].setId("MLA1003");

        categoriesResponses.add(createCategory("MLA1000", "Categoria de ejemplo 1", pathFromRoot1, childrenCategories1));

        // Segunda categoría
        PathFromRoot[] pathFromRoot2 = new PathFromRoot[3];
        pathFromRoot2[0] = new PathFromRoot();
        pathFromRoot2[0].setId("MLA2000");
        pathFromRoot2[1] = new PathFromRoot();
        pathFromRoot2[1].setId("MLA3000");
        pathFromRoot2[2] = new PathFromRoot();
        pathFromRoot2[2].setId("MLA4000");

        ChildrenCategories[] childrenCategories2 = new ChildrenCategories[3];
        childrenCategories2[0] = new ChildrenCategories();
        childrenCategories2[0].setId("MLA4001");
        childrenCategories2[1] = new ChildrenCategories();
        childrenCategories2[1].setId("MLA4002");
        childrenCategories2[2] = new ChildrenCategories();
        childrenCategories2[2].setId("MLA4003");

        categoriesResponses.add(createCategory("MLA4000", "Categoria de ejemplo 2", pathFromRoot2, childrenCategories2));

        // Buscar el categoryId en los children categories
        for (CategoriesResponse category : categoriesResponses) {
            for (ChildrenCategories child : category.getChildrenCategories()) {
                if (child.getId().equals(categoryId)) {
                    return category;
                }
            }
        }

        return null; // Si no se encuentra el categoryId en los children categories
    }

    private static CategoriesResponse createCategory(String categoryId, String categoryName, PathFromRoot[] pathFromRoot, ChildrenCategories[] childrenCategories) {
        CategoriesResponse categoriesResponse = new CategoriesResponse();
        categoriesResponse.setId(categoryId);
        categoriesResponse.setName(categoryName);
        categoriesResponse.setPathFromRoot(pathFromRoot);
        categoriesResponse.setChildrenCategories(childrenCategories);
        return categoriesResponse;
    }

    private static ItemsResponse createItem(String id, String dateCreated, String lastUpdated, String categoryId) {
        ItemsResponse itemsResponse = new ItemsResponse();
        itemsResponse.setCode(200);

        BodyResponse bodyResponse = new BodyResponse();
        bodyResponse.setId(id);
        bodyResponse.setSiteId("MLA");
        bodyResponse.setTitle("Item de prueba " + id);
        bodyResponse.setSellerId(123456);
        bodyResponse.setCategoryId(categoryId);
        bodyResponse.setStatus("active");
        bodyResponse.setDateCreated(dateCreated);
        bodyResponse.setLastUpdated(lastUpdated);

        itemsResponse.setBody(bodyResponse);
        return itemsResponse;
    }
}