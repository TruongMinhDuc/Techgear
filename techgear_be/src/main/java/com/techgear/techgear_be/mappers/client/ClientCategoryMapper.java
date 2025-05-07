package com.techgear.techgear_be.mappers.client;

import com.techgear.techgear_be.dtos.client.ClientCategoryResponse;
import com.techgear.techgear_be.models.product.Category;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ClientCategoryMapper {


    public List<ClientCategoryResponse> entityToResponse(List<Category> categories, int maxLevel) {
        if (maxLevel == 0) {
            return Collections.emptyList();
        }

        return categories.stream()
                .flatMap(category -> Stream.of(new ClientCategoryResponse()
                        .setCategoryName(category.getName())
                        .setCategorySlug(category.getSlug())
                        .setCategoryChildren(entityToResponse(category.getCategories(), maxLevel - 1))))
                .collect(Collectors.toList());
    }


    public ClientCategoryResponse entityToResponse(@Nullable Category category, boolean isParent) {
        if (category == null) {
            return null;
        }

        ClientCategoryResponse categoryResponse = new ClientCategoryResponse();

        categoryResponse
                .setCategoryName(category.getName())
                .setCategorySlug(category.getSlug());

        if (!isParent) {
            categoryResponse.setCategoryChildren(entityToResponse(category.getCategories(), 1));
        }

        if (category.getParentCategory() == null) {
            return categoryResponse;
        }

        return categoryResponse.setCategoryParent(entityToResponse(category.getParentCategory(), true));
    }

}
